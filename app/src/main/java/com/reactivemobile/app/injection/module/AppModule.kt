package com.reactivemobile.app.injection.module

import com.reactivemobile.app.data.remote.RatesNetworkService
import com.reactivemobile.app.data.remote.Repository
import com.reactivemobile.app.ui.rates.viewmodel.RatesViewModelFactory
import com.reactivemobile.app.util.FlagMapper
import com.reactivemobile.app.util.JsonFlagMapper
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideRepository(networkService: RatesNetworkService): Repository {
        return Repository(networkService)
    }

    @Provides
    @Singleton
    fun provideViewModelFactory(repository: Repository): RatesViewModelFactory {
        return RatesViewModelFactory(repository)
    }

    @Provides
    @Singleton
    fun provideFlagMapper() : FlagMapper = JsonFlagMapper()

    @Provides
    @Singleton
    fun providePicasso() : Picasso = Picasso.get()


}