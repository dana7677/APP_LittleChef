package com.example.app_littlechef.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_littlechef.APP_LittleChefApplication
import com.example.app_littlechef.databinding.ViewRecipeItemBinding
import com.example.app_littlechef.APP_LittleChefApplication.Companion.prefs
import com.example.app_littlechef.data.dataTable.Recipe
import com.example.app_littlechef.retrofit.RecipesResponse
import com.squareup.picasso.Picasso

class MyRecipesAdapter (private var MyrecipeList:List<Recipe>, private val onItemClickListener: (Int) -> Unit):
    RecyclerView.Adapter<MyRecipeViewHolder>()
{
    //SearchViewConfig

    fun setFilteredList(filterList: List<Recipe>) {
        this.MyrecipeList = filterList
        notifyDataSetChanged()

    }

    fun updateItems(items: List<Recipe>) {
        this.MyrecipeList = items
        notifyDataSetChanged()
    }

    //Componenente dentro del ViewHolder que va a componer la vista


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecipeViewHolder {

        //De Context coge a la vista de su padre que lo ha creado.


        val binding =
            ViewRecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyRecipeViewHolder(binding)

    }

    //Devolver automaticamente la cantidad de recetas en nuestro Lista de recetas
    override fun getItemCount() = MyrecipeList.size

    override fun onBindViewHolder(holder: MyRecipeViewHolder, position: Int) {

        holder.bind(MyrecipeList[position],position)

        holder.itemView.setOnClickListener {
            onItemClickListener(position)
        }

    }

}
class MyRecipeViewHolder(private val binding: ViewRecipeItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(recipeData: Recipe,pos:Int) {

        val context = itemView.context

        binding.timePrep.setText("${recipeData.TimeToCook} Min")
        binding.calories.setText("${recipeData.KCalories} Kcal")
        binding.recipeName.setText(recipeData.name)

        binding.ratingStar.visibility=View.INVISIBLE

       // binding.ratingStar.setText(recipeData.rating)


        //Picasso.get().load(recipeData.imageUrl).into(binding.imgRecipe)

    }
}