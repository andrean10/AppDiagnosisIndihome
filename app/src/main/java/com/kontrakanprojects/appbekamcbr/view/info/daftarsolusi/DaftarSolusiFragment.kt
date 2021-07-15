package com.kontrakanprojects.appbekamcbr.view.info.daftarsolusi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kontrakanprojects.appbekamcbr.R
import com.kontrakanprojects.appbekamcbr.databinding.FragmentDaftarSolusiBinding
import com.kontrakanprojects.appbekamcbr.model.solution.Solution
import com.kontrakanprojects.appbekamcbr.utils.isLoading
import com.kontrakanprojects.appbekamcbr.utils.showMessage
import www.sanju.motiontoast.MotionToast

class DaftarSolusiFragment : Fragment() {

    private var _binding: FragmentDaftarSolusiBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DaftarSolusiViewModel>()
    private lateinit var solutionAdapter: DaftarSolusiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDaftarSolusiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            solutionAdapter = DaftarSolusiAdapter()
            with(rvDaftarSolusiList){
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                this.adapter = solutionAdapter
            }
            observeSolutions()
            solutionAdapter.setOnItemClickCallBack(object : DaftarSolusiAdapter.OnItemClickCallBack {
                override fun onItemClicked(solution: Solution) {
                    // send to db parcel
                    val dataSolution = Solution(
                        solution.idSolusi,
                        solution.kdSolusi,
                        solution.nmSolusi,
                        solution.keterangan
                    )
                    //moving to detail activity
                }
            })
        }
    }

    private fun observeSolutions() {
        with(binding){
            viewModel.getListSolution().observe(viewLifecycleOwner,{
                isLoading(false,progressBar)
                if(it != null){
                    if(it.code == 200){

                    }else{
                        dataSolutionNotFound()
                        showMessage(
                            requireActivity(),
                            getString(R.string.message_title_failed),
                            it.message,
                            style = MotionToast.TOAST_ERROR
                        )
                    }
                }else{
                    showMessage(
                        requireActivity(),
                        getString(R.string.message_title_failed),
                        style = MotionToast.TOAST_ERROR
                    )
                }
            })
        }
    }

    private fun dataSolutionNotFound() {
        with(binding) {
            progressBar.visibility = View.GONE
            tvDaftarSolusiNotFound.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}