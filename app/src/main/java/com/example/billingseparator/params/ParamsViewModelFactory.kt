package com.example.billingseparator.params

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.billingseparator.database.persons.PersonDatabaseDao

class ParamsViewModelFactory(
    private val dataSource: PersonDatabaseDao,
    private val application: Application,
    private val billId: Long): ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ParamsViewModel::class.java)) {
            return ParamsViewModel(dataSource, application, billId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}