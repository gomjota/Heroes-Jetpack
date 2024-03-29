package com.juangomez.heroes_jetpack.repository.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "superheroes")
data class SuperHeroEntity(
    @PrimaryKey val id: String,
    val name: String,
    val photo: String?,
    val isAvenger: Boolean,
    val description: String
)