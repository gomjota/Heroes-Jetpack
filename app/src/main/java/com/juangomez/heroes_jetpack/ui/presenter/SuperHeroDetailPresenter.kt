package com.juangomez.heroes_jetpack.ui.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.juangomez.heroes_jetpack.common.weak
import com.juangomez.heroes_jetpack.domain.model.SuperHero
import com.juangomez.heroes_jetpack.domain.usecase.GetSuperHeroById
import java.util.concurrent.ExecutorService

class SuperHeroDetailPresenter(
    view: View,
    private val getSuperHeroById: GetSuperHeroById,
    private val executor: ExecutorService
) : SuperHeroDetailListener, LifecycleObserver {

    private val view: View? by weak(view)

    private lateinit var id: String

    fun preparePresenter(id: String) {
        this.id = id
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        view?.showLoading()
        refreshSuperHero()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        executor.shutdownNow()
    }

    override fun onEditSelected() {
        view?.openEditSuperHero(id)
    }

    private fun refreshSuperHero() = executor.submit {
        val superHero = getSuperHeroById(id) ?: return@submit
        view?.hideLoading()
        view?.showSuperHero(superHero)
    }

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showSuperHero(superHero: SuperHero)
        fun openEditSuperHero(superHeroId: String)
    }
}

interface SuperHeroDetailListener {
    fun onEditSelected()
}