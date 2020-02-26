package com.example.billingseparator.bills


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.billingseparator.R
import com.example.billingseparator.database.BillDatabase
import com.example.billingseparator.databinding.BillsFragmentBinding


class BillsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.bills_fragment, container, false)
        val binding: BillsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.bills_fragment, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = BillDatabase.getInstance(application).billDatabaseDao
        val viewModelFactory = BillsViewModelFactory(dataSource, application)
        val billsViewModel = ViewModelProviders.of(this, viewModelFactory).get(BillsViewModel::class.java)

        binding.billsViewModel = billsViewModel
        binding.lifecycleOwner = this

        val adapter = BillsAdapter()

        binding.rvBillsList.adapter = adapter

        billsViewModel.bills.observe(viewLifecycleOwner, Observer {
            it.let {
                adapter.data = it
            }
        })

        return binding.root
    }


}
