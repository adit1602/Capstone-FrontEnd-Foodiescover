package com.dicoding.foodiescover

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dicoding.foodiescover.db.FavoriteMealDB
import com.dicoding.foodiescover.db.MealDao
import com.dicoding.foodiescover.models.Meal
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class ExampleUnitTest {
    private lateinit var mealDao : MealDao
    private lateinit var testDB : FavoriteMealDB

    @Before
    fun createDB(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        testDB = Room.inMemoryDatabaseBuilder(
            context,
            FavoriteMealDB::class.java
        ).build()

        mealDao = testDB.dao
    }

    @After
    @Throws(IOException::class)
    fun closeDB(){
        testDB.close()
    }


    @Test
    @Throws(Exception::class)
    fun writeMealAndReadInList(){
        val meal : Meal = TestUtil.saveMeal()
        mealDao.addMeal(meal)

    }
}