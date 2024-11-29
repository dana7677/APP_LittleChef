package com.example.app_littlechef

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.app_littlechef.Adapters.IngInstCreateAdapter
import com.example.app_littlechef.data.dataTable.Recipe
import com.example.app_littlechef.data.dataTable.providers.RecipeDAO
import com.example.app_littlechef.databinding.ActivityAddRecipeBinding

class AddRecipeActivity : AppCompatActivity() {

    lateinit var bindingMainActivity: ActivityAddRecipeBinding
    lateinit var adapter: IngInstCreateAdapter
    lateinit var IngList:MutableList<String>
    lateinit var InstList:MutableList<String>
    var uriImg:String=""
    var IngOrInST:Boolean=true

    val pickMedia=registerForActivityResult(ActivityResultContracts.PickVisualMedia()){ uri->
        if(uri!=null)
        {

            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
            this.contentResolver.takePersistableUriPermission(uri, flag)
            bindingMainActivity.MyReceipeNewAddImgBtn.setImageURI(uri)
            uriImg=uri.toString()
            Log.i("popo","popoporr")
        }
        else
        {
            Log.i("popo","popoporr")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        IngList= emptyList<String>().toMutableList()
        InstList= emptyList<String>().toMutableList()

        bindingMainActivity = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(bindingMainActivity.root)


        //btn Imagen
        bindingMainActivity.MyReceipeNewAddImgBtn.setOnClickListener{
            if(ActivityResultContracts.PickVisualMedia.isPhotoPickerAvailable(this))
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        }


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

        bindingMainActivity.addRecipeBtn.setOnClickListener {
            onPressedAddButton()
        }

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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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

        println(txtValue)
        if(IngOrInST==true)
        {
            IngList[position]=txtValue
            addItemList(IngOrInST)
        }
        else
        {
            InstList[position]=txtValue
            addItemList(IngOrInST)
        }
    }
    fun newIngInstItem(position:Int,string:String) {


        println(string)
    if(string!=R.string.newIngredient.toString())
    {
        addItemList(IngOrInST)
    }

    }

    //Cuando remueves un elemento hay que cambiar el Adapter y tambien el value en la lista
    fun removeIngInst(position:Int) {

        if(IngOrInST==true)
        {

            IngList.removeAt(position)
            adapter.updateItems(IngList,IngOrInST)

            if(IngList.size==0)
            {
                addItemList(IngOrInST)
            }

        }
        else
        {
            InstList.removeAt(position)
            adapter.updateItems(IngList,IngOrInST)

            if(InstList.size==0)
            {
                addItemList(IngOrInST)
            }

        }

    }


    private fun onPressedAddButton()
    {

        var uripass=""

        if(bindingMainActivity.timeRecipeText.getText().toString().toIntOrNull()==null||bindingMainActivity.kcalRecipeText.getText().toString().toIntOrNull()==null)
        {

            Toast.makeText(this,"Use numbers for Kcal and Min", Toast.LENGTH_SHORT).show()

        }
        else
        {
            val recipeDAO = RecipeDAO(this)

            //Adding Value to the DT
            recipeDAO.insert(Recipe(-1, bindingMainActivity.nameTaskText.getText().toString(),
                "Example",
                convertListOnUniqueString(IngList),convertListOnUniqueString(InstList),
                bindingMainActivity.timeRecipeText.getText().toString(),
                bindingMainActivity.kcalRecipeText.getText().toString(),uriImg,false
            ))

            finish()
        }


    }

    private fun convertListOnUniqueString(IngOrInstList:MutableList<String>,):String
    {

        var StringList:String=""
        for (x in IngOrInstList) {

            if(x!=R.string.newIngredient.toString()&& x!=R.string.newInstruction.toString())
            {
                StringList=StringList+x+"/"
            }

        }

        return StringList
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
}