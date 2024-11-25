package com.example.app_littlechef

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app_littlechef.APP_LittleChefApplication.Companion.prefs
import com.example.app_littlechef.databinding.ActivityRecipeDetailBinding
import com.example.app_littlechef.retrofit.Recipe
import com.example.app_littlechef.utils.retrofitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeDetailActivity : AppCompatActivity() {


    lateinit var recipeID:String
    lateinit var recipeData:Recipe
    lateinit var binding: ActivityRecipeDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recipe_detail)


        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setContentView(R.layout.activity_selected_hero)

        val resultID = intent.extras?.getInt("extra_ID")
        if(resultID!=null) {
            recipeID=resultID.toString()
            searchRecipeID(recipeID)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun searchRecipeID(query:String)
    {
        val service = retrofitProvider.makeRetrofitService()
        CoroutineScope(Dispatchers.IO).launch {
            try
            {
                //Log.d("MainActivity",dataofHeros.listHero.toString())
                var result = service.getSingleRecipe(query)
                CoroutineScope(Dispatchers.Main).launch {
                    if(result.id=="error")
                    {
                        //TODO MOSTRAR MENSAJE DE QUE HAY QUE HACER ALGO
                    }else
                    {
                        recipeData=result
                        setMoreDataHeroe()
                    }
                }
            }
            catch (e: Exception) {
                Log.e("MainActivity",e.stackTraceToString())
            }

        }

    }

    private fun setMoreDataHeroe() {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {

        when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            R.id.save -> {
                if (prefs.getName() == recipeData.id) {
                    if (prefs.getFav() == true) {
                        clearSharedPrefs()
                        item.setIcon(R.drawable.save_icon)
                    } else {
                        saveFavZodiac()
                        item.setIcon(R.drawable.saved_icon)
                    }
                } else {

                    clearSharedPrefs()
                    saveFavZodiac()
                    item.setIcon(R.drawable.saved_icon)

                }
            }
        }
        return true
    }

        private fun clearSharedPrefs()
        {
            prefs.wipe()
        }
        private fun saveFavZodiac()
        {
            prefs.saveFav(true)
            prefs.saveZodiacNameString(recipeData.Name,recipeData.id)
        }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_recipe_detail,menu)
        val menuItem=menu?.findItem(R.id.save)!!

            val favIcon = menu.findItem(R.id.save)
            if(prefs.getName()==recipeData.id)
            {
                if(prefs.getFav()==true)
                {
                    favIcon.setIcon(R.drawable.saved_icon)
                }
                else
                {
                    favIcon.setIcon(R.drawable.save_icon)
                }
            }
            else
            {
                favIcon.setIcon(R.drawable.save_icon)
            }

        return true
    }
}