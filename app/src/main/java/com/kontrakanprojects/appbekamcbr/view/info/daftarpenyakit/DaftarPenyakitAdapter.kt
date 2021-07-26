package com.kontrakanprojects.appbekamcbr.view.info.daftarpenyakit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kontrakanprojects.appbekamcbr.databinding.InfoItemBinding
import com.kontrakanprojects.appbekamcbr.model.disease.Disease

class DaftarPenyakitAdapter :
    RecyclerView.Adapter<DaftarPenyakitAdapter.DaftarPenyakitViewHolder>() {
    private val listDisease = ArrayList<Disease>()
    private var onItemClickCallBack: OnItemClickCallBack? = null

    fun setData(listDisease: List<Disease>?) {
        if (listDisease == null) return
        this.listDisease.clear()
        this.listDisease.addAll(listDisease)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaftarPenyakitViewHolder {
        val binding = InfoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DaftarPenyakitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DaftarPenyakitViewHolder, position: Int) {
        holder.bind(listDisease.get(position))
    }

    override fun getItemCount(): Int {
        return listDisease.size
    }

    inner class DaftarPenyakitViewHolder(private val binding: InfoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(disease: Disease) {
            with(binding) {
                tvInfoKode.text = disease.kdPenyakit
                tvInfoName.text = disease.nmPenyakit

                clInfoItem.setOnClickListener { onItemClickCallBack?.onItemClicked(disease) }
            }
        }

    }

    interface OnItemClickCallBack {
        fun onItemClicked(disease: Disease)
    }
}


