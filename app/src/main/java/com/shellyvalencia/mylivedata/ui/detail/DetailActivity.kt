package com.shellyvalencia.mylivedata.ui.detail

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.shellyvalencia.mylivedata.data.remote.response.DetailUserResponse
import com.shellyvalencia.mylivedata.R
import com.shellyvalencia.mylivedata.data.remote.response.ItemsItem
import com.shellyvalencia.mylivedata.data.local.entity.FavoriteUser
import com.shellyvalencia.mylivedata.databinding.ActivityDetailBinding
import com.shellyvalencia.mylivedata.helper.FavoriteViewModelFactory
import com.shellyvalencia.mylivedata.ui.insert.FavoriteUserAddUpdateViewModel

class DetailActivity : AppCompatActivity() {

    companion object {
        const val KEY_USER = "key_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>()

    private var favoriteUser: FavoriteUser? = null
    private lateinit var favoriteUserAddUpdateViewModel: FavoriteUserAddUpdateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteUserAddUpdateViewModel = obtainViewModel(this@DetailActivity)

        val dataUser = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(KEY_USER, ItemsItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(KEY_USER)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, dataUser!!.login)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Detail Github User"

        initObserver(dataUser)

        favoriteUser = FavoriteUser()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.fab.setOnClickListener {
            if (binding.fab.tag == "favorite") {
                favoriteUserAddUpdateViewModel.delete(favoriteUser as FavoriteUser)
            } else {
                favoriteUserAddUpdateViewModel.insert(favoriteUser as FavoriteUser)
            }
        }
    }

    private fun initObserver(user: ItemsItem) {
        viewModel.getDetailUser(user.login)

        viewModel.detailUser.observe(this) {
            setUserData(it)
            favoriteUser = it.login?.let { username -> FavoriteUser(username, it.avatarUrl) }
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackbarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackbarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUserData(user: DetailUserResponse) {
        val tvDetailName:TextView = findViewById(R.id.tv_detail_name)
        val tvDetailUsername:TextView = findViewById(R.id.tv_detail_username)
        val tvDetailFollowers:TextView = findViewById(R.id.tv_detail_followers)
        val tvDetailFollowing:TextView = findViewById(R.id.tv_detail_following)
        val ivDetailPhoto:ImageView = findViewById(R.id.iv_detail_photo)

        tvDetailName.text = user.name
        tvDetailUsername.text = user.login
        tvDetailFollowers.text = "${user.followers} Followers"
        tvDetailFollowing.text = "${user.following} Following"
        Glide.with(this)
            .load(user.avatarUrl)
            .into(ivDetailPhoto)

        favoriteUser = user.login?.let { FavoriteUser(it, user.avatarUrl) }
        favoriteUser?.let {
            favoriteUserAddUpdateViewModel.isFavorited(it.username).observe(this) {
                setFavoriteUser(it)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUserAddUpdateViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteUserAddUpdateViewModel::class.java)
    }

    private fun setFavoriteUser(value: Boolean) {
        binding.fab.apply {
            if (value) {
                tag = "favorite"
                setImageDrawable(ContextCompat.getDrawable(
                    this@DetailActivity,
                    R.drawable.ic_favorite_full_white_24px
                ))
            } else {
                tag = ""
                setImageDrawable(ContextCompat.getDrawable(
                    this@DetailActivity,
                    R.drawable.ic_favorite_border_white_24px
                ))
            }
        }
    }
}