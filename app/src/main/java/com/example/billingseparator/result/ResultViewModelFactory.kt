package com.example.billingseparator.result

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.billingseparator.database.persons.PersonDatabaseDao

class ResultViewModelFactory(private val personsDatabase: PersonDatabaseDao,
                             private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            return ResultViewModel(personsDatabase, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}