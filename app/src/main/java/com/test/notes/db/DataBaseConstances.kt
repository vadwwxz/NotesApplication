package com.test.notes.db

import android.provider.BaseColumns

object DataBaseConstances : BaseColumns{
    const val TABLE_NAME = "main_table"
    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_NAME_CONTENT = "content"
    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE $TABLE_NAME (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "$COLUMN_NAME_TITLE TEXT," +
                "$COLUMN_NAME_CONTENT TEXT)"
    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    const val DATABASE_NAME = "DataBaseSql.db"
    const val DATABASE_VERSION = 1
}