package com.example.billingseparator.database.products

import androidx.room.*
import com.example.billingseparator.BuyersConverter
import com.example.billingseparator.database.bills.Bill

@Entity(tableName = "products_table", foreignKeys = arrayOf(
    ForeignKey(entity = Bill::class,
        parentColumns = arrayOf("billId"),
        childColumns = arrayOf("related_bill_id"),
        onDelete = ForeignKey.CASCADE)), indices = arrayOf(Index(value = arrayOf("related_bill_id"), name = "SecondIndex")))
@TypeConverters(BuyersConverter::class)
data class Product(

    @PrimaryKey(autoGenerate = true)
    val productId: Long = 0L,

    @ColumnInfo(name = "product_name")
    val name: String,

    @ColumnInfo(name = "product_price")
    val productPrice: Double,

    @ColumnInfo(name = "related_bill_id")
    val billId: Long,

    @ColumnInfo(name = "product_buyers")
    val productBuyers: LongArray

)