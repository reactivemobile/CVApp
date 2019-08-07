package com.reactivemobile.app.injection.component

import com.reactivemobile.app.injection.module.AppModule
import com.reactivemobile.app.injection.module.NetworkModule
import com.reactivemobile.app.ui.main.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {
    fun inject(fragment: MainFragment)
}