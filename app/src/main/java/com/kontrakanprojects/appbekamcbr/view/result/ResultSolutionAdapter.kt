package com.kontrakanprojects.appbekamcbr.view.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kontrakanprojects.appbekamcbr.R
import com.kontrakanprojects.appbekamcbr.databinding.SolusiItemBinding
import com.kontrakanprojects.appbekamcbr.model.disease.Disease
import com.kontrakanprojects.appbekamcbr.model.disease.ResultsDiseaseSolution

class ResultSolutionAdapter :
    RecyclerView.Adapter<ResultSolutionAdapter.ResultSolutionViewHolder>() {
    private val listResultDisease = ArrayList<ResultsDiseaseSolution>()
    private var diseaseResult: Disease? = null

    fun setData(result: List<ResultsDiseaseSolution>?) {
        if (result == null) return
        this.listResultDisease.clear()
        this.listResultDisease.addAll(result)
        notifyDataSetChanged()
    }

    private fun parseToListString(result: ResultsDiseaseSolution): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("[")
        stringBuilder.append(result.solution?.kdSolusi)
        stringBuilder.append("]")
        stringBuilder.append("\t")
        stringBuilder.append(result.solution?.nmSolusi)
        return stringBuilder.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultSolutionViewHolder {
        val binding = SolusiItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResultSolutionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultSolutionViewHolder, position: Int) {
        holder.bind(listResultDisease[position])
    }

    override fun getItemCount() = listResultDisease.size

    inner class ResultSolutionViewHolder(private val binding: SolusiItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(resultsDiseaseSolution: ResultsDiseaseSolution) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(resultsDiseaseSolution.solution?.gambar)
                    .placeholder(R.drawable.img_not_found)
                    .error(R.drawable.img_not_found)
                    .into(imgSolusi)

                tvSolution.text = parseToListString(resultsDiseaseSolution)
            }
        }
    }
}