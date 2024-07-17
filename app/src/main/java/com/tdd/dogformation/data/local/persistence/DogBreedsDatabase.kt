package com.tdd.dogformation.data.local.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tdd.dogformation.domain.model.DogBreed
import com.tdd.dogformation.domain.model.DogBreedImages
import com.tdd.dogformation.utils.Converters


@Database(version = 1, entities = [DogBreed::class, DogBreedImages::class], exportSchema = false)
@TypeConverters(Converters::class)
abstract class DogBreedsDatabase : RoomDatabase() {

    abstract fun dogBreedsDao(): DogBreedsDao
}