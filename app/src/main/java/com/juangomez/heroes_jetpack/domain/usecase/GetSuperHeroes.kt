package com.juangomez.heroes_jetpack.domain.usecase

import com.juangomez.heroes_jetpack.domain.model.SuperHero
import com.juangomez.heroes_jetpack.repository.SuperHeroRepository


class GetSuperHeroes(private val superHeroesRepository: SuperHeroRepository) {
    operator fun invoke(): List<SuperHero> = superHeroesRepository.getAllSuperHeroes()
}