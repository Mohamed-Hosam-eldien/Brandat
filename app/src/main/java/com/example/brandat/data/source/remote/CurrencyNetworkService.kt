package com.example.brandat.data.source.remote

import com.example.brandat.models.currency.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyNetworkService{

    @GET("v6/345d0e86dea309b5506b82a5/latest/USD")
    suspend fun convertCurrency(): Response<CurrencyResponse>

    }

