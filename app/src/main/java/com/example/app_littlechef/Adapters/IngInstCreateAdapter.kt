package com.example.app_littlechef.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.app_littlechef.APP_LittleChefApplication
import com.example.app_littlechef.R
import com.example.app_littlechef.databinding.ViewMyRecipeIngInstBinding
import com.example.app_littlechef.retrofit.Recipe
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso

class IngInstCreateAdapter (private var IngInstList:List<String>,var IngList: Boolean,private val onItemClickListener: (Int) -> Unit,private val onEditextClickListener: (Int,String) -> Unit,
                            private val onSubmitTextListener: (Int,String) -> Unit):
    RecyclerView.Adapter<ViewHolderCreateIngInst>()
{

    fun updateItems(items: List<String>,IngOrInst:Boolean) {
        this.IngList=IngOrInst
        this.IngInstList = items
        notifyDataSetChanged()
    }
    fun setFilteredList(newList: List<String>,NewIngList: Boolean) {

        this.IngList=NewIngList
        this.IngInstList = newList
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCreateIngInst {

        //De Context coge a la vista de su padre que lo ha creado.


        val binding =
            ViewMyRecipeIngInstBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderCreateIngInst(binding)

    }

    override fun onBindViewHolder(holder: ViewHolderCreateIngInst, position: Int) {

        holder.bind(IngInstList[position],IngList,position)
        holder.button.setOnClickListener {
                onItemClickListener(position)
        }

        //Para cambiar el boton de guardado por esta escucha
        /*
        holder.editText.addTextChangedListener {

        }
        */

        holder.editText.setOnClickListener {
            var InInst=IngInstList[position]
            if(InInst=="")
            {
                println(InInst)
                onEditextClickListener(position,holder.editText.text.toString())
            }
        }

        holder.buttonAccept.setOnClickListener {
                onSubmitTextListener(position,holder.editText.text.toString())

        }


        //Para cambiar el boton de guardado por esta escucha
        /*
        holder.editText.addTextChangedListener {

        }
        */

    }

    override fun getItemCount() = IngInstList.size



}

class ViewHolderCreateIngInst(private val binding: ViewMyRecipeIngInstBinding) :
    RecyclerView.ViewHolder(binding.root) {

    val button: ImageButton = itemView.findViewById(R.id.RemoveIng_btn)
    val buttonAccept: ImageButton = itemView.findViewById(R.id.AccceptIng_btn)
    val editText:TextInputEditText=itemView.findViewById(R.id.nameIngInst_Text)

    fun bind(IngInst: String,IngList:Boolean,position: Int) {

        val context = itemView.context

        if(IngInst=="")
        {
            if(IngList==true)
            {
                binding.nameHintText.setHint(R.string.newIngredient)
                binding.nameIngInstText.setText(R.string.newIngredient)
            }
            else
            {
                binding.nameHintText.setHint(R.string.newInstruction)
                binding.nameIngInstText.setText(R.string.newInstruction)
            }
        }

        else
        {
            binding.nameIngInstText.setText(IngInst)
        }

        //Position

        binding.IngInstNumber.setText("${position+1} -")

    }
}