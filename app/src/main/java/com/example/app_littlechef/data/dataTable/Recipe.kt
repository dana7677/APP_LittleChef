package com.example.app_littlechef.data.dataTable

data class Recipe(val id:Long, var name:String, var descript:String, var Ingredients:String, var Instructions:String,var TimeToCook:String,var KCalories:String, var done:Boolean)

{
    companion object
    {
        const val TABLE_NAME="Task"
        const val COLUMN_ID="id"
        const val COLUMN_NAME="Name"
        const val COLUMN_DESCRIPTION="Description"
        const val COLUMN_INGREDIENTS="Ingredients"
        const val COLUMN_INSTRUCTIONS="Instructions"
        const val COLUMN_TIMETOCOOK="TimeToCook"
        const val COLUMN_KCALORIES="KCalories"
        const val COLUMN_DONE="Done"

    }
}