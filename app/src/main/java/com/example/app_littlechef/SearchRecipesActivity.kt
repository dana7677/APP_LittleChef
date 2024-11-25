package com.example.app_littlechef

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.app_littlechef.Adapters.RecipesAdapter
import com.example.app_littlechef.databinding.ActivitySearchRecipesBinding
import com.example.app_littlechef.retrofit.Recipe
import com.example.app_littlechef.utils.retrofitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchRecipesActivity : AppCompatActivity() {

    lateinit var binding: ActivitySearchRecipesBinding
    lateinit var adapter: RecipesAdapter
    lateinit var searchView: SearchView
    var recipeList: List<Recipe> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySearchRecipesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RecipesAdapter(recipeList){ position->navigateToDetail(recipeList[position])}


        binding.recyclerHero.adapter = adapter
        binding.recyclerHero.layoutManager = GridLayoutManager(this, 2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initialSearch()

    }
    private fun initialSearch()
    {
        val service = retrofitProvider.makeRetrofitService()
        CoroutineScope(Dispatchers.IO).launch {
            try
            {
                //Log.d("MainActivity",dataofHeros.listHero.toString())
                var result = service.getListRecipes()
                CoroutineScope(Dispatchers.Main).launch {
                    if(result.totalSearchs=="0")
                    {
                        //TODO MOSTRAR MENSAJE DE QUE HAY QUE HACER ALGO
                    }else
                    {
                        recipeList = result.listRecipes
                        adapter.setFilteredList(recipeList)
                    }
                }


            }
            catch (e: Exception) {
                Log.e("MainActivity",e.stackTraceToString())
            }

        }

    }

    private fun searchSuperHeroes(query:String)
    {
        val service = retrofitProvider.makeRetrofitService()
        CoroutineScope(Dispatchers.IO).launch {
            try
            {
                //Log.d("MainActivity",dataofHeros.listHero.toString())
                var result = service.getRecipeName(query)
                CoroutineScope(Dispatchers.Main).launch {
                    if(result.totalSearchs=="0")
                    {
                        //TODO MOSTRAR MENSAJE DE QUE HAY QUE HACER ALGO
                    }else
                    {
                        recipeList = result.listRecipes
                        adapter.setFilteredList(recipeList)
                    }
                }


            }
            catch (e: Exception) {
                Log.e("MainActivity",e.stackTraceToString())
            }

        }

    }
    fun navigateToDetail(recipeSelected: Recipe) {


        /*
        val intent: Intent = Intent(this, selected_hero_activity::class.java)
        intent.putExtra("extra_ID",heroSelected.numID)
        intent.putExtra("extra_Name",heroSelected.nameHero)
        intent.putExtra("extra_Url",heroSelected.urlImage.url)
        startActivity(intent)
        */

    }
}