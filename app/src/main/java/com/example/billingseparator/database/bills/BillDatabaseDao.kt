package com.example.billingseparator.database.bills

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BillDatabaseDao {

    @Insert
    fun insert(bill: Bill)

    @Query("SELECT * FROM bills_table ORDER BY bill_date")
    fun getAllBills(): LiveData<List<Bill>>

    @Query("SELECT * FROM bills_table WHERE billId = :key")
    fun get(key: Long): Bill?

    @Query("SELECT * FROM bills_table ORDER BY bill_date DESC LIMIT 1")
    fun getLastBill(): Bill?

    @Query("DELETE FROM bills_table WHERE billId = :key")
    fun delete(key: Long)

    @Query("DELETE FROM bills_table")
    fun clear()
}