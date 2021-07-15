package com.kontrakanprojects.appbekamcbr.view.info.daftarpenyakit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kontrakanprojects.appbekamcbr.databinding.InfoItemBinding
import com.kontrakanprojects.appbekamcbr.model.disease.Disease

class DaftarPenyakitAdapter: RecyclerView.Adapter<DaftarPenyakitAdapter.DaftarPenyakitViewHolder> () {
    private lateinit var listDisease: ArrayList<Disease>
    private lateinit var onItemClickCallBack: OnItemClickCallBack

    fun setData(listDisease: ArrayList<Disease>){
        this.listDisease = listDisease
        notifyDataSetChanged()
    }

    fun setItemClickCallBack(onItemClickCallBack: OnItemClickCallBack){
        this.onItemClickCallBack = onItemClickCallBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaftarPenyakitViewHolder {
        val binding = InfoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DaftarPenyakitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DaftarPenyakitViewHolder, position: Int) {
        holder.bind(listDisease.get(position))
    }

    override fun getItemCount(): Int {
        return listDisease.size
    }

    inner class DaftarPenyakitViewHolder(private val binding:  InfoItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(disease: Disease) {
            with(binding){
                tvInfoKode.text = disease.kdPenyakit
                tvInfoName.text = disease.nmPenyakit

                btnInfoDetail.setOnClickListener{onItemClickCallBack.onItemClicked(disease)}
            }
        }

    }

    interface OnItemClickCallBack{
        fun onItemClicked(disease: Disease)
    }
}


