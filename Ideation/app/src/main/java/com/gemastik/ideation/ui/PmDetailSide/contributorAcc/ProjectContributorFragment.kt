package com.gemastik.ideation.ui.PmDetailSide.contributorAcc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gemastik.ideation.MainViewModelFactory
import com.gemastik.ideation.adapter.ContributorProjectAdapter
import com.gemastik.ideation.databinding.FragmentProjectContributorBinding
import com.gemastik.ideation.result.TheResult
import com.gemastik.ideation.ui.home.HomeViewModel


class ProjectContributorFragment : Fragment() {
    private var _binding: FragmentProjectContributorBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel> {
        MainViewModelFactory.getInstance(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectContributorBinding.inflate(inflater, container, false)

        val extras = requireActivity().intent.extras
        if (extras != null){
            val idProyek = extras.getInt("extra_id")

            homeViewModel.getUser().observe(viewLifecycleOwner){user->
                getContributorAcc(user.token,idProyek)
            }

        }

        return binding.root
    }

    private fun getContributorAcc(token:String,Id:Int?){
        homeViewModel.getContributorAcc(token,Id).observe(viewLifecycleOwner){
            if (it != null){
                when(it){
                    is TheResult.Loading->{

                    }
                    is TheResult.Success ->{
                        val adapter = ContributorProjectAdapter()
                        val data = it.data
                        binding.rvListContributor.layoutManager = LinearLayoutManager(requireContext())
                        binding.rvListContributor.adapter = adapter
                        adapter.submitList(data)

                    }
                    is TheResult.Error ->{

                    }
                }

            }

        }


    }






}