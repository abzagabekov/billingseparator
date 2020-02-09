package com.example.billingseparator.params

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.billingseparator.database.Person
import com.example.billingseparator.formatPersons

class ParamsViewModel : ViewModel() {

    companion object {
        private val _participants = MutableLiveData<MutableList<Person>>()
        val participants: LiveData<MutableList<Person>>
            get() = _participants
    }

    val personsString = Transformations.map(_participants) { participants ->
        formatPersons(participants)
    }

    private var _personAddedEvent = MutableLiveData<Boolean>()
    val personAddedEvent: LiveData<Boolean>
        get() = _personAddedEvent

    private var _navigateToProducts = MutableLiveData<MutableList<Person>>()
    val navigateToProducts: LiveData<MutableList<Person>>
        get() = _navigateToProducts

    init {
        _participants.value = mutableListOf()
    }

    fun onPersonAdd(name: String) {

        if (!name.isNullOrEmpty()) {
            val oldList: MutableList<Person>? = _participants.value
            oldList?.add(Person(name))
            _participants.value = oldList
            _personAddedEvent.value = true
        }
    }

    fun onPersonDelete() {
        val oldList: MutableList<Person>? = _participants.value
        if (!oldList.isNullOrEmpty()) {
            oldList.removeAt(oldList.size - 1)
            _participants.value = oldList
        }
    }

    fun onContinue() {
        _navigateToProducts.value = _participants.value
    }

    fun doneNavigating() {
        _navigateToProducts.value = null
    }

    fun donePersonAdd() {
        _personAddedEvent.value = false
    }


}