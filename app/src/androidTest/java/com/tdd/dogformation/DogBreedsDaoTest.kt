package com.tdd.dogformation

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.tdd.dogformation.data.local.persistence.DogBreedsDao
import com.tdd.dogformation.data.local.persistence.DogBreedsDatabase
import com.tdd.dogformation.domain.model.DogBreed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
@SmallTest
class DogBreedsDaoTest {
    private lateinit var database: DogBreedsDatabase
    private lateinit var dogBreedsDao: DogBreedsDao

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DogBreedsDatabase::class.java
        ).allowMainThreadQueries().build()

        dogBreedsDao = database.dogBreedsDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }


    @Test
    fun insertAllBreeds_returnsTrue() = runBlocking {
        val breed1 = DogBreed(
            name = "african",
            subBreeds = "",
            isFavourite = false,
            imageUrl = "dummy_url"
        )
        val breed2 = DogBreed(
            name = "australian",
            subBreeds = "",
            isFavourite = false,
            imageUrl = "dummy_url"
        )
        dogBreedsDao.insertAllDogBreeds(listOf(breed1, breed2))

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            dogBreedsDao.getDogBreeds().collect {
                assertThat(it).contains(breed1)
                latch.countDown()

            }
            latch.await()
        }
        job.cancelAndJoin()
    }

    @Test
    fun updateDogBreeds_returnsTrue() = runBlocking {
        val breed = DogBreed(
            name = "african",
            subBreeds = "",
            isFavourite = false,
            imageUrl = "dummy_url"
        )
        dogBreedsDao.insertDogBreed(breed)

        val updatedBreed = DogBreed(
            name = "african",
            subBreeds = "",
            isFavourite = true,
            imageUrl = "dummy_url"
        )

        dogBreedsDao.updateDogBreeds("african", true)

        val result = dogBreedsDao.getOneDogBreed("african")

        assertThat(result.isFavourite).isEqualTo(updatedBreed.isFavourite)
    }

}