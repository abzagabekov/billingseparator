package com.example.billingseparator.params

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.billingseparator.database.persons.Person
import com.example.billingseparator.database.persons.PersonDatabaseDao
import com.example.billingseparator.formatPersons
import kotlinx.coroutines.*

class ParamsViewModel(private val personDatabase: PersonDatabaseDao, application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private var uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val participants = personDatabase.getAllPersons()

    val personsString = Transformations.map(participants) { participants ->
        formatPersons(participants)
    }

    private var _personAddedEvent = MutableLiveData<Boolean>()
    val personAddedEvent: LiveData<Boolean>
        get() = _personAddedEvent

    private var _navigateToProducts = MutableLiveData<Boolean?>()
    val navigateToProducts: LiveData<Boolean?>
        get() = _navigateToProducts


    fun onPersonAdd(name: String) {

        if (!name.isNullOrEmpty()) {
            uiScope.launch {
                val newPerson = Person(name = name, billId = 1)
                insert(newPerson)
                _personAddedEvent.value = true
            }
        }
    }

    private suspend fun insert(person: Person) {
        withContext(Dispatchers.IO) {
            personDatabase.insert(person)
        }
    }


    fun onPersonDelete() {
        uiScope.launch {
            val lastPerson: Person? = getLastPerson()
            if (lastPerson != null)
                delete(lastPerson.personId)
        }
    }

    private suspend fun getLastPerson(): Person? {
        var res: Person? = null
        withContext(Dispatchers.IO) {
            res = personDatabase.getLastPerson()
        }
        return res
    }

    private suspend fun delete(personId: Long) {
        withContext(Dispatchers.IO) {
            personDatabase.delete(personId)
        }
    }

    fun onContinue() {
        _navigateToProducts.value = true
    }

    fun doneNavigating() {
        _navigateToProducts.value = null
    }

    fun donePersonAdd() {
        _personAddedEvent.value = false
    }


}