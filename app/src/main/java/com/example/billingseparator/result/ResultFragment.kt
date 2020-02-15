package com.example.billingseparator.result


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.billingseparator.R
import com.example.billingseparator.databinding.ResultFragmentBinding
import kotlinx.android.synthetic.main.sample_table_row.view.*

class ResultFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: ResultFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.result_fragment, container, false)
        val resultViewModel = ViewModelProviders.of(this).get(ResultViewModel::class.java)
        binding.resultViewModel = resultViewModel

        var summary: Double = 0.0
        resultViewModel.getParticipants()?.forEach {
            val newRow =
                inflater.inflate(R.layout.sample_table_row, container, false) as TableRow
            newRow.tv_product_item.text = it.name
            newRow.tv_price_item.text = it.billSummary.toString()
            summary = summary.plus(it.billSummary)
            binding.tableResult.addView(newRow)
        }

        val newRow =
            inflater.inflate(R.layout.sample_table_row, container, false) as TableRow
        newRow.tv_price_item.text = "${getString(R.string.sumary)} ${summary.toString()}"
        newRow.tv_product_item.text = ""
        binding.tableResult.addView(newRow)

        return binding.root
    }

}
