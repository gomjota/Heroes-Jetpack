package com.juangomez.heroes_jetpack.repository

import com.juangomez.heroes_jetpack.domain.model.SuperHero

class SuperHeroRepository(
    private val local: LocalSuperHeroDataSource,
    private val remote: RemoteSuperHeroDataSource
) {
    fun getAllSuperHeroes(): List<SuperHero> =
        local.getAllSuperHeroes().ifEmpty {
            remote.getAllSuperHeroes()
                .also { local.saveAll(it) }
        }

    fun get(id: String): SuperHero? =
        local.get(id)
            ?: remote.get(id)?.also { local.save(it) }

    fun save(superHero: SuperHero): SuperHero {
        local.save(superHero)
        remote.save(superHero)
        return superHero
    }
}