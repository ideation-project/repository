package com.capstone.idekita.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.idekita.MainViewModel
import com.capstone.idekita.MainViewModelFactory
import com.capstone.idekita.databinding.FragmentProfileBinding
import com.capstone.idekita.ui.login.LoginActivity


class ProfileFragment : Fragment() {

    private val homeViewModel by viewModels<MainViewModel> {
        MainViewModelFactory.getInstance(requireContext())
    }
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setViewModel()

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root


    }

    private fun setViewModel() {
        homeViewModel.getUser().observe(viewLifecycleOwner) { user ->
            if (user.isLogin) {
                binding.nameTV.text = user.name
                binding.emailTV.text = user.gmail
                //binding.idUser.text = user.id
                binding.btnLogout.setOnClickListener {
                    homeViewModel.logout()
                }
            } else {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)

            }
        }
    }


}