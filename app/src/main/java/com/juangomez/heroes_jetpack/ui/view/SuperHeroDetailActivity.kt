package com.juangomez.heroes_jetpack.ui.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil.bind
import com.juangomez.heroes_jetpack.R
import com.juangomez.heroes_jetpack.common.module
import com.juangomez.heroes_jetpack.databinding.SuperHeroDetailActivityBinding
import com.juangomez.heroes_jetpack.domain.model.SuperHero
import com.juangomez.heroes_jetpack.domain.usecase.GetSuperHeroById
import com.juangomez.heroes_jetpack.ui.presenter.SuperHeroDetailPresenter
import com.juangomez.heroes_jetpack.ui.utils.setImageBackground
import kotlinx.android.synthetic.main.super_hero_detail_activity.*
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider

class SuperHeroDetailActivity :
    BaseActivity<SuperHeroDetailActivityBinding>(),
    SuperHeroDetailPresenter.View {

    companion object {
        private const val SUPER_HERO_ID_KEY = "super_hero_id_key"

        fun open(activity: Activity, superHeroId: String) {
            val intent = Intent(activity, SuperHeroDetailActivity::class.java)
            intent.putExtra(SUPER_HERO_ID_KEY, superHeroId)
            activity.startActivity(intent)
        }
    }

    override val presenter: SuperHeroDetailPresenter by instance()
    override val layoutId: Int = R.layout.super_hero_detail_activity
    override val toolbarView: Toolbar
        get() = toolbar
    private val superHeroId: String by lazy { intent?.extras?.getString(SUPER_HERO_ID_KEY) ?: "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        edit_super_hero.setOnClickListener { presenter.onEditSelected() }
    }

    override fun configureBinding(binding: SuperHeroDetailActivityBinding) {
        binding.listener = presenter
        binding.isLoading = false
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun prepare(intent: Intent?) {
        title = superHeroId
        presenter.preparePresenter(superHeroId)
    }

    override fun showLoading() = runOnUiThread {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() = runOnUiThread {
        progress_bar.visibility = View.GONE
    }

    override fun showSuperHero(superHero: SuperHero) = runOnUiThread {
        title = superHero.name
        tv_super_hero_name.text = superHero.name
        tv_super_hero_description.text = superHero.description
        iv_avengers_badge.visibility =
            if (superHero.isAvenger) View.VISIBLE else View.GONE
        iv_super_hero_photo.setImageBackground(superHero.photo)
        edit_super_hero.visibility = View.VISIBLE
        super_hero_background.visibility = View.VISIBLE
    }

    override fun openEditSuperHero(superHeroId: String) = runOnUiThread {
        EditSuperHeroActivity.open(this, superHeroId)
    }

    override val activityModules = module {
        bind<SuperHeroDetailPresenter>() with provider {
            SuperHeroDetailPresenter(this@SuperHeroDetailActivity, instance(), instance())
        }
        bind<GetSuperHeroById>() with provider { GetSuperHeroById(instance()) }
    }
}