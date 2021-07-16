package com.kontrakanprojects.appbekamcbr.view.consult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kontrakanprojects.appbekamcbr.R
import com.kontrakanprojects.appbekamcbr.databinding.FragmentConsultBinding
import com.kontrakanprojects.appbekamcbr.model.consult.ResultConsult
import com.kontrakanprojects.appbekamcbr.utils.checkValue
import com.kontrakanprojects.appbekamcbr.utils.showMessage
import com.kontrakanprojects.appbekamcbr.view.consult.viewmodel.ConsultViewModel
import www.sanju.motiontoast.MotionToast

class ConsultFragment : Fragment() {

    private var _binding: FragmentConsultBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<ConsultViewModel>()

    private var isValid = false

    companion object {
        private const val NAME_IS_REQUIRED = "Nama Harus Di Isi"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConsultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnConsultCreate.setOnClickListener { createConsult() }
        }
    }

    private fun createConsult() {
        with(binding) {
            val nama = etName.text.toString().trim()

            isValid = checkValue(nama, etName, NAME_IS_REQUIRED)

            if (isValid) {
                viewModel.consult(nama).observe(viewLifecycleOwner, { response ->
                    if (response != null) {
                        if (response.code == 200) {
                            moveToSymptomp(response.result!!)
                        } else {
                            showMessage(
                                requireActivity(), getString(R.string.message_title_failed),
                                response.message, MotionToast.TOAST_ERROR
                            )
                        }
                    } else {
                        showMessage(
                            requireActivity(), getString(R.string.message_title_failed),
                            style = MotionToast.TOAST_ERROR
                        )
                    }
                })
            }
        }
    }

    private fun moveToSymptomp(result: ResultConsult) {
        val toSymptomp =
            ConsultFragmentDirections.actionConsultFragmentToSymptompFragment(result)
        findNavController().navigate(toSymptomp)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}