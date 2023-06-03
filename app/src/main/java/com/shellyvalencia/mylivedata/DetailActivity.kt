package com.shellyvalencia.mylivedata

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.shellyvalencia.mylivedata.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object {
        const val KEY_USER = "key_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
                R.string.tab_text_1,
                R.string.tab_text_2,
                R.string.tab_text_3
        )
    }

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataUser = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(KEY_USER, ItemsItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(KEY_USER)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Detail Github User"

        if (dataUser != null) {
            initObserver(dataUser)
        }
    }

    private fun initObserver(user: ItemsItem) {
        detailViewModel.getDetailUser(user.login)

        detailViewModel.detailUser.observe(this, {
            setUserData(it)
        })

        detailViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        detailViewModel.snackbarText.observe(this, {
            it.getContentIfNotHandled()?.let { snackbarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackbarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })
    }

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
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}