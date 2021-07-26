package com.kontrakanprojects.appbekamcbr.view.info.daftarsolusi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kontrakanprojects.appbekamcbr.databinding.InfoItemBinding
import com.kontrakanprojects.appbekamcbr.model.solution.Solution
import com.kontrakanprojects.appbekamcbr.view.info.daftarsolusi.DaftarSolusiAdapter.DaftarSolusiViewHolder

class DaftarSolusiAdapter : RecyclerView.Adapter<DaftarSolusiViewHolder>() {
    private val listSolution = ArrayList<Solution>()
    private var onItemClickCallBack: OnItemClickCallBack? = null

    fun setData(listSolution: List<Solution>?) {
        if (listSolution == null) return
        this.listSolution.clear()
        this.listSolution.addAll(listSolution)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaftarSolusiViewHolder {
        val binding = InfoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DaftarSolusiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DaftarSolusiViewHolder, position: Int) {
        holder.bind(listSolution.get(position))
    }

    override fun getItemCount(): Int {
        return listSolution.size
    }

    inner class DaftarSolusiViewHolder(private val binding: InfoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(solution: Solution) {
            with(binding) {
                tvInfoKode.text = solution.kdSolusi
                tvInfoName.text = solution.nmSolusi

                clInfoItem.setOnClickListener { onItemClickCallBack?.onItemClicked(solution) }
            }
        }
    }

    interface OnItemClickCallBack {
        fun onItemClicked(solution: Solution)
    }
}