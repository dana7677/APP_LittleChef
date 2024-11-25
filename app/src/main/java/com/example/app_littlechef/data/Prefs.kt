package com.example.app_littlechef.data

import android.content.Context

class Prefs(val context: Context){

    val SHARED_NAME="MyDTB"
    val SHARED_Recipe_NAME="RecipeName"
    val SHARED_Recipe_ID="RecipeID"
    val SHARED_FAV="fav"

    val storage=context.getSharedPreferences(SHARED_NAME,0)

    fun saveZodiacNameString(name:String,id:String)
    {
        storage.edit().putString(SHARED_Recipe_NAME,name).apply()
        storage.edit().putString(SHARED_Recipe_ID,id).apply()
    }
    fun saveFav(fav:Boolean)
    {
        storage.edit().putBoolean(SHARED_FAV,fav).apply()
    }
    fun getName():String
    {
        return storage.getString(SHARED_Recipe_NAME,"")!!
    }
    fun getFav():Boolean
    {
        return storage.getBoolean(SHARED_FAV,false)
    }
    fun getID():String
    {
        return storage.getString(SHARED_Recipe_ID,"")!!
    }
    fun wipe()
    {
        storage.edit().clear().apply()
    }

}