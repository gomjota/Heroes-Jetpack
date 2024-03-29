package com.juangomez.heroes_jetpack.ui.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil.bind
import androidx.recyclerview.widget.LinearLayoutManager
import com.juangomez.heroes_jetpack.R
import com.juangomez.heroes_jetpack.common.module
import com.juangomez.heroes_jetpack.databinding.MainActivityBinding
import com.juangomez.heroes_jetpack.domain.model.SuperHero
import com.juangomez.heroes_jetpack.domain.usecase.GetSuperHeroes
import com.juangomez.heroes_jetpack.ui.presenter.SuperHeroesPresenter
import com.juangomez.heroes_jetpack.ui.view.adapter.SuperHeroesAdapter
import kotlinx.android.synthetic.main.main_activity.*
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider

class MainActivity : BaseActivity<MainActivityBinding>(), SuperHeroesPresenter.View {

    override val presenter: SuperHeroesPresenter by instance()
    private lateinit var adapter: SuperHeroesAdapter
    override val layoutId: Int = R.layout.main_activity
    override val toolbarView: Toolbar
        get() = toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeAdapter()
        initializeRecyclerView()
    }

    override fun configureBinding(binding: MainActivityBinding) {
        binding.isLoading = false
        binding.isShowingEmptyCase = false
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    private fun initializeAdapter() {
        adapter = SuperHeroesAdapter(presenter)
    }

    private fun initializeRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = adapter
    }

    override fun showLoading() = runOnUiThread {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() = runOnUiThread {
        progress_bar.visibility = View.GONE
    }

    override fun showEmptyCase() = runOnUiThread {
        tv_empty_case.visibility = View.VISIBLE
    }

    override fun showSuperHeroes(superHeroes: List<SuperHero>) = runOnUiThread {
        adapter.clear()
        adapter.addAll(superHeroes)
        adapter.notifyDataSetChanged()
    }

    override fun openDetail(id: String) = runOnUiThread {
        SuperHeroDetailActivity.open(
            activity = this,
            superHeroId = id
        )
    }

    override val activityModules = module {
        bind<SuperHeroesPresenter>() with provider {
            SuperHeroesPresenter(this@MainActivity, instance(), instance())
        }
        bind<GetSuperHeroes>() with provider { GetSuperHeroes(instance()) }
    }
}
