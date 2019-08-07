package com.reactivemobile.app.injection.module

import com.reactivemobile.app.data.remote.NetworkService
import com.reactivemobile.app.data.remote.Repository
import com.reactivemobile.app.ui.main.MainContract
import com.reactivemobile.app.ui.main.MainPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideRepository(networkService: NetworkService): Repository {
        return Repository(networkService)
    }

    @Provides
    fun provideMainPresenter(repository: Repository): MainContract.Presenter {
        return MainPresenter(repository)
    }
}