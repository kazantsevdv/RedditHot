package com.kazantsev.reddithot.di

import com.kazantsev.reddithot.ui.RedditFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        DbModule::class,
        RepoModule::class
    ]
)
interface AppComponent {
    fun inject(fragment: RedditFragment)


}