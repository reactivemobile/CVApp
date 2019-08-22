package com.reactivemobile.app.ui.cv

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
class CvPresenterTest {
    lateinit var cvPresenter: CvPresenter

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var view: CvContract.View

    @Before
    fun setUp() {
        val cv: CV = mock(CV::class.java)

        `when`(repository.getCV()).thenReturn(Single.just(cv))

        cvPresenter = CvPresenter(repository)
        cvPresenter.attach(view)
    }

    @Test
    fun fetchResults() {
        cvPresenter.fetchCv()

        verify(view).showLoading()
        verify(repository).getCV()
    }
}