package com.kazantsev.reddithot.di

import com.kazantsev.reddithot.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {


    @Singleton
    @Provides
    fun app(): App {
        return app
    }

}