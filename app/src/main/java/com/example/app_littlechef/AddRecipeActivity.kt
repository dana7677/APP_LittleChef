package com.example.app_littlechef

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.app_littlechef.Adapters.IngInstCreateAdapter
import com.example.app_littlechef.databinding.ActivityAddRecipeBinding

class AddRecipeActivity : AppCompatActivity() {

    lateinit var bindingMainActivity: ActivityAddRecipeBinding
    lateinit var adapter: IngInstCreateAdapter
    lateinit var IngList:MutableList<String>
    lateinit var InstList:MutableList<String>
    var IngOrInST:Boolean=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        IngList= emptyList<String>().toMutableList()
        InstList= emptyList<String>().toMutableList()

        bindingMainActivity = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(bindingMainActivity.root)


        adapter = IngInstCreateAdapter(IngList,IngOrInST,{

            position ->
            removeIngInst(position)

        }, {
                position,text->
            newIngInstItem(position,text)

        }, {
                    position,text->
                saveValueText(position,text)

        })


        bindingMainActivity.recyclerAddIngInst.adapter=adapter
        bindingMainActivity.recyclerAddIngInst.layoutManager = GridLayoutManager(this,1)

        //addItemList(IngOrInST)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bindingMainActivity.navigationMyRecipeBar.setOnItemSelectedListener {
            when(it.itemId)
            {
                R.id.Ingredient_btn -> {

                    IngOrInST=true
                    if(IngList.size==0)
                    {
                        addItemList(IngOrInST)
                    }

                adapter.updateItems(IngList,IngOrInST)

                }
                R.id.recipe_btn -> {

                    IngOrInST=false
                    if(InstList.size==0)
                    {
                        addItemList(IngOrInST)
                    }
                    adapter.updateItems(InstList,IngOrInST)

                }
                else->{
                    println("Error")
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    fun addItemList(ingOrInst:Boolean)
    {
        if(ingOrInst==true)
        {
            IngList.add("")

            adapter.updateItems(IngList,IngOrInST)
        }
        else
        {
            InstList.add("")

            adapter.updateItems(InstList,IngOrInST)
        }
    }

    //Cuado clickas el boton de guardar para cambiar los values
    fun saveValueText(position: Int,txtValue:String)
    {
        if(IngOrInST==true)
        {
            IngList[position]=txtValue
        }
        else
        {
            InstList[position]=txtValue
        }
    }
    fun newIngInstItem(position:Int,string:String) {


        println(string)
    if(string!="New Ingredient")
    {
        addItemList(IngOrInST)
    }

    }

    //Cuando remueves un elemento hay que cambiar el Adapter y tambien el value en la lista
    fun removeIngInst(position:Int) {

        if(IngOrInST==true)
        {

            IngList.removeAt(position)

            if(IngList.size==0)
            {
                addItemList(IngOrInST)
            }

        }
        else
        {
            InstList.removeAt(position)

            if(InstList.size==0)
            {
                addItemList(IngOrInST)
            }

        }

    }
}