package com.reactivemobile.app.presentation.ui.rates.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import com.reactivemobile.app.data.model.CV
import com.reactivemobile.app.data.remote.Repository
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CvViewModelTest {

    private lateinit var cvViewModel: RatesViewModel

    @Mock
    private lateinit var cv: CV

    @Mock
    private lateinit var cvObserver: Observer<CV>

    @Mock
    private lateinit var loadingObserver: Observer<Boolean>

    @Mock
    private lateinit var errorObserver: Observer<Boolean>

    @Rule
    @JvmField
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `test successful fetch updates UI`() {
        val repository = mock<Repository> {
            onBlocking { getCV() } doReturn cv
        }

        setupViewModel(repository)

        cvViewModel.fetchRates()

        verify(loadingObserver).onChanged(eq(true))
        verify(cvObserver).onChanged(eq(cv))
    }

    @Test
    fun `test failed fetch shows error`() {
        val repository = mock<Repository> {
            onBlocking { getCV() } doThrow RuntimeException("Error fetching data")
        }

        setupViewModel(repository)

        cvViewModel.fetchRates()

        verify(errorObserver).onChanged(eq(true))
        verify(cvObserver, never()).onChanged(any())
    }

    private fun setupViewModel(repository: Repository) {
        cvViewModel = RatesViewModel(repository)
        cvViewModel.rates.observeForever(cvObserver)
        cvViewModel.loading.observeForever(loadingObserver)
        cvViewModel.error.observeForever(errorObserver)
    }
}