package com.example.billingseparator.title


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController

import com.example.billingseparator.R
import com.example.billingseparator.database.BillDatabase
import com.example.billingseparator.database.bills.Bill
import com.example.billingseparator.database.bills.BillDatabaseDao
import com.example.billingseparator.databinding.TitleFragmentBinding
import kotlinx.coroutines.*


class TitleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: TitleFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.title_fragment, container, false)

        binding.btnStart.setOnClickListener {
            findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToBillsFragment())
        }

        return binding.root
    }

}
