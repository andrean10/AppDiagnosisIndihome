package com.kontrakanprojects.appbekamcbr.view.info.daftarpenyakit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kontrakanprojects.appbekamcbr.R
import com.kontrakanprojects.appbekamcbr.databinding.FragmentDaftarPenyakitBinding
import com.kontrakanprojects.appbekamcbr.model.disease.Disease
import com.kontrakanprojects.appbekamcbr.utils.isLoading
import com.kontrakanprojects.appbekamcbr.utils.showMessage
import www.sanju.motiontoast.MotionToast

class DaftarPenyakitFragment : Fragment() {

    private var _binding: FragmentDaftarPenyakitBinding? = null
    private val binding get() = _binding!!
    private val viewmodel by viewModels<DaftarPenyakitViewModel>()
    private lateinit var diseasesAdapter: DaftarPenyakitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDaftarPenyakitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            diseasesAdapter = DaftarPenyakitAdapter()
            with(rvDaftarPenyakitList){
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                this.adapter = diseasesAdapter
            }
            observeDisease()
            diseasesAdapter.setOnItemClickCallBack(object : DaftarPenyakitAdapter.OnItemClickCallBack {
                override fun onItemClicked(disease: Disease) {
                    // send to db parcel
                    val dataDisease = Disease(
                        disease.nmPenyakit,
                        disease.definisi,
                        disease.kdPenyakit,
                        disease.idPenyakit,
                    )
                    //moving to detail activity

                }
            })
        }

    }

    private fun observeDisease(){
        with(binding){
            viewmodel.getListDisease().observe(viewLifecycleOwner, { response ->
                isLoading(false, progressBar)
                if (response != null) {
                    if (response.code == 200) {
                        val result = response.result
                        diseasesAdapter.setData(result)
                    } else {
                        dataDiseaseNotFoound()
                        showMessage(
                            requireActivity(),
                            getString(R.string.message_title_failed),
                            response.message,
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

    private fun dataDiseaseNotFoound() {
        with(binding) {
            progressBar.visibility = View.GONE
            tvDaftarPenyakitNotFound.visibility = View.VISIBLE
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}