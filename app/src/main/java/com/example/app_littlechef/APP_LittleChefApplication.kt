package com.example.app_littlechef

import android.app.Application
import com.example.app_littlechef.data.Prefs

class APP_LittleChefApplication:Application() {

    companion object
    {
        lateinit var prefs:Prefs
    }

    override fun onCreate() {
        super.onCreate()

        prefs= Prefs(applicationContext)

    }
}