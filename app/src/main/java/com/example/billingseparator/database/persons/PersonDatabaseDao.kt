package com.example.billingseparator.database.persons

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PersonDatabaseDao {

    @Insert
    fun insert(person: Person)

    @Update
    fun update(person: Person)

    @Query("SELECT * FROM persons_table WHERE personId = :key")
    fun get(key: Long): Person?

    @Query("SELECT * FROM persons_table ORDER BY personId DESC")
    fun getAllPersons(): LiveData<List<Person>>

    @Query("DELETE FROM persons_table WHERE personId = :key")
    fun delete(key: Long)

    @Query("DELETE FROM persons_table WHERE related_bill_id = :billId")
    fun clear(billId: Long)

    @Query("SELECT * FROM persons_table ORDER BY personId DESC LIMIT 1")
    fun getLastPerson(): Person?

}