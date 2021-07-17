package com.kontrakanprojects.appbekamcbr.view.consult.symptomp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kontrakanprojects.appbekamcbr.databinding.SymptompItemBinding
import com.kontrakanprojects.appbekamcbr.model.symptoms.ResultSymptoms

class SymptompAdapter : RecyclerView.Adapter<SymptompAdapter.SymptompViewHolder>() {
    private val listSymptomp = ArrayList<ResultSymptoms>()

    fun setData(listSymptomp: List<ResultSymptoms>?) {
        if (listSymptomp == null) return
        this.listSymptomp.clear()
        this.listSymptomp.addAll(listSymptomp)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymptompViewHolder {
        val binding =
            SymptompItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SymptompViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SymptompViewHolder, position: Int) {
        holder.bind(listSymptomp[position])
    }

    override fun getItemCount(): Int {
        return listSymptomp.size
    }

    inner class SymptompViewHolder(private val binding: SymptompItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(symptom: ResultSymptoms) {
            with(binding) {
                symptomCode.text = symptom.kdGejala
                symptompName.text = symptom.nmGejala
            }
        }

    }

    interface onItemClickCallback {
        fun onItemClicked()
    }
}