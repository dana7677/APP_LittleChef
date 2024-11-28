package com.example.app_littlechef

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.app_littlechef.APP_LittleChefApplication.Companion.prefs
import com.example.app_littlechef.Adapters.RecipeDetailAdapter
import com.example.app_littlechef.data.dataTable.providers.RecipeDAO
import com.example.app_littlechef.databinding.ActivityRecipeDetailBinding
import com.example.app_littlechef.retrofit.Recipe
import com.example.app_littlechef.utils.retrofitProvider
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeDetailActivity : AppCompatActivity() {


    lateinit var recipeID:String
    lateinit var recipeData:Recipe
    lateinit var binding: ActivityRecipeDetailBinding
    lateinit var adapter: RecipeDetailAdapter
    lateinit var IngList:List<String>
    lateinit var InstList:List<String>
    lateinit var favIcon:MenuItem
    var bolMyRecipes:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recipe_detail)

        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Recoger Values Intent de SearchRecipesActivity
        val resultID = intent.extras?.getString("extra_ID")
        val resultLongId=intent.extras?.getLong("extra_LONG")
        val resultBol=intent.extras?.getBoolean("extra_BL")
        if(resultBol!=null)
        {
            bolMyRecipes=resultBol
        }
        if(resultID!=null) {
            if(resultBol==true)
            {
                if(resultLongId!=null)
                {
                    getMyRecipeInfo(resultLongId)
                }

            }else
            {
                recipeID=resultID
                searchRecipeID(recipeID)
            }


        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
    private fun getMyRecipeInfo(string:Long)
    {
        var recipeDao= RecipeDAO(this)
        var newRecipe = recipeDao.findByID(string)

        if(newRecipe!=null)
        {
            recipeData=Recipe("",newRecipe.name,convertStringToList(newRecipe.Ingredients),
                convertStringToList(newRecipe.Instructions),
                newRecipe.TimeToCook,newRecipe.TimeToCook,
                "servings","easy",
                "default",newRecipe.KCalories,
                convertStringToList(newRecipe.Ingredients),
                "1","noImgUrl","5","5",convertStringToList(newRecipe.Ingredients))


            //SeteamosLosValores
            setMoreDataRecipe()
        }


    }
    private fun convertStringToList(listInsIng:String):List<String>
    {

        val ListNew=listInsIng.split("/")

        return ListNew

    }
    private fun searchRecipeID(query:String)
    {
        val service = retrofitProvider.makeRetrofitService()
        CoroutineScope(Dispatchers.IO).launch {
            try
            {
                var result = service.getSingleRecipe(query)
                CoroutineScope(Dispatchers.Main).launch {
                    if(result.id=="error")
                    {
                        //TODO MOSTRAR MENSAJE DE QUE HAY QUE HACER ALGO
                    }
                    else
                    {
                        recipeData=result
                        setMoreDataRecipe()
                    }
                }
            }
            catch (e: Exception) {
                Log.e("MainActivity",e.stackTraceToString())
            }

        }

    }

    private fun changeIngToInst(change:Boolean)
    {

        if(change==true)
        {
            adapter.setFilteredList(InstList,false)
            binding.ingredientRecipeDetailTitle.setText(R.string.Recipe_String)

            val StringPrueba = this.resources.getString(R.string.item_String)
            val StringPass=String.format(InstList.size.toString())
            binding.ingredientRecipeDetailNumber.setText("$StringPass $StringPrueba")

        }else
        {
            adapter.setFilteredList(IngList,true)
            binding.ingredientRecipeDetailTitle.setText(R.string.ingredient_String)

            val StringPrueba = this.resources.getString(R.string.item_String)
            val StringPass=String.format(IngList.size.toString())
            binding.ingredientRecipeDetailNumber.setText("$StringPass $StringPrueba")
        }



    }
    private fun setMoreDataRecipe()
    {
        if(recipeData!=null)
        {
            IngList=recipeData.Ingredients
            InstList=recipeData.Instructions


            supportActionBar?.title=recipeData.Name

            //Crear un Adapter para los Ingredientes

            adapter = RecipeDetailAdapter(IngList,true)



            //Initial
            if(recipeData.imageUrl!="noImgUrl")
            {
                Picasso.get().load(recipeData.imageUrl).into(binding.imgRecipeDetail)
            }
            binding.ingredientRecipeDetailTitle.setText(R.string.ingredient_String)
            val StringPrueba = this.resources.getString(R.string.item_String)
            val StringPass=String.format(adapter.itemCount.toString())
            binding.ingredientRecipeDetailNumber.setText("$StringPass $StringPrueba")
            binding.recyclerIngInst.adapter = adapter
            binding.recyclerIngInst.layoutManager = GridLayoutManager(this, 1)

            if(bolMyRecipes==false)
            {
                optionsBullShit()
            }
        }


        //Bindeando Botones Menu IngInst
        binding.navigationBar.setOnItemSelectedListener {
            when(it.itemId)
            {
                R.id.Ingredient_btn -> {

                    changeIngToInst(false)

                }
                R.id.recipe_btn -> {
                    changeIngToInst(true)
                }
                else->{
                    println("Error")
                }
            }
            return@setOnItemSelectedListener true
        }

    }

    private fun optionsBullShit()
    {
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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {

        when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            R.id.save -> {
                if(recipeData!=null){
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

            favIcon = menu.findItem(R.id.save)

        return true
    }
}