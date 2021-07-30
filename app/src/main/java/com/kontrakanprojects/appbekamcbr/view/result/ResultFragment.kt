package com.kontrakanprojects.appbekamcbr.view.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kontrakanprojects.appbekamcbr.R
import com.kontrakanprojects.appbekamcbr.databinding.FragmentResultBinding
import com.kontrakanprojects.appbekamcbr.model.disease.Disease
import com.kontrakanprojects.appbekamcbr.model.disease.ResultsDiseaseSolution
import com.kontrakanprojects.appbekamcbr.utils.isLoading
import com.kontrakanprojects.appbekamcbr.utils.showMessage
import www.sanju.motiontoast.MotionToast

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<ResultViewModel>()
    private lateinit var resultAdapter: ResultAdapter
    private lateinit var resultSolutionAdapter: ResultSolutionAdapter
    private lateinit var dataIdConsult: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            buttonResultFinish.setOnClickListener { finishConsultation() }
            resultAdapter = ResultAdapter()
            with(rvResultCase) {
                layoutManager =
                    LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                this.adapter = resultAdapter
            }
        }
        //data from navigation
        dataIdConsult = ResultFragmentArgs.fromBundle(arguments as Bundle).idConsultation

        observeResult(dataIdConsult)
    }

    private fun finishConsultation() {
        findNavController().navigate(R.id.action_resultFragment_to_homeFragment)
    }

    private fun observeResult(idConsult: String) {
        viewModel.result(idConsult).observe(viewLifecycleOwner, {
            isLoading(false, binding.progressBarResult)
            if (it != null) {
                if (it.code == 200) {
                    resultAdapter.setData(it.result)
                    binding.tvResultDescription.text =
                        convertToSentece(resultAdapter.getDiseaseDiagnosis())
                    observeSolution(resultAdapter.getDiseaseDiagnosis()?.idPenyakit.toString())
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

    private fun observeSolution(idDisease: String) {
        viewModel.solutions(idDisease).observe(viewLifecycleOwner, {
            isLoading(false, binding.progressBarResult)
            if (it != null) {
                if (it.code == 200) {
                    loadListViewSolution(it.result)
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

    private fun loadListViewSolution(result: List<ResultsDiseaseSolution>?) {
        if (result != null) {
            with(binding) {
                resultSolutionAdapter = ResultSolutionAdapter()
                with(rvSolusi) {
                    layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL, false
                    )
                    setHasFixedSize(true)
                    this.adapter = resultSolutionAdapter
                }

                resultSolutionAdapter.setData(result)
            }
        }
    }

    private fun convertToSentece(disease: Disease?): String {
        var sentece = ""
        if (disease != null) {
            val stringBuilder = StringBuilder()
            stringBuilder.append("Hasil Diagnosa Adalah ")
            stringBuilder.append(disease.nmPenyakit)
            stringBuilder.append(".\t")
            stringBuilder.append(disease.definisi)
            sentece = stringBuilder.toString()
        }
        return sentece
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}