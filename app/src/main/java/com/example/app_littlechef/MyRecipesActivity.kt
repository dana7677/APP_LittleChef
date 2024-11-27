package com.example.app_littlechef

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.app_littlechef.Adapters.MyRecipesAdapter
import com.example.app_littlechef.Adapters.RecipesAdapter
import com.example.app_littlechef.data.dataTable.Recipe
import com.example.app_littlechef.data.dataTable.providers.RecipeDAO
import com.example.app_littlechef.databinding.ActivityMyRecipesBinding
import com.example.app_littlechef.utils.retrofitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyRecipesActivity : AppCompatActivity() {


    lateinit var bindingMainActivity: ActivityMyRecipesBinding
    lateinit var adapter: MyRecipesAdapter
    lateinit var taskList:List<Recipe>
    lateinit var buttonMenu: Button
    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        bindingMainActivity = ActivityMyRecipesBinding.inflate(layoutInflater)
        setContentView(bindingMainActivity.root)

        val taskDAO = RecipeDAO(this)


        taskList=taskDAO.findAll()

        //Solo Ejecutar la primera Vez
        //taskDAO.deleteAll()

        adapter = MyRecipesAdapter(taskList){

            //Cuando se de click al checkbox se ejecutara este codigo
            val task = taskList[it]
            task.done = !task.done //Si es + -> - y al reves - -> +
            taskDAO.update(task)   //Updatear los valores
            adapter.updateItems(taskList)
        }


        bindingMainActivity.recyclerTask.adapter=adapter
        bindingMainActivity.recyclerTask.layoutManager = GridLayoutManager(this,1)



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        bindingMainActivity.addTaskButton.setOnClickListener {

            val intent= Intent(this,AddRecipeActivity::class.java)
            startActivity(intent)

        }

        bindingMainActivity.clearButton.setOnClickListener {

            taskDAO.deleteCompleteTask()
            taskList=taskDAO.findAll()
            adapter.updateItems(taskDAO.findAll())

        }
    }
    override fun onResume() {

        val taskDAO = RecipeDAO(this)
        taskList=taskDAO.findAll()
        adapter.updateItems(taskDAO.findAll())
        adapter.notifyDataSetChanged()

        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_search_recipes,menu)

        val menuItem=menu?.findItem(R.id.menuSearch)!!
        searchView= menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener
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
    private fun searchRecipe(query:String)
    {

        //Filtrando con los valores de la DT Recipe
        val filteredList = taskList.filter {
            it.name.contains(query, true)
                    || it.Ingredients.contains(query, true)
        }

        adapter.setFilteredList(filteredList)
        if(filteredList.isEmpty())
        {
            Toast.makeText(this,"No data Found", Toast.LENGTH_SHORT).show()
        }

    }
}