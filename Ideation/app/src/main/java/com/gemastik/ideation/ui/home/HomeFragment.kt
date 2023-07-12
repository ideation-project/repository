package com.gemastik.ideation.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gemastik.ideation.MainViewModelFactory
import com.gemastik.ideation.R
import com.gemastik.ideation.adapter.*
import com.gemastik.ideation.databinding.FragmentHomeBinding
import com.gemastik.ideation.response.ProjectsItem
import com.gemastik.ideation.response.RecommendationsItem
import com.gemastik.ideation.result.TheResult
import com.gemastik.ideation.ui.PmDetailSide.PmDetailProjectActivity
import com.gemastik.ideation.ui.listKategori.ListKategoriActivity
import com.gemastik.ideation.ui.PmDetailSide.contributorAcc.login.LoginActivity
import com.gemastik.ideation.ui.search.SearchActivity
import com.gemastik.ideation.ui.search.SearchFragment


class HomeFragment : Fragment() {

    private val homeViewModel by viewModels<HomeViewModel> {
        MainViewModelFactory.getInstance(requireContext())
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    companion object{
        val EXTRA_DATA = "OBJECT_INTENT"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setViewModel()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)


        val searchItem = binding.searchView

        searchItem.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null){
                    //binding.rvSearchProjecet.scrollToPosition(0)

                    val nama = query
                    val intent = Intent(requireContext(),SearchActivity::class.java)
                    val bundle = Bundle()
                    bundle.putString("nama",nama)
                    intent.putExtras(bundle)
                    startActivity(intent)


                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        return binding.root

    }


    private fun setViewModel() {
        homeViewModel.getUser().observe(viewLifecycleOwner) { user ->
            if (user.isLogin) {
                //get list LatestProyek
                homeViewModel.listProject.observe(viewLifecycleOwner) {
                    ShowRecycleListLatestProject(it)
                }
                homeViewModel.getAllProject("Bearer ${user.token}")

                showRekomendasi(user.token)
                goToCategory()

            } else if (user.token == "expiredToken"){
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }


    private fun goToCategory(){
        binding.ivSosial.setOnClickListener {
            val intent = Intent(requireContext(),ListKategoriActivity::class.java)
            val bundle = Bundle()
            bundle.putString("extra_kategori","Sosial")
            intent.putExtras(bundle)
            startActivity(intent)
        }
        binding.ivKesehatan.setOnClickListener {
            val intent = Intent(requireContext(),ListKategoriActivity::class.java)
            val bundle = Bundle()
            bundle.putString("extra_kategori","Kesehatan")
            intent.putExtras(bundle)
            startActivity(intent)
        }
        binding.ivPolitik.setOnClickListener {
            val intent = Intent(requireContext(),ListKategoriActivity::class.java)
            val bundle = Bundle()
            bundle.putString("extra_kategori","Pemerintahan")
            intent.putExtras(bundle)
            startActivity(intent)
        }
        binding.ivBudaya.setOnClickListener {
            val intent = Intent(requireContext(),ListKategoriActivity::class.java)
            val bundle = Bundle()
            bundle.putString("extra_kategori","Budaya")
            intent.putExtras(bundle)
            startActivity(intent)
        }
        binding.ivPendidikan.setOnClickListener {
            val intent = Intent(requireContext(),ListKategoriActivity::class.java)
            val bundle = Bundle()
            bundle.putString("extra_kategori","Pendidikan")
            intent.putExtras(bundle)
            startActivity(intent)
        }
        binding.ivLingkungan.setOnClickListener {
            val intent = Intent(requireContext(),ListKategoriActivity::class.java)
            val bundle = Bundle()
            bundle.putString("extra_kategori","Lingkungan")
            intent.putExtras(bundle)
            startActivity(intent)
        }
        binding.ivKesejahteraan.setOnClickListener {
            val intent = Intent(requireContext(),ListKategoriActivity::class.java)
            val bundle = Bundle()
            bundle.putString("extra_kategori","Kesejeahteraan")
            intent.putExtras(bundle)
            startActivity(intent)
        }
        binding.ivSains.setOnClickListener {
            val intent = Intent(requireContext(),ListKategoriActivity::class.java)
            val bundle = Bundle()
            bundle.putString("extra_kategori","Sains")
            intent.putExtras(bundle)
            startActivity(intent)
        }
        binding.ivOlahraga.setOnClickListener {
            val intent = Intent(requireContext(),ListKategoriActivity::class.java)
            val bundle = Bundle()
            bundle.putString("extra_kategori","Olahraga")
            intent.putExtras(bundle)
            startActivity(intent)
        }
        binding.ivHukum.setOnClickListener {
            val intent = Intent(requireContext(),ListKategoriActivity::class.java)
            val bundle = Bundle()
            bundle.putString("extra_kategori","Hukum")
            intent.putExtras(bundle)
            startActivity(intent)
        }

    }

    private fun ShowRecycleListLatestProject(listProject: List<ProjectsItem>) {

        //Recycle View Latest
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecent.layoutManager = layoutManager
        val projectListAdapter = ListAllProjectAdapter(listProject)
        binding.rvRecent.adapter = projectListAdapter


        projectListAdapter.setOnItemClickCallback(object :
            ListAllProjectAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ProjectsItem) {
                val intent = Intent(requireContext(), PmDetailProjectActivity::class.java)

                intent.putExtra(PmDetailProjectActivity.EXTRA_DATA,data)

                startActivity(intent)
            }
        })

    }

    private fun showLatestWhenRekomEmpty(listProject: List<ProjectsItem>) {

        //Recycle View Latest
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvRekomendasi.layoutManager = layoutManager
        val projectListAdapter = ListAllProjectAdapter(listProject)
        binding.rvRekomendasi.adapter = projectListAdapter


        projectListAdapter.setOnItemClickCallback(object :
            ListAllProjectAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ProjectsItem) {
                val intent = Intent(requireContext(), PmDetailProjectActivity::class.java)

                intent.putExtra(PmDetailProjectActivity.EXTRA_DATA,data)

                startActivity(intent)
            }
        })

    }


    private fun showRekomenProject(listProjectRekomen: List<RecommendationsItem>){

        //Recycle View Rekomendasi
        binding.rvRekomendasi.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val projectListRecomenAdapter = RekomenListAdapter(listProjectRekomen)
        binding.rvRekomendasi.adapter = projectListRecomenAdapter

        projectListRecomenAdapter.setOnItemClickCallback(object :RekomenListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: RecommendationsItem) {
                val intent = Intent(requireContext(),PmDetailProjectActivity::class.java)

                intent.putExtra(PmDetailProjectActivity.EXTRA_DATA,data)
                startActivity(intent)
            }

        })

    }


    private fun showRekomendasi(token:String){
       // binding.progressBar2.visibility = View.VISIBLE
        homeViewModel.getRecomendation(token).observe(viewLifecycleOwner){
            if (it != null){
                when(it){
                    is TheResult.Loading->{

                    }
                    is TheResult.Success ->{
                        binding.emptyData.visibility = View.GONE
                       // binding.progressBar2.visibility = View.GONE
                        val adapter = ListAllRecomendationProjectAdapter()
                        val data = it.data
                        if (data.isEmpty()){
                            //binding.emptyData.visibility = View.VISIBLE
                            homeViewModel.getUser().observe(viewLifecycleOwner){user->
                                homeViewModel.getAllProject("Bearer ${user.token}")
                                homeViewModel.listProject.observe(viewLifecycleOwner){
                                    showLatestWhenRekomEmpty(it)
                                }

                            }

                        }else{
                            binding.emptyData.visibility = View.GONE
                            binding.rvRekomendasi.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                            binding.rvRekomendasi.adapter = adapter
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