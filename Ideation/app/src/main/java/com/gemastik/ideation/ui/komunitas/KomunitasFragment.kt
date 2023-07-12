package com.gemastik.ideation.ui.komunitas

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gemastik.ideation.MainViewModelFactory
import com.gemastik.ideation.R
import com.gemastik.ideation.adapter.ListAllProjectAdapter
import com.gemastik.ideation.adapter.ListPostinganAdapter
import com.gemastik.ideation.databinding.FragmentKomunitasBinding

import com.gemastik.ideation.model.postingan
import com.gemastik.ideation.response.ProjectsItem
import com.gemastik.ideation.ui.PmDetailSide.PmDetailProjectActivity
import com.gemastik.ideation.ui.home.HomeViewModel


class KomunitasFragment : Fragment() {

    private var _binding: FragmentKomunitasBinding? = null
    private val binding get() = _binding!!
    private val list = ArrayList<postingan>()

    private val homeViewModel by viewModels<HomeViewModel> {
        MainViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentKomunitasBinding.inflate(inflater, container, false)


        list.addAll(getListPost())
        ShowRecycleListLatestPost()

        return binding.root
    }


    private fun getListPost():ArrayList<postingan>{
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listWisata = ArrayList<postingan>()
        for (i in dataName.indices){
            val postingan = postingan(dataName[i],dataDescription[i],dataPhoto.getResourceId(i, -1))
            listWisata.add(postingan)
        }
        return listWisata
    }

    private fun ShowRecycleListLatestPost() {

        //Recycle View Latest
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvPostingan.layoutManager = layoutManager
        val postinganListAdapter = ListPostinganAdapter(list)
        binding.rvPostingan.adapter = postinganListAdapter


    }



}