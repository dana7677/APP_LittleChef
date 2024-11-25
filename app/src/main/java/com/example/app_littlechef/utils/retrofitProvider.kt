package com.example.app_littlechef.utils

import com.example.app_littlechef.retrofit.RecipeService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class retrofitProvider {

    companion object
    {
        fun makeRetrofitService():RecipeService
        {
            val retrofit= Retrofit.Builder()
                .baseUrl("https://dummyjson.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(RecipeService::class.java)
        }
    }
}