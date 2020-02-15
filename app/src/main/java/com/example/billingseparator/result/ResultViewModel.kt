package com.example.billingseparator.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.billingseparator.database.Person
import com.example.billingseparator.params.ParamsViewModel
import com.example.billingseparator.products.ProductsViewModel

class ResultViewModel : ViewModel() {

    init {
        countResults()
    }

    private fun countResults() {

        ProductsViewModel.products.value?.forEach {
            val fraction = it.productPrice / it.productBuyers.size

            it.productBuyers.forEach {buyerId ->
                ParamsViewModel.participants.value?.forEach {
                    if (it.personId == buyerId) {
                        it.billSummary += fraction
                    }
                }
            }
        }
    }

    fun getParticipants(): List<Person>? = ParamsViewModel.participants.value
}