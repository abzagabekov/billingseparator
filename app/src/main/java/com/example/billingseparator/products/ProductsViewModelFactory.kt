package com.example.billingseparator.products

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.billingseparator.database.persons.PersonDatabaseDao
import com.example.billingseparator.database.products.ProductDatabaseDao
import com.example.billingseparator.params.ParamsViewModel
import javax.sql.DataSource

class ProductsViewModelFactory(private val dataSource: ProductDatabaseDao,
                               private val personsData: PersonDatabaseDao,
                               private val application: Application,
                               private val billId: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductsViewModel::class.java)) {
            return ProductsViewModel(dataSource, personsData, application, billId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}