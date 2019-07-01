package com.juangomez.heroes_jetpack.ui.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.juangomez.heroes_jetpack.common.weak
import com.juangomez.heroes_jetpack.domain.model.SuperHero
import com.juangomez.heroes_jetpack.domain.usecase.GetSuperHeroById
import com.juangomez.heroes_jetpack.domain.usecase.SaveSuperHero
import java.util.concurrent.ExecutorService

class EditSuperHeroPresenter(
    view: View,
    private val getSuperHeroById: GetSuperHeroById,
    private val saveSuperHero: SaveSuperHero,
    private val executor: ExecutorService
) : EditSuperHeroListener, LifecycleObserver {

    private val view: View? by weak(view)
    private lateinit var id: String
    private var superHero: SuperHero? = null

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

    override fun onSaveSuperHeroSelected(
        name: String,
        description: String,
        isAvenger: Boolean
    ) {
        saveSuperHero(name, description, isAvenger)
    }

    private fun saveSuperHero(
        name: String,
        description: String,
        isAvenger: Boolean
    ) = executor.submit {
        view?.showLoading()
        val superHero = superHero ?: return@submit
        saveSuperHero(
            superHero.copy(
                name = name,
                description = description,
                isAvenger = isAvenger
            )
        )
        view?.close()
    }

    private fun refreshSuperHero() = executor.submit {
        val superHero = getSuperHeroById(id) ?: return@submit
        view?.hideLoading()
        view?.showSuperHero(superHero)
        this@EditSuperHeroPresenter.superHero = superHero
    }

    interface View {
        fun close()
        fun showLoading()
        fun hideLoading()
        fun showSuperHero(superHero: SuperHero)
    }
}

interface EditSuperHeroListener {
    fun onSaveSuperHeroSelected(
        name: String,
        description: String,
        isAvenger: Boolean
    )
}