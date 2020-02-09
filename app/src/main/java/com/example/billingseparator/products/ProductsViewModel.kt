package com.example.billingseparator.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.billingseparator.database.Person
import com.example.billingseparator.database.Product
import com.example.billingseparator.params.ParamsViewModel

class ProductsViewModel : ViewModel() {

    companion object {
        private val _products = MutableLiveData<MutableList<Product>>()
        val products: LiveData<MutableList<Product>>
            get() = _products
    }

    val participants: LiveData<MutableList<Person>>
        get() = ParamsViewModel.participants

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


    init {
        _products.value = mutableListOf()
    }

    fun onProductAdd(productName: String, productPrice: Double, productBuyers: IntArray) {

        val newProduct = Product(productName, productPrice, productBuyers)
        val editList: MutableList<Product>? = _products.value
        editList?.add(newProduct)
        _eventProductAdd = true
        _products.value = editList

    }

    fun doneProductAdd() {
        _eventProductAdd = false
    }

    fun onProductDelete() {

        val editList: MutableList<Product>? = _products.value
        if (!editList.isNullOrEmpty()) {
            _deletedProduct = editList.removeAt(editList.size - 1)
            _eventProductDelete = true
        }
        _products.value = editList

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