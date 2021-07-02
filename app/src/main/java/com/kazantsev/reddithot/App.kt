package com.kazantsev.reddithot

import android.app.Application
import com.kazantsev.reddithot.di.AppComponent
import com.kazantsev.reddithot.di.AppModule
import com.kazantsev.reddithot.di.DaggerAppComponent

class App : Application() {
    private var _appComponent: AppComponent? = null

    val appComponent: AppComponent
        get() = checkNotNull(_appComponent)

    override fun onCreate() {
        super.onCreate()
        instance = this
        _appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        lateinit var instance: App
        val component get() = instance.appComponent
    }

}