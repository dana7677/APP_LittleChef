package com.example.app_littlechef

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

    private fun searchRecipe(query:String)
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


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search_recipes,menu)

        val menuItem=menu?.findItem(R.id.menuSearch)!!
        searchView= menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener
        {
            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query!=null)
                {
                    searchRecipe(query)
                    return true
                }
                return false
            }
        })

        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

        }
        return true
    }
    fun navigateToDetail(recipeSelected: Recipe) {



        val intent: Intent = Intent(this, RecipeDetailActivity::class.java)
        intent.putExtra("extra_ID",recipeSelected.id)
        intent.putExtra("extra_BL",false)
        startActivity(intent)
    }
}