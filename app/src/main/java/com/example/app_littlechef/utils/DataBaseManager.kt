package com.example.app_littlechef.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.app_littlechef.data.dataTable.Recipe

class DataBaseManager(context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME,null,
    DATABASE_VERSION
) {
    companion object {
        const val DATABASE_VERSION = 9
        const val DATABASE_NAME = "DTtoDoList.db"

        private const val SQL_CREATE_TABLE =
            "CREATE TABLE ${Recipe.TABLE_NAME}(" +
                    "${Recipe.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${Recipe.COLUMN_NAME} TEXT," +
                    "${Recipe.COLUMN_DESCRIPTION} TEXT," +
                    "${Recipe.COLUMN_INGREDIENTS} TEXT," +
                    "${Recipe.COLUMN_INSTRUCTIONS} TEXT," +
                    "${Recipe.COLUMN_TIMETOCOOK} TEXT," +
                    "${Recipe.COLUMN_KCALORIES} TEXT," +
                    "${Recipe.COLUMN_IMGURI} TEXT," +
                    "${Recipe.COLUMN_DONE} INTEGER)"

        private const val SQL_DELETE_TABLE ="DROP TABLE IF EXISTS Task"
    }
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        onDelete(db)
        onCreate(db)
    }

    private fun onDelete(db: SQLiteDatabase)
    {
        db.execSQL(SQL_DELETE_TABLE)
    }


}