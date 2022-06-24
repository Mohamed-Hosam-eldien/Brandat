package com.example.brandat.viewmodels

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.brandat.data.source.FakeRemoteDataSource
import com.example.brandat.data.repo.FakeProductRepo
import com.example.brandat.data.source.local.ILocalDataSource
import com.example.brandat.data.source.remote.IRemoteDataSource
import com.example.brandat.models.Brand
import com.example.brandat.models.Brands
import com.example.brandat.ui.fragments.home.BrandViewModel
import getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {

    @get:Rule
    val instanceTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var  productViewModel : BrandViewModel

   // val repoLocalList = mutableListOf<Brand>(Brand(), Brand(), Brand(), Brand())
    val listOfBrands : List<Brand>? = listOf(Brand(), Brand(),Brand())
    val repoRemoteList = Brands(listOfBrands!!)

    lateinit var fakeRemoteDataSource: IRemoteDataSource

    @Before
    fun setup(){
        //Given brandViewModel

        val app = ApplicationProvider.getApplicationContext<Application>()
        //fakeLocalDataSource = FakeDataSource(repoRemoteList)
        val fakeDataSource = FakeRemoteDataSource()
        fakeDataSource.brands = repoRemoteList
        fakeRemoteDataSource = fakeDataSource

        val repo = FakeProductRepo()
        productViewModel = BrandViewModel(repo, app)
    }


    @Test
    fun getAllBrandsFromAPI_listOfBrandsNotNull() {

        //When get all brands
        productViewModel.getBrands()
        val value = productViewModel.brandResponse.getOrAwaitValue()

        //Then the list is not null
        assertNotNull(value)

    }

}