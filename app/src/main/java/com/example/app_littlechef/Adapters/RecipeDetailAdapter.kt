package com.example.app_littlechef.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_littlechef.APP_LittleChefApplication
import com.example.app_littlechef.databinding.ViewIngInstBinding
import com.example.app_littlechef.retrofit.Recipe
import com.squareup.picasso.Picasso

class RecipeDetailAdapter (private var IngInstList:List<String>,var IngList: Boolean):
    RecyclerView.Adapter<ViewHolderIngList>()
{

    fun setFilteredList(newList: List<String>,NewIngList: Boolean) {

        this.IngList=NewIngList
        this.IngInstList = newList
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderIngList {

        //De Context coge a la vista de su padre que lo ha creado.


        val binding =
            ViewIngInstBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderIngList(binding)

    }

    override fun onBindViewHolder(holder: ViewHolderIngList, position: Int) {

        holder.bind(IngInstList[position],IngList,position)

    }

    //Devolver automaticamente la cantidad de recetas en nuestro Lista de recetas
    override fun getItemCount() = IngInstList.size

}

class ViewHolderIngList(private val binding: ViewIngInstBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(IngInst: String,IngList:Boolean,position: Int) {

        val context = itemView.context

        binding.IngInstNumber.setText("${position+1} -")
        binding.IngInstTxt.setText(IngInst)

    }
}