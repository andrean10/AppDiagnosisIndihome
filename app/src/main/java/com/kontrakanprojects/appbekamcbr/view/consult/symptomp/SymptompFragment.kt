package com.kontrakanprojects.appbekamcbr.view.consult.symptomp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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
import com.kontrakanprojects.appbekamcbr.utils.snackbar
import com.kontrakanprojects.appbekamcbr.view.consult.viewmodel.SymptompViewModel
import www.sanju.motiontoast.MotionToast

class SymptompFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSymptompBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<SymptompViewModel>()
    private lateinit var symptompAdapter: SymptompAdapter
    private var listSelectedIds = ArrayList<String>()
    private var listSelectedSymptomps = ArrayList<ResultSymptoms>()
    private lateinit var data: ResultConsult
    private lateinit var categories: ArrayList<ResultCategory>
    private var index = 0
    private var numSelectedSymptomps = 0

    private val TAG = SymptompFragment::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
        setToolbar()
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
            if (categories.size == 1) {
                when (index) {
                    0 -> {
                        btnSymptompDiagnosis.visibility = View.VISIBLE
                        btnSymptompPrevious.visibility = View.INVISIBLE
                        btnSymptompNext.visibility = View.GONE
                    }
                }
            } else {
                when (index) {
                    0 -> btnSymptompPrevious.visibility = View.INVISIBLE
                    categories.size - 1 -> {
                        btnSymptompDiagnosis.visibility = View.VISIBLE
                        btnSymptompPrevious.visibility = View.VISIBLE
                        btnSymptompNext.visibility = View.GONE
                    }
                    else -> {
                        btnSymptompPrevious.visibility = View.VISIBLE
                        btnSymptompNext.visibility = View.VISIBLE
                        btnSymptompDiagnosis.visibility = View.GONE
                    }
                }
            }

            setTitle(categories[index].gejalaKategori.toString())
        }

        symptompAdapter.setOnItemClickCallback(object : SymptompAdapter.OnItemClickCallback {
            override fun onItemSelected(symptom: ResultSymptoms) {
                //store checked id and remove in listUnselected if exist
                symptom.isSelected = true
                listSelectedIds.add(symptom.idGejala.toString())
                listSelectedSymptomps.add(symptom)
                numSelectedSymptomps++
            }

            override fun onItemUnSelected(symptom: ResultSymptoms) {
                //remove stored id in listSelected when unchecked and store in listUnselected
                symptom.isSelected = false
                listSelectedIds.remove(symptom.idGejala ?: "")
                listSelectedSymptomps.remove(symptom)
                numSelectedSymptomps--
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_symptomp_next -> {
                if (hasSelectingAnySymptomp()) nextCategory()
            }
            R.id.btn_symptomp_previous -> {
                if (hasSelectingAnySymptomp()) previousCategory()
            }
            R.id.btn_symptomp_diagnosis -> {
                if (hasSelectingAnySymptomp()) {
                    saveSymptomp(listSelectedIds, data.idKonsultasi.toString())
                    moveToResultDiagnosis(data.idKonsultasi.toString())
                    storeSelectedDataTemp()
                }
            }
        }
    }

    private fun hasSelectingAnySymptomp(): Boolean {
        var state = false
        if (numSelectedSymptomps == 0) {
            binding.root.snackbar("Anda memilih setidaknya satu gejala.")
        } else {
            state = true
        }
        return state
    }

    private fun storeSelectedDataTemp() {
        viewModel.setSelectedIdsSymtomps(listSelectedIds)
        viewModel.setSelectedSymptomps(listSelectedSymptomps)
    }

    private fun moveToResultDiagnosis(idConsult: String) {
        val toResultDiagnosis =
            SymptompFragmentDirections.actionSymptompFragmentToResultFragment(idConsult)
        findNavController().navigate(toResultDiagnosis)
    }

    private fun nextCategory() {
        if (categories.size != 1) index++
        numSelectedSymptomps = 0
        if (index != categories.size) getSymptopByCategory(categories[index].idGejalaKategori.toString())
    }

    private fun previousCategory() {
        if (categories.size != 1) index--
        numSelectedSymptomps = 0
        if (index != -1) getSymptopByCategory(categories[index].idGejalaKategori.toString())
    }

    private fun loadCategory() {
        viewModel.getCategories().observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.code == 200) {
                    //store in arraylist
                    categories.addAll(it.result as ArrayList<ResultCategory>)
                    //force to load data the first symptomp category
                    getSymptopByCategory(categories[index].idGejalaKategori.toString())
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

    private fun getSymptopByCategory(idGejalaKategori: String) {
        viewModel.getSymptomp(idGejalaKategori, data.idKonsultasi.toString())
            .observe(viewLifecycleOwner, {
                if (it != null) {
                    if (it.code == 200) {
                        // jika dia kembali maka check user saat checklis item sebelumnya
                        // dengan cara mengecek data result yang sudah ditambahkan
                        // check dulu jika user ada memilih gejala
                        listSelectedIds.forEachIndexed { _, idGejala ->
                            it.result!!.forEach { resultSymptoms ->
                                if (resultSymptoms.idGejala == idGejala) {
                                    resultSymptoms.isSelected = true
                                    numSelectedSymptomps++
                                }
                            }
                        }

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

    private fun setToolbar() {
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbar)
        if ((activity as AppCompatActivity?)!!.supportActionBar != null) {
            (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setTitle(titleGejala: String) {
        if ((activity as AppCompatActivity?)!!.supportActionBar != null) {
            (activity as AppCompatActivity?)!!.supportActionBar!!.title =
                "Kategori $titleGejala"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) findNavController().navigateUp()
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}