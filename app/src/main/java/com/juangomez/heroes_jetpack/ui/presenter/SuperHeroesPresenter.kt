package com.juangomez.heroes_jetpack.ui.presenter

import com.juangomez.heroes_jetpack.common.weak
import com.juangomez.heroes_jetpack.domain.model.SuperHero
import com.juangomez.heroes_jetpack.domain.usecase.GetSuperHeroes
import java.util.concurrent.ExecutorService

class SuperHeroesPresenter(
    view: View,
    private val getSuperHeroes: GetSuperHeroes,
    private val executor: ExecutorService
) {

    private val view: View? by weak(view)

    fun onResume() {
        view?.showLoading()
        refreshSuperHeroes()
    }

    fun onDestroy() {
        executor.shutdownNow()
    }

    private fun refreshSuperHeroes() = executor.submit {
        val result = getSuperHeroes()
        view?.hideLoading()
        when {
            result.isEmpty() -> view?.showEmptyCase()
            else -> view?.showSuperHeroes(result)
        }
    }

    fun onSuperHeroClicked(superHero: SuperHero) {
        view?.openDetail(superHero.id)
    }

    interface View {
        fun hideLoading()
        fun showLoading()
        fun showEmptyCase()
        fun showSuperHeroes(superHeroes: List<SuperHero>)
        fun openDetail(id: String)
    }
}