package com.example.billingseparator.database

data class Product(
    val name: String,
    val productPrice: Double,
    val productBuyers: IntArray,
    val productId: Int = (1..10000).shuffled().first()
)