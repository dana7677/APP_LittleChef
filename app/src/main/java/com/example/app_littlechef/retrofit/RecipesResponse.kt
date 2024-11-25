package com.example.app_littlechef.retrofit

import com.google.gson.annotations.SerializedName
data class RecipesResponse(

    @SerializedName("recipes") val listRecipes: List<Recipe>,
    @SerializedName("total") val totalSearchs: String,
    @SerializedName("skip")val skipSearchs: String,
    @SerializedName("limit")val limitSearchs: String,
)

data class Recipe(

    @SerializedName("id") val id: String,
    @SerializedName("name")val Name: String,
    @SerializedName("ingredients")val Ingredients: List<String>,
    @SerializedName("instructions")val Instructions: List<String>,
    @SerializedName("prepTimeMinutes")val prepTime: String,
    @SerializedName("cookTimeMinutes")val cookTime: String,
    @SerializedName("servings")val servings: String,
    @SerializedName("difficulty")val difficulty: String,
    @SerializedName("cuisine")val cuisine: String,
    @SerializedName("caloriesPerServing")val caloriesPerServing: String,
    @SerializedName("tags")val tags: List<String>,
    @SerializedName("userId")val userId: String,
    @SerializedName("image")val imageUrl: String,
    @SerializedName("rating")val rating: String,
    @SerializedName("reviewCount")val reviewCount: String,
    @SerializedName("mealType")val mealType: List<String>,
)