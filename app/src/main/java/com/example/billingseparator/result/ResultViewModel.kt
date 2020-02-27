package com.example.billingseparator.result

import android.app.Application
import androidx.lifecycle.*
import com.example.billingseparator.database.persons.PersonDatabaseDao


class ResultViewModel(personsDatabase: PersonDatabaseDao,
                      application: Application,
                      val billId: Long) : AndroidViewModel(application) {

    private val participants = personsDatabase.getPersonsByBill(billId)

    fun getParticipants() = participants


}