package com.kontrakanprojects.appbekamcbr.view.consult.symptomp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kontrakanprojects.appbekamcbr.databinding.FragmentSymptompBinding
import com.kontrakanprojects.appbekamcbr.view.consult.viewmodel.SymptompViewModel
import com.kontrakanprojects.appbekamcbr.view.diagnosis.DiagnosisActivity

class SymptompFragment : Fragment() {

    private var _binding: FragmentSymptompBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<SymptompViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSymptompBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ambil data dari navigation
        val data = SymptompFragmentArgs.fromBundle(arguments as Bundle).resultConsult

        with(binding) {
            btnSymptompSave.setOnClickListener { saveSymptomp() }
            btnSymptompDiagnosis.setOnClickListener { moveToDiagnosis() }
        }
    }

    private fun moveToDiagnosis() {
        startActivity(Intent(requireContext(), DiagnosisActivity::class.java))
    }

    private fun saveSymptomp() {
        // untuk menyimpan nilai variabel gejala
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}