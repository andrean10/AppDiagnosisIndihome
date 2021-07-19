package com.kontrakanprojects.appbekamcbr.view.info.detail

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kontrakanprojects.appbekamcbr.R
import com.kontrakanprojects.appbekamcbr.databinding.ActivityDetailBinding
import com.kontrakanprojects.appbekamcbr.model.disease.Disease
import com.kontrakanprojects.appbekamcbr.model.disease.ResultItem
import com.kontrakanprojects.appbekamcbr.model.solution.Solution
import com.kontrakanprojects.appbekamcbr.utils.EXTRA_OBJECT_TYPE
import com.kontrakanprojects.appbekamcbr.utils.dataNotFound
import com.kontrakanprojects.appbekamcbr.utils.showMessage
import com.kontrakanprojects.appbekamcbr.view.info.daftarpenyakit.DaftarPenyakitFragment.Companion.EXTRA_OBJECT_DISEASE
import com.kontrakanprojects.appbekamcbr.view.info.daftarsolusi.DaftarSolusiFragment.Companion.EXTRA_OBJECT_SOLUTION
import www.sanju.motiontoast.MotionToast

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewmodel by viewModels<DetailViewModel>()
    private var disease: Disease? = null
    private var solution: Solution? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get data that have been passed
        getData()
    }

    private fun getData() {
        with(binding) {
            if (intent != null) {
                if (intent.extras?.getString(EXTRA_OBJECT_TYPE)
                        .equals("disease") && intent.extras?.equals(EXTRA_OBJECT_DISEASE) != null
                ) {
                    disease = intent.extras?.getParcelable(EXTRA_OBJECT_DISEASE)
                    idTvDetailCode.text = disease?.kdPenyakit
                    idTvDetailName.text = disease?.nmPenyakit
                    tvDetailInfo.text = disease?.definisi

                    //show solusi section
                    tvDetailLabelSolusi.visibility = View.VISIBLE
                    listviewDetail.visibility = View.VISIBLE

                    observeDiseaseSolutions(disease?.idPenyakit ?: 0)
                } else if (intent.extras?.getString(EXTRA_OBJECT_TYPE)
                        .equals("solution") && intent.extras?.equals(EXTRA_OBJECT_SOLUTION) != null
                ) {
                    solution = intent.extras?.getParcelable(EXTRA_OBJECT_SOLUTION)
                    idTvDetailCode.text = solution?.kdSolusi
                    idTvDetailName.text = solution?.nmSolusi
                    tvDetailInfo.text = solution?.keterangan
                } else {
                    showMessage(
                        this@DetailActivity,
                        getString(R.string.message_title_failed),
                        "Tidak ada data yang di pass",
                        style = MotionToast.TOAST_ERROR
                    )
                }
            }
        }
    }

    private fun observeDiseaseSolutions(idPenyakit: Int) {
        with(binding) {
            viewmodel.getDiseaseSolution(idPenyakit).observe(this@DetailActivity, {
                if (it != null) {
                    if (it.code == 200) {
                        val listSolution = parseToListString(it.result)
                        val listAdapter = ArrayAdapter(
                            this@DetailActivity,
                            android.R.layout.simple_list_item_1,
                            android.R.id.text1,
                            listSolution
                        )
                        listviewDetail.adapter = listAdapter
                    } else {
                        dataNotFound(
                            tvDetailNotFound,
                            it.message
                        )
                        showMessage(
                            this@DetailActivity,
                            getString(R.string.message_title_failed),
                            style = MotionToast.TOAST_ERROR
                        )
                    }
                } else {
                    dataNotFound(
                        tvDetailNotFound
                    )
                    showMessage(
                        this@DetailActivity,
                        getString(R.string.message_title_failed),
                        style = MotionToast.TOAST_ERROR
                    )
                }
            })
        }
    }

    private fun parseToListString(result: List<ResultItem?>?): List<String> {
        val list: MutableList<String> = mutableListOf()
        result?.forEach { item ->
            val stringBuilder = StringBuilder()
            stringBuilder.append("[")
            stringBuilder.append(item?.solution?.kdSolusi)
            stringBuilder.append("]")
            stringBuilder.append("\t")
            stringBuilder.append(item?.solution?.nmSolusi)
            list.add(stringBuilder.toString())
        }
        return list
    }
}