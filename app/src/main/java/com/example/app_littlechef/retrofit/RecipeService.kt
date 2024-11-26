package com.example.app_littlechef.retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {

   //'https://dummyjson.com/recipes/search?q=Margherita'
    @GET("recipes/search")
    suspend fun getRecipeName(
       @Query("q") query:String,
       @Query("sortBy") sortBy:String = "name",
       @Query("order") order:String = "asc"
    ):RecipesResponse

    //Esto Ordenaria
    //@Query("sortBy") sortBy:String = "name",
    //@Query("order") order:String = "asc"
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