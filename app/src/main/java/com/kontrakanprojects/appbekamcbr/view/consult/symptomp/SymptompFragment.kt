package com.kontrakanprojects.appbekamcbr.view.consult.symptomp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kontrakanprojects.appbekamcbr.R
import com.kontrakanprojects.appbekamcbr.databinding.FragmentSymptompBinding
import com.kontrakanprojects.appbekamcbr.model.category.ResultCategory
import com.kontrakanprojects.appbekamcbr.utils.showMessage
import com.kontrakanprojects.appbekamcbr.view.consult.viewmodel.SymptompViewModel
import com.kontrakanprojects.appbekamcbr.view.diagnosis.DiagnosisActivity
import www.sanju.motiontoast.MotionToast

class SymptompFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentSymptompBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<SymptompViewModel>()
    private lateinit var symptompAdapter: SymptompAdapter

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
            symptompAdapter = SymptompAdapter()
            with(rvSymptompList) {
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                this.adapter = symptompAdapter
            }
        }

        loadCategory()
    }


    private fun moveToDiagnosis() {
        startActivity(Intent(requireContext(), DiagnosisActivity::class.java))
    }

    private fun loadCategory() {
        with(binding) {
            viewModel.getCategories().observe(viewLifecycleOwner, {
                if (it != null) {
                    if (it.code == 200) {
                        //set data category into spinner
                        val result = it.result as MutableList
                        val spinnerAdapter = ArrayAdapter(
                            requireActivity(),
                            android.R.layout.simple_spinner_item,
                            result
                        )
                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        with(spinner) {
                            adapter = spinnerAdapter
                            onItemSelectedListener = this@SymptompFragment
                            setSelection(0)
                        }
                    } else {
                        showMessage(
                            requireActivity(),
                            getString(R.string.message_title_failed),
                            it.message,
                            style = MotionToast.TOAST_ERROR
                        )
                    }
                } else {
                    showMessage(
                        requireActivity(),
                        getString(R.string.message_title_failed),
                        style = MotionToast.TOAST_ERROR
                    )
                }
            })
        }
    }

//    private fun parseToListString(data: List<ResultCategory>?): List<String> {
//        val result = mutableListOf<String>()
//        data?.forEach { item ->
//            result.add(item.gejalaKategori?: "-")
//        }
//        return result
//    }

    private fun getSymptopByCategory(idGejalaKategori: Int?) {
        viewModel.getSymptomp(idGejalaKategori ?: 0).observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.code == 200) {
                    symptompAdapter.setData(it.result)
                } else {
                    showMessage(
                        requireActivity(),
                        getString(R.string.message_title_failed),
                        it.message,
                        style = MotionToast.TOAST_ERROR
                    )
                }
            } else {
                showMessage(
                    requireActivity(),
                    getString(R.string.message_title_failed),
                    style = MotionToast.TOAST_ERROR
                )
            }
        })
    }

    private fun saveSymptomp() {
        // untuk menyimpan nilai variabel gejala
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val category = parent?.selectedItem as ResultCategory
        getSymptopByCategory(category.idGejalaKategori)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        showMessage(
            requireActivity(),
            getString(R.string.message_title_warning),
            "Tidak Ada kategori yang dipilih",
            style = MotionToast.TOAST_WARNING
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}