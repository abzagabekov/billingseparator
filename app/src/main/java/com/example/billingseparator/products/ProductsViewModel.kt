package com.example.billingseparator.products

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.example.billingseparator.database.persons.Person
import com.example.billingseparator.database.persons.PersonDatabaseDao
import com.example.billingseparator.database.products.Product
import com.example.billingseparator.database.products.ProductDatabaseDao
import com.example.billingseparator.personsToMap
import kotlinx.coroutines.*
import java.util.function.LongFunction

class ProductsViewModel(private val productsDatabase: ProductDatabaseDao,
                        private val personsDatabase: PersonDatabaseDao,
                        application: Application,
                        val billId: Long) : AndroidViewModel(application) {



    val products = productsDatabase.getProductsByBill(billId)

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val participants = personsDatabase.getPersonsByBill(billId)


    private var _eventProductAdd = false
    val eventProductAdd: Boolean
        get() = _eventProductAdd

    private var _eventProductDelete = false
    val eventProductDelete: Boolean
        get() = _eventProductDelete

    private var _deletedProduct: Product? = null
    val deletedProduct: Product?
        get() = _deletedProduct

    private val _eventNavigateToResults = MutableLiveData<Boolean>()
    val eventNavigateToResults: LiveData<Boolean>
        get() = _eventNavigateToResults

    val personsList = Transformations.map(participants) {
        personsToMap(it)
    }


    private val prodObserver = Observer<List<Product>> {
        it.forEach {
            val fraction = it.productPrice / it.productBuyers.size

            it.productBuyers.forEach {buyerId ->
                persons?.forEach {
                    it.billSummary = 0.0
                    uiScope.launch {
                        withContext(Dispatchers.IO) {
                            doCount(it, buyerId, fraction)
                            personsDatabase.update(it)
                        }
                    }
                }
            }
        }

    }

    private var persons: List<Person>? = null

    private val partObserver = Observer<List<Person>> {
        persons = it
    }

    init {
        products.observeForever(prodObserver)
        participants.observeForever(partObserver)
    }

    override fun onCleared() {
        products.removeObserver(prodObserver)
        participants.removeObserver(partObserver)
        super.onCleared()
    }

    private fun doCount(person: Person, buyerId: Long, fraction: Double) {
        if (person.personId == buyerId) {
            person.billSummary += fraction
        }
    }

    fun onProductAdd(productName: String, productPrice: Double, productBuyers: LongArray) {

        val newProduct = Product(
            name = productName,
            productPrice = productPrice,
            productBuyers = productBuyers,
            billId = billId
        )
        uiScope.launch {
            insert(newProduct)
            _eventProductAdd = true
        }
    }

    private suspend fun insert(product: Product) {
        withContext(Dispatchers.IO) {
            productsDatabase.insert(product)
        }
    }

    fun doneProductAdd() {
        _eventProductAdd = false
    }

    fun onProductDelete() {

        uiScope.launch {
            val lastProduct: Product? = getLastProduct()
            if (lastProduct != null) {
                delete(lastProduct.productId)
                _deletedProduct = lastProduct
                _eventProductDelete = true
            }
        }
    }

    private suspend fun delete(productId: Long) {
        withContext(Dispatchers.IO) {
            productsDatabase.delete(productId)
        }
    }

    private suspend fun getLastProduct(): Product? {
        var res: Product? = null
        withContext(Dispatchers.IO) {
            res = productsDatabase.getLastProduct()
        }
        return res
    }

    fun doneProductDelete() {
        _eventProductDelete = false
    }

    fun onCalculate() {
        _eventNavigateToResults.value = true
    }

    fun doneNavigating() {
        _eventNavigateToResults.value = false
    }


}