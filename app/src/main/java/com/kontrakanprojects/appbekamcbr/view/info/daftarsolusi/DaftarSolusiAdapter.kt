package com.kontrakanprojects.appbekamcbr.view.info.daftarsolusi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kontrakanprojects.appbekamcbr.databinding.InfoItemBinding
import com.kontrakanprojects.appbekamcbr.model.solution.Solution
import com.kontrakanprojects.appbekamcbr.view.info.daftarsolusi.DaftarSolusiAdapter.*

class DaftarSolusiAdapter: RecyclerView.Adapter<DaftarSolusiViewHolder>() {
    private lateinit var listSolution: ArrayList<Solution>
    private lateinit var onItemClickCallBack: OnItemClickCallBack

    fun setData(listSolution: List<Solution>?){
        if(listSolution == null) return
        this.listSolution.clear()
        this.listSolution.addAll(listSolution)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack){
        this.onItemClickCallBack = onItemClickCallBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaftarSolusiViewHolder {
        val binding = InfoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DaftarSolusiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DaftarSolusiViewHolder, position: Int) {
        holder.bind(listSolution.get(position))
    }

    override fun getItemCount(): Int {
        return listSolution.size
    }

    inner class DaftarSolusiViewHolder(private val binding: InfoItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(solution: Solution) {
            with(binding){
                tvInfoKode.text = solution.kdSolusi
                tvInfoName.text = solution.nmSolusi

                btnInfoDetail.setOnClickListener{onItemClickCallBack.onItemClicked(solution)}
            }
        }
    }

    interface OnItemClickCallBack{
        fun onItemClicked(solution: Solution)
    }
}