package com.gemastik.ideation.ui.myProject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gemastik.ideation.databinding.FragmentFinishBinding
import com.gemastik.ideation.result.TheResult


class FinishFragment : Fragment() {

    private var _binding: FragmentFinishBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MyProjectViewModel> {
        MyProjectFactory.getInstance(requireContext())
    }

    val theAdapter = MyProjectAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFinishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getToken().observe(viewLifecycleOwner) { token ->
            getData(token.token)

        }

    }

    private fun getData(token: String) {
        viewModel.getMyproject(token,"selesai").observe(viewLifecycleOwner) { result ->
            if (result != null) {

                when (result) {
                    is TheResult.Loading -> {

                    }
                    is TheResult.Success -> {
                        val myProjData = result.data
                        if (myProjData.isEmpty()) {
                            binding.tvNoData.visibility = View.VISIBLE
                        } else {
                            binding.tvNoData.visibility = View.GONE
                            theAdapter.submitList(myProjData)
                            binding.rvOngoing.apply {
                                layoutManager = LinearLayoutManager(context)
                                adapter = theAdapter
                            }
                        }


                    }
                    is TheResult.Error -> {
                    }
                }
            }
        }
    }

}