package com.reactivemobile.app.ui.main

import com.reactivemobile.app.data.model.CV
import com.reactivemobile.app.data.remote.Repository
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {
    lateinit var mainPresenter: MainPresenter

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var mainView: MainContract.View

    @Before
    fun setUp() {
        val cv: CV = mock(CV::class.java)

        `when`(repository.getCV()).thenReturn(Single.just(cv))

        mainPresenter = MainPresenter(repository)
        mainPresenter.attach(mainView)
    }

    @Test
    fun fetchResults() {
        mainPresenter.fetchCv()

        verify(mainView).showLoading()
        verify(repository).getCV()
    }
}