package com.kontrakanprojects.appbekamcbr.view.info.daftarsolusi

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kontrakanprojects.appbekamcbr.R
import com.kontrakanprojects.appbekamcbr.databinding.FragmentDaftarSolusiBinding
import com.kontrakanprojects.appbekamcbr.model.solution.Solution
import com.kontrakanprojects.appbekamcbr.utils.EXTRA_OBJECT_TYPE
import com.kontrakanprojects.appbekamcbr.utils.dataNotFound
import com.kontrakanprojects.appbekamcbr.utils.isLoading
import com.kontrakanprojects.appbekamcbr.utils.showMessage
import com.kontrakanprojects.appbekamcbr.view.info.detail.DetailActivity
import com.kontrakanprojects.appbekamcbr.view.info.viewmodel.InfoViewModel
import www.sanju.motiontoast.MotionToast

class DaftarSolusiFragment : Fragment() {

    private var _binding: FragmentDaftarSolusiBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InfoViewModel by activityViewModels()
    private lateinit var solutionAdapter: DaftarSolusiAdapter

    companion object {
        fun newInstance(): DaftarSolusiFragment {
            return DaftarSolusiFragment()
        }

        const val EXTRA_OBJECT_SOLUTION = "EXTRA_OBJECT_SOLUTION"
    }

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
        init()
    }

    private fun init() {
        with(binding) {
            solutionAdapter = DaftarSolusiAdapter()
            with(rvDaftarSolusiList) {
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                this.adapter = solutionAdapter
            }

            observeSolutions()

            solutionAdapter.setOnItemClickCallBack(object :
                DaftarSolusiAdapter.OnItemClickCallBack {
                override fun onItemClicked(solution: Solution) {
                    // send to db parcel
                    val dataSolution = Solution(
                        solution.gambar,
                        solution.keterangan,
                        solution.idSolusi,
                        solution.kdSolusi,
                        solution.nmSolusi
                    )
                    val intent = Intent(requireActivity(), DetailActivity::class.java)
                    intent.putExtra(EXTRA_OBJECT_TYPE, "solution")
                    intent.putExtra(EXTRA_OBJECT_SOLUTION, dataSolution)
                    startActivity(intent)
                }
            })
        }
    }

    private fun observeSolutions() {
        with(binding) {
            viewModel.getListSolution().observe(viewLifecycleOwner, { response ->
                isLoading(false, progressBar)
                if (response != null) {
                    if (response.code == 200) {
                        val result = response.result
                        solutionAdapter.setData(result)
                    } else {
                        dataNotFound(
                            solusiEmptyView.imgEmptyData,
                            solusiEmptyView.tvEmptyMessage,
                            response.message
                        )
                        showMessage(
                            requireActivity(),
                            getString(R.string.message_title_failed),
                            response.message,
                            style = MotionToast.TOAST_ERROR
                        )
                    }
                } else {
                    dataNotFound(solusiEmptyView.imgEmptyData, solusiEmptyView.tvEmptyMessage)
                    showMessage(
                        requireActivity(),
                        getString(R.string.message_title_failed),
                        style = MotionToast.TOAST_ERROR
                    )
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}