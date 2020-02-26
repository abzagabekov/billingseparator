package com.example.billingseparator.bills

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.billingseparator.database.bills.BillDatabaseDao

class BillsViewModelFactory(
    private val dataSource: BillDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BillsViewModel::class.java)) {
            return BillsViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}