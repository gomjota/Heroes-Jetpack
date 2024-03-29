package com.juangomez.heroes_jetpack.repository

import com.juangomez.heroes_jetpack.domain.model.SuperHero
import com.juangomez.heroes_jetpack.repository.room.SuperHeroDao
import com.juangomez.heroes_jetpack.repository.room.SuperHeroEntity
import java.util.concurrent.ExecutorService

class LocalSuperHeroDataSource(
    private val dao: SuperHeroDao,
    private val executor: ExecutorService
) {
    fun getAllSuperHeroes(): List<SuperHero> =
        dao.getAll()
            .map { it.toSuperHero() }

    fun get(id: String): SuperHero? =
        dao.getById(id)?.toSuperHero()

    fun saveAll(all: List<SuperHero>) = executor.execute {
        dao.deleteAll()
        dao.insertAll(all.map { it.toEntity() })
    }

    fun save(superHero: SuperHero): SuperHero {
        executor.execute { dao.insertAll(listOf(superHero.toEntity())) }
        return superHero
    }

    private fun SuperHeroEntity.toSuperHero(): SuperHero =
        SuperHero(id, name, photo, isAvenger, description)

    private fun SuperHero.toEntity(): SuperHeroEntity =
        SuperHeroEntity(id, name, photo, isAvenger, description)
}