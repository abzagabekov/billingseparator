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
import com.example.billingseparator.database.BillDatabase
import com.example.billingseparator.databinding.ResultFragmentBinding
import kotlinx.android.synthetic.main.sample_table_row.view.*

class ResultFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: ResultFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.result_fragment, container, false)
        val application = requireNotNull(this.activity).application
        val personsDatabase = BillDatabase.getInstance(application).personDatabaseDao
        val productsDatabase = BillDatabase.getInstance(application).productDatabaseDao
        val viewModelFactory = ResultViewModelFactory(personsDatabase, productsDatabase, application)
        val resultViewModel = ViewModelProviders.of(this, viewModelFactory).get(ResultViewModel::class.java)
        binding.resultViewModel = resultViewModel

        showResults(resultViewModel, inflater, container, binding)

        return binding.root
    }

    private fun showResults(
        resultViewModel: ResultViewModel,
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: ResultFragmentBinding
    ) {
        var summary: Double = 0.0
        resultViewModel.getParticipants().observe(this, Observer {
            it.forEach {
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
        })

    }


}
