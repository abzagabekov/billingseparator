package com.example.billingseparator.database

data class Bill(

    val billId: Int = (1..10000).shuffled().first(),
    val billDate: Long = System.currentTimeMillis()
) {
}