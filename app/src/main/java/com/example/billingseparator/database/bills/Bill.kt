package com.example.billingseparator.database.bills

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bills_table")
data class Bill(

    @PrimaryKey(autoGenerate = true)
    var billId: Long = 0,

    @ColumnInfo(name = "bill_name")
    val billName: String,

    @ColumnInfo(name = "bill_date")
    val billDate: Long = System.currentTimeMillis()
) {
}