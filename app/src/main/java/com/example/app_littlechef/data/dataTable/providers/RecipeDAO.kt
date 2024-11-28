package com.example.app_littlechef.data.dataTable.providers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.app_littlechef.data.dataTable.Recipe
import com.example.app_littlechef.utils.DataBaseManager

class RecipeDAO (val context: Context)
{
    lateinit var db: SQLiteDatabase

    private fun getValues(taskpass:Recipe): ContentValues
    {

        val valuespass= ContentValues().apply {

            put(Recipe.COLUMN_NAME,taskpass.name)
            put(Recipe.COLUMN_DESCRIPTION,taskpass.descript)
            put(Recipe.COLUMN_INGREDIENTS,taskpass.Ingredients)
            put(Recipe.COLUMN_INSTRUCTIONS,taskpass.Instructions)
            put(Recipe.COLUMN_TIMETOCOOK,taskpass.TimeToCook)
            put(Recipe.COLUMN_KCALORIES,taskpass.KCalories)
            put(Recipe.COLUMN_DONE, taskpass.done)

        }
        return valuespass
    }

    private fun getProjection(task:Recipe.Companion):Array<String>
    {
        val projection = arrayOf(Recipe.COLUMN_ID, Recipe.COLUMN_NAME,Recipe.COLUMN_DESCRIPTION,Recipe.COLUMN_INGREDIENTS,Recipe.COLUMN_INSTRUCTIONS,Recipe.COLUMN_TIMETOCOOK,Recipe.COLUMN_KCALORIES, Recipe.COLUMN_DONE)

        return projection
    }

    private fun returnTask(getcursor: Cursor):Recipe
    {
        val id = getcursor.getLong(getcursor.getColumnIndexOrThrow(Recipe.COLUMN_ID))
        val name = getcursor.getString(getcursor.getColumnIndexOrThrow(Recipe.COLUMN_NAME))
        val descript = getcursor.getString(getcursor.getColumnIndexOrThrow(Recipe.COLUMN_DESCRIPTION))
        val ingredients = getcursor.getString(getcursor.getColumnIndexOrThrow(Recipe.COLUMN_INGREDIENTS))
        val instructions = getcursor.getString(getcursor.getColumnIndexOrThrow(Recipe.COLUMN_INSTRUCTIONS))
        val timeToCook = getcursor.getString(getcursor.getColumnIndexOrThrow(Recipe.COLUMN_TIMETOCOOK))
        val kCalories = getcursor.getString(getcursor.getColumnIndexOrThrow(Recipe.COLUMN_KCALORIES))
        val done = getcursor.getInt(getcursor.getColumnIndexOrThrow(Recipe.COLUMN_DONE)) != 0

        return Recipe(id,name,descript,ingredients,instructions,timeToCook,kCalories,done)
    }

    fun open()
    {
        db=DataBaseManager(context).writableDatabase
    }
    fun close()
    {

        db.close()

    }
    fun insert(task:Recipe)
    {

        open()
        val values=getValues(task)

        try{
            val id=db.insert(Recipe.TABLE_NAME,null,values)

        }catch (e:Exception)
        {
            Log.e("DB",e.stackTraceToString())
        }
        finally {
            close()
        }


    }
    fun update(task: Recipe)
    {
        open()
        val values=getValues(task)

        try{
            val updatedRows=db.update(Recipe.TABLE_NAME, values, "${Recipe.COLUMN_ID} = ${task.id}", null)

        }catch (e:Exception)
        {
            Log.e("DB",e.stackTraceToString())
        }
        finally {
            close()
        }

    }
    fun delete(task: Recipe)
    {
        open()

        try {
            // Delete the existing row, returning the number of affected rows
            val deletedRows = db.delete(Recipe.TABLE_NAME, "${Recipe.COLUMN_ID} = ${task.id}", null)
        } catch (e: Exception) {
            Log.e("DB", e.stackTraceToString())
        } finally {
            close()
        }
    }
    fun findByID(id:Long):Recipe?
    {
        open()

        val projection = getProjection(Recipe)

        try {
            val cursor = db.query(
                Recipe.TABLE_NAME,
                projection,
                "${Recipe.COLUMN_ID}=$id",
                null,
                null,
                null,
                null
            )
            if (cursor.moveToNext()) {

                val task=returnTask(cursor)

                return task
            }
        }catch (e:Exception)
        {
            Log.e("DB",e.stackTraceToString())
        }
        finally {
            close()
        }

        return null

    }

    fun findByName(name:String):Recipe?
    {
        open()

        val projection = getProjection(Recipe)

        try {
            val cursor = db.query(
                Recipe.TABLE_NAME,
                projection,
                "${Recipe.COLUMN_NAME}=$name",
                null,
                null,
                null,
                null
            )
            if (cursor.moveToNext()) {

                val task=returnTask(cursor)

                return task
            }
        }catch (e:Exception)
        {
            Log.e("DB",e.stackTraceToString())
        }
        finally {
            close()
        }

        return null

    }
    fun findAll():List<Recipe>
    {

        open()
        var list:MutableList<Recipe> = mutableListOf()

        val projection = getProjection(Recipe)

        try {
            val cursor = db.query(
                Recipe.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
            )

            while (cursor.moveToNext()) {

                val task=returnTask(cursor)
                list.add(task)

            }

        }catch (e:Exception)
        {
            Log.e("DB",e.stackTraceToString())
        }
        finally {
            close()
        }

        return list

    }

    fun deleteCompleteTask()
    {
        open()
        try
        {

            val deleteItem=db.delete(Recipe.TABLE_NAME,"${Recipe.COLUMN_DONE} = true",null)

        }catch (e:Exception)
        {
            Log.e("DB",e.stackTraceToString())
        }
        finally {
            close()
        }
    }
    fun deleteAll()
    {
        open()
        try
        {
            val deleteItem=db.delete(Recipe.TABLE_NAME,null,null)

        }catch (e:Exception)
        {
            Log.e("DB",e.stackTraceToString())
        }
        finally {
            close()
        }
    }
}