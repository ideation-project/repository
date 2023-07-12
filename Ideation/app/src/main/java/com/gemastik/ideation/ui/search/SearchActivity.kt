package com.gemastik.ideation.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gemastik.ideation.MainViewModelFactory
import com.gemastik.ideation.R
import com.gemastik.ideation.adapter.ListAllSearchProjectAdapter
import com.gemastik.ideation.ui.search.SearchActivity
import com.gemastik.ideation.databinding.ActivitySearchBinding
import com.gemastik.ideation.result.TheResult
import com.gemastik.ideation.ui.PmDetailSide.PmDetailFactory
import com.gemastik.ideation.ui.PmDetailSide.PmDetailViewModel
import com.gemastik.ideation.ui.home.HomeViewModel

class SearchActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySearchBinding

    private val viewModel by viewModels<HomeViewModel> {
        MainViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setInitiateSearch()

        val searchItem = binding.searchViews

        viewModel.getUser().observe(this){
            val token = it

            searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null){
                        binding.rvSearchProjecet.scrollToPosition(0)

                        val nama = query
                        getData(token.token,nama)


                    }
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {

                    return true
                }

            })
        }


    }

    private fun setInitiateSearch(){

        val bundle = intent.extras

        val nama = bundle?.getString("nama")
        viewModel.getUser().observe(this){user->
            if (nama != null) {
                getData(user.token,nama)
            }
        }
    }



    private fun getData(token:String,name:String){
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getSearchProject(token,name).observe(this){
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
                            binding.rvSearchProjecet.layoutManager = LinearLayoutManager(this)
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

