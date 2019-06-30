package com.juangomez.heroes_jetpack.domain.usecase

import com.juangomez.heroes_jetpack.domain.model.SuperHero
import com.juangomez.heroes_jetpack.repository.SuperHeroRepository

class GetSuperHeroById(private val superHeroesRepository: SuperHeroRepository) {
    operator fun invoke(id: String): SuperHero? = superHeroesRepository.get(id)
}