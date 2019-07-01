package com.juangomez.heroes_jetpack.common

import androidx.room.Database
import androidx.room.RoomDatabase
import com.juangomez.heroes_jetpack.repository.room.SuperHeroDao
import com.juangomez.heroes_jetpack.repository.room.SuperHeroEntity

@Database(entities = [SuperHeroEntity::class], version = 1)
abstract class SuperHeroesDatabase : RoomDatabase() {
    abstract fun superHeroesDao(): SuperHeroDao
}
