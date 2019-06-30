package com.juangomez.heroes_jetpack.domain.usecase

import com.juangomez.heroes_jetpack.domain.model.SuperHero
import com.juangomez.heroes_jetpack.repository.SuperHeroRepository


class SaveSuperHero(private val superHeroesRepository: SuperHeroRepository) {
    operator fun invoke(superHero: SuperHero): SuperHero? =
        superHeroesRepository.save(superHero)
}