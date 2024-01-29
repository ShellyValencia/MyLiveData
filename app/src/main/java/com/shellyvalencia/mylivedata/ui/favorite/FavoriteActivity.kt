package com.shellyvalencia.mylivedata.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.shellyvalencia.mylivedata.data.remote.response.ItemsItem
import com.shellyvalencia.mylivedata.databinding.ActivityFavoriteBinding
import com.shellyvalencia.mylivedata.helper.FavoriteViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private var _activityFavoriteBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityFavoriteBinding

    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val viewModel = obtainViewModel(this@FavoriteActivity)
        viewModel.getFavoriteUser().observe(this) { users ->
            val items = arrayListOf<ItemsItem>()
            users.map {
                val item = ItemsItem(login = it.username, avatarUrl = it.avatarUrl.toString())
                items.add(item)
            }
            adapter.submitList(items)
        }

        adapter = FavoriteAdapter()

        binding?.rvFavoriteUsers?.layoutManager = LinearLayoutManager(this)
        binding?.rvFavoriteUsers?.setHasFixedSize(true)
        binding?.rvFavoriteUsers?.adapter = adapter

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

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteBinding = null
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}