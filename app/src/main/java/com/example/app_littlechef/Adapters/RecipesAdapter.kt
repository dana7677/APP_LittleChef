package com.example.app_littlechef.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_littlechef.APP_LittleChefApplication
import com.example.app_littlechef.databinding.ViewRecipeItemBinding
import com.example.app_littlechef.retrofit.Recipe
import com.example.app_littlechef.APP_LittleChefApplication.Companion.prefs
import com.example.app_littlechef.retrofit.RecipesResponse
import com.squareup.picasso.Picasso

class RecipesAdapter (private var recipeList:List<Recipe>, private val onItemClickListener: (Int) -> Unit):
    RecyclerView.Adapter<ViewHolder>()
{
    //SearchViewConfig

    fun setFilteredList(filterList: List<Recipe>) {
        this.recipeList = filterList
        notifyDataSetChanged()

    }

    //Componenente dentro del ViewHolder que va a componer la vista


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        //De Context coge a la vista de su padre que lo ha creado.


        val binding =
            ViewRecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    //Devolver automaticamente la cantidad de recetas en nuestro Lista de recetas
    override fun getItemCount() = recipeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(recipeList[position],position)

        holder.itemView.setOnClickListener {
            onItemClickListener(position)
        }

    }

}
class ViewHolder(private val binding: ViewRecipeItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(recipeData: Recipe,pos:Int) {

        val context = itemView.context
        binding.timePrep.setText("${recipeData.cookTime} Min")
        binding.calories.setText("${recipeData.caloriesPerServing} Kcal")
        binding.recipeName.setText(recipeData.Name)
        binding.ratingStar.setText(recipeData.rating)

        if(prefs.getID()==recipeData.id)
        {
            binding.savedImg.visibility= View.VISIBLE
        }
        else
        {
            binding.savedImg.visibility= View.INVISIBLE
        }

        Picasso.get().load(recipeData.imageUrl).into(binding.imgRecipe)

    }
}