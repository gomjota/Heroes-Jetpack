package com.juangomez.heroes_jetpack

import android.app.Application
import androidx.databinding.DataBindingUtil.bind
import androidx.room.Room
import com.juangomez.heroes_jetpack.common.SuperHeroesDatabase
import com.juangomez.heroes_jetpack.common.module
import com.juangomez.heroes_jetpack.repository.LocalSuperHeroDataSource
import com.juangomez.heroes_jetpack.repository.RemoteSuperHeroDataSource
import com.juangomez.heroes_jetpack.repository.SuperHeroRepository
import com.juangomez.heroes_jetpack.repository.room.SuperHeroDao
import org.kodein.di.DKodein
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider
import org.kodein.di.erased.singleton
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SuperHeroesApplication : Application(), KodeinAware {
    override var kodein = Kodein.lazy {
        import(appDependencies())
        import(androidModule(this@SuperHeroesApplication))
    }

    fun override(overrideModule: Kodein.Module) {
        kodein = Kodein.lazy {
            import(appDependencies())
            import(androidModule(this@SuperHeroesApplication))
            import(overrideModule, allowOverride = true)
        }
    }

    private fun appDependencies(): Kodein.Module = module {
        bind<SuperHeroesDatabase>() with singleton {
            Room.databaseBuilder(
                this@SuperHeroesApplication,
                SuperHeroesDatabase::class.java,
                "superheroes-db"
            ).fallbackToDestructiveMigration()
                .build()
        }
        bind<SuperHeroDao>() with provider {
            val database: SuperHeroesDatabase = instance()
            database.superHeroesDao()
        }
        bind<SuperHeroRepository>() with provider {
            SuperHeroRepository(instance(), instance())
        }
        bind<LocalSuperHeroDataSource>() with singleton {
            LocalSuperHeroDataSource(instance(), instance())
        }
        bind<RemoteSuperHeroDataSource>() with provider {
            RemoteSuperHeroDataSource(instance())
        }
        bind<ExecutorService>() with provider {
            Executors.newCachedThreadPool()
        }
        bind<DKodein>() with provider { this }
    }
}