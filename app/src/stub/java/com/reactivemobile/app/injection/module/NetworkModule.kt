package com.reactivemobile.app.injection.module

import com.google.gson.Gson
import com.reactivemobile.app.data.model.CV
import com.reactivemobile.app.data.remote.NetworkService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * A network module providing fake response data
 */
@Module
class NetworkModule {

    private val responseString =
        """{ "basics": { "name": "Donal O'Callaghan (Stub Mode)", "label": "Software Engineer", "email": "donal@reactivemobile.com", "phone": "07879 123 456", "summary": "Committed Android Developer" }, "work": [{ "company": "Reactive Mobile", "position": "Director", "website": "http://reactivemobile.com", "startDate": "Nov 2009", "endDate": "Present", "summary": "Building open-source apps and games" }, { "company": "Lloyds Banking Group", "position": "Android Software Engineer", "startDate": "May 2017", "endDate": "Jun 2019", "summary": "Building retail banking apps" }, { "company": "Schibsted", "position": "Android Software Engineer", "startDate": "Mar 2016", "endDate": "Dec 2016", "summary": "Building ads app" }, { "company": "Hive", "position": "Android Engineer", "startDate": "Dec 2013", "endDate": "Mar 2016", "summary": "Building connected home app" } ], "education": [{ "institution": "University College Cork", "area": "Computer Science", "studyType": "Bachelor", "startDate": "Sep 1998", "endDate": "May 2003" }], "skills": [{ "name": "Mobile Development", "level": "Expert", "keywords": [ "Android", "Kotlin", "Java" ] }, { "name": "UI Design", "level": "OK", "keywords": [ "PS" ] } ] }"""

    private val json by lazy { Gson().fromJson(responseString, CV::class.java) }

    @Provides
    @Singleton
    fun provideRetrofit() = object : NetworkService {
        override suspend fun getCV() = json
    }
}