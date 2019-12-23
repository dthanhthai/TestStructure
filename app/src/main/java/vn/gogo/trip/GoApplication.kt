package vn.gogo.trip

import androidx.multidex.MultiDexApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import vn.gogo.trip.di.koinModuleList

class GoApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            if (BuildConfig.DEBUG) {
                printLogger()
            }
            androidContext(this@GoApplication)
            modules(koinModuleList)
        }
    }
}