package com.example.billingseparator.bills

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.billingseparator.database.bills.Bill
import com.example.billingseparator.database.bills.BillDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class BillsViewModel(private val billDatabase: BillDatabaseDao, application: Application) : AndroidViewModel(application) {

    val bills = billDatabase.getAllBills()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun onBillAdd() {

    }

    private suspend fun insert(bill: Bill) {
        withContext(Dispatchers.IO) {
            billDatabase.insert(bill)
        }
    }
}