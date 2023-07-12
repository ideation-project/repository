package com.gemastik.ideation.ui.listKategori

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gemastik.ideation.MainViewModelFactory
import com.gemastik.ideation.adapter.LoadingStateAdapter
import com.gemastik.ideation.adapter.ProjectPagingAdapter
import com.gemastik.ideation.databinding.ActivityListKategoriBinding
import com.gemastik.ideation.ui.home.HomeViewModel

class ListKategoriActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListKategoriBinding
    private val mainViewModel by viewModels<HomeViewModel> {
        MainViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListKategoriBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setUpView()

    }

    private fun setUpView() {

        val extras = intent.extras
        if (extras != null){
            val kategori = extras.getString("extra_kategori").toString()

            mainViewModel.getUser().observe(this) { user ->
                getData(user.token,kategori)

            }
        }


    }

    private fun getData(token: String,kategori:String) {
        val adapter = ProjectPagingAdapter()
        binding.rvListProjectCateogry.layoutManager = LinearLayoutManager(this)
        binding.rvListProjectCateogry.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        mainViewModel.getAllProjectPaging(token,kategori).observe(this) {
            adapter.submitData(lifecycle, it)
        }

    }


}