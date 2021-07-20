package com.kontrakanprojects.appbekamcbr.view.consult.symptomp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kontrakanprojects.appbekamcbr.R
import com.kontrakanprojects.appbekamcbr.databinding.FragmentSymptompBinding
import com.kontrakanprojects.appbekamcbr.model.category.ResultCategory
import com.kontrakanprojects.appbekamcbr.model.consult.ResultConsult
import com.kontrakanprojects.appbekamcbr.model.symptoms.ResultSymptoms
import com.kontrakanprojects.appbekamcbr.utils.showMessage
import com.kontrakanprojects.appbekamcbr.view.consult.viewmodel.SymptompViewModel
import www.sanju.motiontoast.MotionToast

class SymptompFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSymptompBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<SymptompViewModel>()
    private lateinit var symptompAdapter: SymptompAdapter
    private var listSelectedSymptoms = ArrayList<String>()
    private lateinit var data: ResultConsult
    private lateinit var categories: ArrayList<ResultCategory>
    private var index = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSymptompBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        // ambil data dari navigation
        data = SymptompFragmentArgs.fromBundle(arguments as Bundle).resultConsult
    }

    private fun initViews() {
        symptompAdapter = SymptompAdapter()
        categories = ArrayList()
        with(binding) {
            btnSymptompNext.setOnClickListener(this@SymptompFragment)
            btnSymptompPrevious.setOnClickListener(this@SymptompFragment)
            btnSymptompDiagnosis.setOnClickListener(this@SymptompFragment)
            with(rvSymptompList) {
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                this.adapter = symptompAdapter
            }
        }
        loadCategory()
    }

    private fun prepareViews() {
        with(binding) {
            //show-hide buttons based category index
            when (index) {
                0 -> btnSymptompPrevious.visibility = View.INVISIBLE
                categories.size - 1 -> {
                    btnSymptompDiagnosis.visibility = View.VISIBLE
                    btnSymptompNext.visibility = View.GONE
                }
                else -> {
                    btnSymptompPrevious.visibility = View.VISIBLE
                    btnSymptompNext.visibility = View.VISIBLE
                    btnSymptompDiagnosis.visibility = View.GONE
                }
            }
            tvSymptompCategory.text = categories[index].gejalaKategori
        }

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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_symptomp_next -> nextCategory()
            R.id.btn_symptomp_previous -> previousCategory()
            R.id.btn_symptomp_diagnosis -> moveToResultDiagnosis(data.idKonsultasi?.toInt() ?: 0)
        }
    }

    private fun moveToResultDiagnosis(idConsult: Int) {
        val toResultDiagnosis =
            SymptompFragmentDirections.actionSymptompFragmentToResultFragment(idConsult)
        findNavController().navigate(toResultDiagnosis)
    }

    private fun nextCategory() {
        index++
        if (index != categories.size) getSymptopByCategory(categories[index].idGejalaKategori)
    }

    private fun previousCategory() {
        index--
        if (index != -1) getSymptopByCategory(categories[index].idGejalaKategori)
    }

    private fun loadCategory() {
        viewModel.getCategories().observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.code == 200) {
                    //store in arraylist
                    categories.addAll(it.result as ArrayList<ResultCategory>)
                    //force to load data the first symptomp category
                    getSymptopByCategory(categories[index].idGejalaKategori)
                    //prepareViews
                    prepareViews()
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

    private fun getSymptopByCategory(idGejalaKategori: Int?) {
        viewModel.getSymptomp(idGejalaKategori ?: 0, data.idKonsultasi?.toInt() ?: 0)
            .observe(viewLifecycleOwner, {
                if (it != null) {
                    if (it.code == 200) {
                        symptompAdapter.setData(it.result)
                        prepareViews()
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
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}