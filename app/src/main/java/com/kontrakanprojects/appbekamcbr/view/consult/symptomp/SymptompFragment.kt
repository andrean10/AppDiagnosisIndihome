package com.kontrakanprojects.appbekamcbr.view.consult.symptomp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kontrakanprojects.appbekamcbr.R
import com.kontrakanprojects.appbekamcbr.databinding.FragmentSymptompBinding
import com.kontrakanprojects.appbekamcbr.model.category.ResultCategory
import com.kontrakanprojects.appbekamcbr.model.consult.ResultConsult
import com.kontrakanprojects.appbekamcbr.model.symptoms.ResultSymptoms
import com.kontrakanprojects.appbekamcbr.utils.showMessage
import com.kontrakanprojects.appbekamcbr.view.consult.viewmodel.SymptompViewModel
import www.sanju.motiontoast.MotionToast

class SymptompFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentSymptompBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<SymptompViewModel>()
    private lateinit var symptompAdapter: SymptompAdapter
    private var listSelectedSymptoms = ArrayList<String>()
    private lateinit var data: ResultConsult
    private lateinit var categories: ArrayList<ResultCategory>

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
        data = SymptompFragmentArgs.fromBundle(arguments as Bundle).resultConsult

        with(binding) {
            btnSymptompNext.setOnClickListener { nextCategory() }
            btnSymptompNext.setOnClickListener { moveToDiagnosis() }
            symptompAdapter = SymptompAdapter()
            with(rvSymptompList) {
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                this.adapter = symptompAdapter
            }
        }
        loadCategory()
        symptompAdapter.setOnItemClickCallback(object : SymptompAdapter.OnItemClickCallback {
            override fun onItemSelected(symptom: ResultSymptoms) {
                //store checked id and remove in listUnselected if exist
                symptom.isSelected = true
                listSelectedSymptoms.add(symptom.idGejala ?: "")
            }

            override fun onItemUnSelected(symptom: ResultSymptoms) {
                //remove stored id in listSelected when unchecked and store in listUnselected
                symptom.isSelected = false
                listSelectedSymptoms.remove(symptom.idGejala ?: "")
            }
        })
    }

    private fun nextCategory() {
        categories.forEachIndexed { index, resultCategory ->
            with(binding) {
                when (index) {
                    0 -> btnSymptompPrevious.visibility = View.GONE
                    categories.size - 1 -> {
                        btnSymptompNext.text = getString(R.string.text_symptomp_save)
                        btnSymptompPrevious.visibility = View.VISIBLE
                    }
                }
            }
            getSymptopByCategory(resultCategory.idGejalaKategori)
        }
    }


    private fun moveToDiagnosis() {
    }

    private fun loadCategory() {
        with(binding) {
            viewModel.getCategories().observe(viewLifecycleOwner, {
                if (it != null) {
                    if (it.code == 200) {
                        //store in arraylist
                        categories.addAll(it.result as ArrayList<ResultCategory>)
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

    private fun getSymptopByCategory(idGejalaKategori: Int?) {
        viewModel.getSymptomp(idGejalaKategori ?: 0, data.idKonsultasi?.toInt() ?: 0)
            .observe(viewLifecycleOwner, {
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

    private fun saveSymptomp(
        listSelectedSymptoms: ArrayList<String>,
        idKonsultasi: String
    ) {
        viewModel.symptompConsult(listSelectedSymptoms, idKonsultasi).observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.code == 200) {
                    showMessage(
                        requireActivity(),
                        getString(R.string.message_title_succes),
                        it.message ?: "",
                        style = MotionToast.TOAST_SUCCESS
                    )
                } else {
                    showMessage(
                        requireActivity(),
                        getString(R.string.message_title_failed),
                        it.message ?: "",
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

    private fun deleteUnselectedSymptom(idSymptom: String, idConsult: String) {
        viewModel.deleteSymptomConsult(idSymptom, idConsult).observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.code == 200) {
                    showMessage(
                        requireActivity(),
                        getString(R.string.message_title_succes),
                        it.message ?: "",
                        style = MotionToast.TOAST_SUCCESS
                    )
                } else {
                    showMessage(
                        requireActivity(),
                        getString(R.string.message_title_failed),
                        it.message ?: "",
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

    private fun deleteSymptom() {

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