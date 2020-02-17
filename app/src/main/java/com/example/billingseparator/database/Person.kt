package com.example.billingseparator.database

data class Person (

    val name: String,
    val billId: Int,
    val personId: Int = (1..10000).shuffled().first(),
    var billSummary: Double = 0.0

)