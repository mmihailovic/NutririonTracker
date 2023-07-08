package rs.raf.projekat_jul_marko_mihailovic_rn252020.application

import android.app.Application
import rs.raf.projekat_jul_marko_mihailovic_rn252020.modules.coreModule
import rs.raf.projekat_jul_marko_mihailovic_rn252020.modules.mealModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import rs.raf.projekat_jul_marko_mihailovic_rn252020.modules.userModule
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        initTimber()
        initKoin()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initKoin() {
        val modules = listOf(
            coreModule,
            mealModule,
            userModule
        )
        startKoin {
            androidLogger(Level.ERROR)
            // Use application context
            androidContext(this@MyApplication)
            // Use properties from assets/koin.properties
            androidFileProperties()
            fragmentFactory()
            // modules
            modules(modules)
        }
    }
}