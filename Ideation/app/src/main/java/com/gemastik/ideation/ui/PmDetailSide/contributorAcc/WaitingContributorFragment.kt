package com.gemastik.ideation.ui.PmDetailSide.contributorAcc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gemastik.ideation.MainViewModelFactory
import com.gemastik.ideation.adapter.WaitingContributorProjectAdapter
import com.gemastik.ideation.databinding.FragmentWaitingContributorBinding
import com.gemastik.ideation.response.ContributorsItemWait
import com.gemastik.ideation.result.TheResult
import com.gemastik.ideation.ui.home.HomeViewModel


class WaitingContributorFragment : Fragment() {


    private var _binding: FragmentWaitingContributorBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel> {
        MainViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentWaitingContributorBinding.inflate(inflater, container, false)

        val extras = requireActivity().intent.extras
        if(extras != null){
            val idProyek = extras.getInt("extra_id")

            homeViewModel.getUser().observe(viewLifecycleOwner){user->
                showContributorWaiting(user.token,idProyek,"Anggota")
            }

        }

        return binding.root
    }


    private fun accProject(token:String,id:Int,statLamar:String,role:String){
        homeViewModel.AccProject(token, id, statLamar, role).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is TheResult.Loading -> {

                    }
                    is TheResult.Success -> {
                       // Toast.makeText(requireContext(), result.data.message, Toast.LENGTH_SHORT).show()
                        Toast.makeText(requireContext(), id.toString(), Toast.LENGTH_SHORT).show()
                    }
                    is TheResult.Error -> {
                        Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showContributorWaiting(token: String,Id:Int,role: String){
        homeViewModel.getWaitingContributor(token,Id).observe(viewLifecycleOwner){
            if (it != null){
                when(it){
                    is TheResult.Loading->{

                    }
                    is TheResult.Success ->{
                        val adapter = WaitingContributorProjectAdapter()
                        val data = it.data
                        if (data.isEmpty()){
                            binding.emptyData.visibility = View.VISIBLE
                        } else{
                            homeViewModel.getUser().observe(viewLifecycleOwner){user->
                                val extras = requireActivity().intent.extras

                                if (user.name != extras?.getString("extra_creator")){
                                    binding.rvWaitingContributor.visibility = View.GONE
                                    binding.emptyData.text = "anda tidak bisa mengakses Halaman ini"
                                }else{
                                    binding.emptyData.visibility = View.GONE
                                    binding.rvWaitingContributor.layoutManager = LinearLayoutManager(requireContext())
                                    binding.rvWaitingContributor.adapter = adapter
                                    adapter.submitList(data)
                                    adapter.setOnItemClickCallback(object :WaitingContributorProjectAdapter.OnItemClickCallback{

                                        override fun onItemClicked(data: ContributorsItemWait) {
                                            val idContributor = data.id
                                            accProject(token,idContributor,"diterima",role)
                                            Toast.makeText(requireContext(),"Ini Tombol Terima",Toast.LENGTH_SHORT).show()

                                        }

                                        override fun onItemTolakClicked(data: ContributorsItemWait) {
                                            Toast.makeText(requireContext(),"Ini Tombol TOLAKKK",Toast.LENGTH_SHORT).show()
                                            val idContributor = data.id
                                            accProject(token,idContributor,"ditolak",role)
                                        }

                                    })
                                }
                            }

                        }


                    }
                    is TheResult.Error ->{

                    }
                }
            }


        }
    }

//    private fun showContributor(listContributor : List<ContributorsItemWait>, token: String,role: String) {
//        binding.rvWaitingContributor.layoutManager = LinearLayoutManager(requireContext())
//        val userWaiting = WaitingContributorProjectAdapter(listContributor,token,role)
//        binding.rvWaitingContributor.adapter = userWaiting
//
//        userWaiting.setOnItemClickCallback(object :WaitingContributorProjectAdapter.OnItemClickCallback{
//
//            override fun onItemClicked(data: ContributorsItemWait) {
//                val idContributor = data.id
//                accProject(token,idContributor,"diterima",role)
//                Toast.makeText(requireContext(),"Ini Tombol Terima",Toast.LENGTH_SHORT).show()
//
//            }
//
//            override fun onItemTolakClicked(data: ContributorsItemWait) {
//                Toast.makeText(requireContext(),"Ini Tombol TOLAKKK",Toast.LENGTH_SHORT).show()
//                val idContributor = data.id
//                accProject(token,idContributor,"ditolak",role)
//            }
//
//        })
//
//    }




}