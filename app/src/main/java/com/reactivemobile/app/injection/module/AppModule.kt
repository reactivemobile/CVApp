package com.reactivemobile.app.injection.module

import com.reactivemobile.app.data.remote.NetworkService
import com.reactivemobile.app.data.remote.Repository
import com.reactivemobile.app.ui.cv.CvContract
import com.reactivemobile.app.ui.cv.CvPresenter
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
    fun provideCvPresenter(repository: Repository): CvContract.Presenter {
        return CvPresenter(repository)
    }
}