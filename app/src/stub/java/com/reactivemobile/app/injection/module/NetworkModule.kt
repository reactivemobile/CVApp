package com.reactivemobile.app.injection.module

import com.reactivemobile.app.data.remote.NetworkService
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * A network module providing fake response data
 */
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): NetworkService {
        val baseUrl = "https://stub.com"

        val loggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val mockInterceptor = MockInterceptor()
        val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).addInterceptor(mockInterceptor).build()

        return Retrofit.Builder().baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(NetworkService::class.java)
    }

    class MockInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {

            val responseString =
                """{ "basics": { "name": "Donal O'Callaghan (Stub Mode)", "label": "Software Engineer", "email": "donal@reactivemobile.com", "phone": "07879 123 456", "summary": "Committed Android Developer" }, "work": [{ "company": "Reactive Mobile", "position": "Director", "website": "http://reactivemobile.com", "startDate": "Nov 2009", "endDate": "Present", "summary": "Building open-source apps and games" }, { "company": "Lloyds Banking Group", "position": "Android Software Engineer", "startDate": "May 2017", "endDate": "Jun 2019", "summary": "Building retail banking apps" }, { "company": "Schibsted", "position": "Android Software Engineer", "startDate": "Mar 2016", "endDate": "Dec 2016", "summary": "Building ads app" }, { "company": "Hive", "position": "Android Engineer", "startDate": "Dec 2013", "endDate": "Mar 2016", "summary": "Building connected home app" } ], "education": [{ "institution": "University College Cork", "area": "Computer Science", "studyType": "Bachelor", "startDate": "Sep 1998", "endDate": "May 2003" }], "skills": [{ "name": "Mobile Development", "level": "Expert", "keywords": [ "Android", "Kotlin", "Java" ] }, { "name": "UI Design", "level": "OK", "keywords": [ "PS" ] } ] }"""

            return chain.proceed(chain.request())
                .newBuilder()
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    ResponseBody.create(
                        MediaType.parse("application/json"),
                        responseString.toByteArray()
                    )
                )
                .addHeader("content-type", "application/json")
                .build()
        }
    }
}