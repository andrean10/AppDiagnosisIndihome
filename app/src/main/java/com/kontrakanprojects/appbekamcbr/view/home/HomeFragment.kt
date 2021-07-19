package com.kontrakanprojects.appbekamcbr.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kontrakanprojects.appbekamcbr.R
import com.kontrakanprojects.appbekamcbr.databinding.FragmentHomeBinding

class HomeFragment : Fragment(){

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            homeConsultation.setOnClickListener { moveToConsultation() }
            homeInfo.setOnClickListener { moveToInfo() }
            homeResult.setOnClickListener { moveToResultDiagnosis() }
        }
    }

    private fun moveToResultDiagnosis() {
        findNavController().navigate(R.id.action_homeFragment_to_resultFragment)
    }

    private fun moveToConsultation() {
        findNavController().navigate(R.id.action_homeFragment_to_consultFragment)
    }

    private fun moveToInfo() {
        findNavController().navigate(R.id.action_homeFragment_to_infoActivity)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}