package com.testingPractice.daotesting.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class ShoppingItem(

    val productName : String,
    val price : Float,
    val quantity :Int,
    val imageUrl : String,
    @PrimaryKey(autoGenerate = true)
    val id : Int
)