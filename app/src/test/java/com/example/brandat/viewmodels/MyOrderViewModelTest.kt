package com.example.brandat.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.brandat.data.source.FakeDataSource
import com.example.brandat.data.repo.FakeProductRepo
import com.example.brandat.data.source.local.ILocalDataSource
import com.example.brandat.data.source.remote.IRemoteDataSource
import com.example.brandat.models.orderModel.Order
import com.example.brandat.models.orderModel.Orders
import com.example.brandat.ui.fragments.myOrder.MyOrderViewModel
import getOrAwaitValue
import org.junit.Assert

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MyOrderViewModelTest {

    @get:Rule
    val instanceTaskExecutorRule = InstantTaskExecutorRule()
    lateinit var  myOrderViewModel : MyOrderViewModel

    // val repoLocalList = mutableListOf<Brand>(Brand(), Brand(), Brand(), Brand())
    var listOfOrders : List<Order>? = listOf(Order(), Order(), Order())
    val repoRemoteList = Orders(listOfOrders!!)

    lateinit var fakeRemoteDataSource: IRemoteDataSource
    lateinit var fakeLocalDataSource: ILocalDataSource

    @Before
    fun setup(){
        //Given orderViewModel
        val fakeDataSource = FakeDataSource()
        fakeDataSource.orders = repoRemoteList
        fakeRemoteDataSource = FakeDataSource()

        val repo = FakeProductRepo()
        myOrderViewModel = MyOrderViewModel(repo)
    }

    @Test
    fun getAllOrdersFromAPI_listOfOrders() {

        //When get all orders
        myOrderViewModel.getOrdersFromApi("mmohamed@gmail.com")
        val value = myOrderViewModel.getOrder.getOrAwaitValue { }

        //Then the list has value
        Assert.assertNotNull(value)


    }
}