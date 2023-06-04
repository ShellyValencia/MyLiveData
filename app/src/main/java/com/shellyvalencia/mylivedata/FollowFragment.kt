package com.shellyvalencia.mylivedata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shellyvalencia.mylivedata.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private var username: String? = null
    private var position: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentFollowBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }
        if (position == 1){
            binding.testUsername.text = "Get Follower $username"
        } else {
            binding.testUsername.text = "Get Following $username"
        }
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}