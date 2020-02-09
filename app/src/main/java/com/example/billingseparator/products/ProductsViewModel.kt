package com.example.billingseparator.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.billingseparator.database.Product

class ProductsViewModel : ViewModel() {

    private val _products = MutableLiveData<MutableList<Product>>()

}