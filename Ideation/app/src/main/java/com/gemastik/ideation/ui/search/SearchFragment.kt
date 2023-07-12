package com.gemastik.ideation.ui.search

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gemastik.ideation.MainViewModelFactory
import com.gemastik.ideation.adapter.*
import com.gemastik.ideation.databinding.FragmentSearchBinding
import com.gemastik.ideation.response.RecommendationsItem
import com.gemastik.ideation.result.TheResult
import com.gemastik.ideation.ui.detailProject.DetailProjectActivity
import com.gemastik.ideation.ui.home.HomeViewModel



class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    companion object{
        const val PROJECT_NAME = "PROJECT_NAME"
    }

    private val searchViewModel by viewModels<HomeViewModel> {
        MainViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)


        setInitiateSearch()
        binding.rvSearchProjecet.scrollToPosition(0)

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

    private fun setInitiateSearch(){

        val bundle = requireActivity().intent.extras

        val nama = bundle?.getString("nama")
        searchViewModel.getUser().observe(viewLifecycleOwner){user->
            if (nama != null) {
                getData(user.token,nama)
            }
        }
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