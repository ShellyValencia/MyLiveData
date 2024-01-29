package com.shellyvalencia.mylivedata.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shellyvalencia.mylivedata.data.remote.response.ItemsItem
import com.shellyvalencia.mylivedata.databinding.FragmentFollowBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.shellyvalencia.mylivedata.ui.ListUserAdapter

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private var username: String? = null
    private var position: Int = 0
    private lateinit var adapter: ListUserAdapter
    private val viewModel by viewModels<FollowViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentFollowBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }
        binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())
        if (position == 1){
//            binding.testUsername.text = "Get Follower $username"
            viewModel.getFollowers(username!!)
            viewModel.listFollowers.observe(viewLifecycleOwner) {
                setFollowData(it)
            }
        } else {
//            binding.testUsername.text = "Get Following $username"
            viewModel.getFollowing(username!!)
            viewModel.listFollowing.observe(viewLifecycleOwner) {
                setFollowData(it)
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        viewModel.snackbarText.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    requireActivity().window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    private fun setFollowData(userFollow: List<ItemsItem>) {
        adapter = ListUserAdapter(userFollow)
        binding.rvFollow.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}