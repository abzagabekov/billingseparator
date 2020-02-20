package com.example.billingseparator.database.products

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDatabaseDao {

    @Insert
    fun insert(product: Product)

    @Update
    fun update(product: Product)

    @Query("SELECT * FROM products_table WHERE productId = :key")
    fun get(key: Long): Product?

    @Query("SELECT * FROM products_table ORDER BY productId DESC")
    fun getAllProducts(): LiveData<List<Product>>

    @Query("DELETE FROM products_table WHERE productId = :key")
    fun delete(key: Long)

    @Query("DELETE FROM products_table WHERE related_bill_id = :billId")
    fun clear(billId: Long)

    @Query("SELECT * FROM products_table ORDER BY productId DESC LIMIT 1")
    fun getLastProduct(): Product?

}