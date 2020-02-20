package com.example.billingseparator.database.persons

import androidx.room.*
import com.example.billingseparator.database.bills.Bill

@Entity(tableName = "persons_table", foreignKeys = arrayOf(ForeignKey(entity = Bill::class,
                                                                        parentColumns = arrayOf("billId"),
                                                                        childColumns = arrayOf("related_bill_id"),
                                                                        onDelete = ForeignKey.CASCADE)), indices = arrayOf(
    Index(value = arrayOf("related_bill_id"), name = "FirstIndex")
))
data class Person (

    @PrimaryKey(autoGenerate = true)
    val personId: Long = 0L,

    @ColumnInfo(name = "person_name")
    val name: String,

    @ColumnInfo(name = "related_bill_id")
    val billId: Long,

    @ColumnInfo(name = "bill_summary")
    var billSummary: Double = 0.0

)