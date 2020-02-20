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

    private val fragmentJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + fragmentJob)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: TitleFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.title_fragment, container, false)
        val application = requireNotNull(this.activity).application
        val billDatabase = BillDatabase.getInstance(application).billDatabaseDao

        binding.btnStart.setOnClickListener {

            uiScope.launch {
                val firstBill = getBill(1, billDatabase)
                if (firstBill == null) {
                    insert(billDatabase, Bill(billName = "FirstBill"))
                }
                findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToParamsFragment())
            }
        }

        return binding.root
    }

    private suspend fun insert(database: BillDatabaseDao, bill: Bill) {
        withContext(Dispatchers.IO) {
            database.insert(bill)
        }
    }

    private suspend fun getBill(key: Long, billDatabaseDao: BillDatabaseDao): Bill? {
        var res: Bill? = null
        withContext(Dispatchers.IO) {
            res = billDatabaseDao.get(key)
        }
        return res
    }


}
