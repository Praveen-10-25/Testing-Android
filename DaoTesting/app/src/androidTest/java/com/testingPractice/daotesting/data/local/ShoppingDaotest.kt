package com.testingPractice.daotesting.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.testingPractice.daotesting.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingItemDb
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDb::class.java
        ).allowMainThreadQueries().build()
        dao = database.shoppingDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem("Apple", 20f, 50, "url", id = 1)
        dao.insertProduct(shoppingItem)

        val allShoppingItems = dao.observeAllProducts().getOrAwaitValue()

        assertThat(allShoppingItems).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem("Apple", 20f, 50, "url", id = 1)
        dao.insertProduct(shoppingItem)
        dao.deleteProduct(shoppingItem)

        val allShoppingItems = dao.observeAllProducts().getOrAwaitValue()

        assertThat(allShoppingItems).doesNotContain(shoppingItem)
    }
}
