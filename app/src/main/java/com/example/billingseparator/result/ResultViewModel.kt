package com.example.billingseparator.result

import android.app.Application
import androidx.lifecycle.*
import com.example.billingseparator.database.persons.Person
import com.example.billingseparator.database.persons.PersonDatabaseDao
import com.example.billingseparator.database.products.Product
import com.example.billingseparator.database.products.ProductDatabaseDao
import kotlinx.coroutines.*

class ResultViewModel(private val personsDatabase: PersonDatabaseDao,
                      private val productsDatabase: ProductDatabaseDao,
                      application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val products = productsDatabase.getAllProducts()
    private val participants = personsDatabase.getAllPersons()

    fun getParticipants() = participants


}