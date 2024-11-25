package com.example.app_littlechef.retrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeService {

   //'https://dummyjson.com/recipes/search?q=Margherita'
    @GET("recipes/search?q={name}")
    suspend fun getRecipeName(
        @Path("name") query:String
    ):RecipesResponse

    //'https://dummyjson.com/recipes'
    @GET("recipes")
    suspend fun getListRecipes(
    ):RecipesResponse

    //'https://dummyjson.com/recipes/1'
    @GET("recipes/{recipe-id}")
    suspend fun getSingleRecipe(
        @Path("recipe-id") id:String
    ):Recipe

}