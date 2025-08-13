package com.testingPractice.daotesting.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteProduct(shoppingItem: ShoppingItem)

    @Query("SELECT * FROM product_table")
    fun observeAllProducts():LiveData<List<ShoppingItem>>

    @Query("SELECT SUM(price * quantity) FROM  product_table")
    fun observeTotalPrice():LiveData<Float>
}