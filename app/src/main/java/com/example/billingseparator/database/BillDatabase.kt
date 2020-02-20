package com.example.billingseparator.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.billingseparator.database.bills.Bill
import com.example.billingseparator.database.bills.BillDatabaseDao
import com.example.billingseparator.database.persons.Person
import com.example.billingseparator.database.persons.PersonDatabaseDao
import com.example.billingseparator.database.products.Product
import com.example.billingseparator.database.products.ProductDatabaseDao

@Database(entities = [Bill::class, Person::class, Product::class], version = 2, exportSchema = false)
abstract class BillDatabase : RoomDatabase() {

    abstract val billDatabaseDao: BillDatabaseDao
    abstract val personDatabaseDao: PersonDatabaseDao
    abstract val productDatabaseDao: ProductDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: BillDatabase? = null

        fun getInstance(context: Context): BillDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BillDatabase::class.java,
                        "bills_store_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}