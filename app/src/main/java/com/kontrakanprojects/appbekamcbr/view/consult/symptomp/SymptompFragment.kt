package com.kontrakanprojects.appbekamcbr.view.consult.symptomp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kontrakanprojects.appbekamcbr.R

class SymptompFragment : Fragment() {

    companion object {
        fun newInstance() = SymptompFragment()
    }

    private lateinit var viewModel: SymptompViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.symptomp_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SymptompViewModel::class.java)
        // TODO: Use the ViewModel
    }

}