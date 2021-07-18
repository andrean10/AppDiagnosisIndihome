package com.kontrakanprojects.appbekamcbr.view.consult.symptomp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kontrakanprojects.appbekamcbr.databinding.SymptompItemBinding
import com.kontrakanprojects.appbekamcbr.model.symptoms.ResultSymptoms

class SymptompAdapter : RecyclerView.Adapter<SymptompAdapter.SymptompViewHolder>() {
    private val listSymptomp = ArrayList<ResultSymptoms>()
    private val listSelectedSymptomp = ArrayList<ResultSymptoms>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setData(listSymptomp: List<ResultSymptoms>?) {
        if (listSymptomp == null) return
        this.listSymptomp.clear()
        this.listSymptomp.addAll(listSymptomp)
        notifyDataSetChanged()
    }


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymptompViewHolder {
        val binding =
            SymptompItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SymptompViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SymptompViewHolder, position: Int) {
        holder.bind(listSymptomp[position])
        //avoid checkbox randomly checked so always checking its state with value isSelected
        holder.mItem = listSymptomp[position]
        holder.setChecked(listSymptomp[position].isSelected)
    }

    override fun getItemCount(): Int {
        return listSymptomp.size
    }

    inner class SymptompViewHolder(private val binding: SymptompItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        lateinit var mItem: ResultSymptoms


        fun bind(symptom: ResultSymptoms) {
            with(binding) {
                symptomCode.text = symptom.kdGejala
                symptompName.text = symptom.nmGejala

                clSymptomItem.setOnClickListener {
                    if (symptom.isSelected) {
                        checkBox.isChecked = false
                        onItemClickCallback?.onItemUnSelected(symptom)
                    } else {
                        checkBox.isChecked = true
                        onItemClickCallback?.onItemSelected(symptom)
                    }
                }
            }
        }

        fun setChecked(value: Boolean) {
            with(binding) {
                mItem.isSelected = value
                checkBox.isChecked = value
            }
        }

    }

    interface OnItemClickCallback {
        fun onItemSelected(symptom: ResultSymptoms)
        fun onItemUnSelected(symptom: ResultSymptoms)
    }
}