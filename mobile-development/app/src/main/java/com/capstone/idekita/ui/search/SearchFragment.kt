package com.capstone.idekita.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.idekita.MainViewModelFactory
import com.capstone.idekita.R
import com.capstone.idekita.adapter.*
import com.capstone.idekita.databinding.FragmentSearchBinding
import com.capstone.idekita.response.ProjectsItem
import com.capstone.idekita.result.TheResult
import com.capstone.idekita.ui.PmDetailSide.PmDetailProjectActivity
import com.capstone.idekita.ui.home.HomeViewModel



class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel by viewModels<HomeViewModel> {
        MainViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        val searchItem = binding.searchView

        searchItem.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null){
                    binding.rvSearchProjecet.scrollToPosition(0)

                    val nama = query

                    searchViewModel.getUser().observe(viewLifecycleOwner){user->
                        getData(user.token,nama)
                    }

                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        return binding.root


    }

    private fun getData(token:String,name:String){
        binding.progressBar.visibility = View.VISIBLE
        searchViewModel.getSearchProject(token,name).observe(viewLifecycleOwner){
            if (it != null){
                when(it){
                    is TheResult.Loading->{

                    }
                    is TheResult.Success ->{
                        binding.progressBar.visibility = View.GONE
                        val adapter = ListAllSearchProjectAdapter()
                        val data = it.data
                        if (data.isEmpty()){
                            binding.emptyData.visibility = View.VISIBLE
                            binding.rvSearchProjecet.visibility = View.GONE
                        }else{
                            binding.emptyData.visibility = View.GONE
                            binding.rvSearchProjecet.layoutManager = LinearLayoutManager(requireContext())
                            binding.rvSearchProjecet.adapter = adapter
                            adapter.submitList(data)
                        }

                    }
                    is TheResult.Error ->{

                    }
                }

            }
        }

    }




}