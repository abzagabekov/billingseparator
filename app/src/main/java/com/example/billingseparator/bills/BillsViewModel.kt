package com.example.billingseparator.bills

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.billingseparator.database.bills.Bill
import com.example.billingseparator.database.bills.BillDatabaseDao
import com.example.billingseparator.newBillNamePrefix
import kotlinx.coroutines.*

class BillsViewModel(private val billDatabase: BillDatabaseDao, application: Application) : AndroidViewModel(application) {

    val bills = billDatabase.getAllBills()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToParams = MutableLiveData<Long>()
    val navigateToParams: LiveData<Long>
        get() = _navigateToParams

    fun onBillAdd() {
        uiScope.launch {
            val newBill = Bill(billName = newBillNamePrefix)
            insert(newBill)
            _navigateToParams.value = newBill.billId
        }
    }

    private suspend fun insert(bill: Bill) {
        withContext(Dispatchers.IO) {
            billDatabase.insert(bill)
        }
    }

    fun onBillClicked(billId: Long) {
        _navigateToParams.value = billId
    }

    fun onParamsNavigated() {
        _navigateToParams.value = null
    }

    fun onBillsDelete(bills: LongArray) {
        uiScope.launch {
            bills.forEach {
                delete(it)
            }
        }
    }

    private suspend fun delete(billId: Long) {
        withContext(Dispatchers.IO) {
            billDatabase.delete(billId)
        }
    }
}