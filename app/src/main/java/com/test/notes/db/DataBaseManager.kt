package com.test.notes.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.test.notes.recyclerview.Note
import com.test.notes.db.DataBaseConstances.COLUMN_NAME_CONTENT
import com.test.notes.db.DataBaseConstances.COLUMN_NAME_TITLE
import com.test.notes.db.DataBaseConstances.TABLE_NAME

class DataBaseManager(context: Context) {
    val dataBaseHelper = DataBaseHelper(context)
    var db: SQLiteDatabase? = null

    fun insertToDataBase(title: String, content: String) {
        db = dataBaseHelper.writableDatabase //open DataBase for writing
        val values = ContentValues().apply {
            put(COLUMN_NAME_TITLE, title)
            put(COLUMN_NAME_CONTENT, content)
        }
        db?.insert(TABLE_NAME, null, values)
        db?.close()
    }

    @SuppressLint("Range")
    fun readDataBase(): ArrayList<Note> {
        db = dataBaseHelper.readableDatabase //open DataBase for reading
        val cursor = db?.query(TABLE_NAME, null, null, null, null, null, null)
        val titleList = ArrayList<Note>()
        while (cursor!!.moveToNext()) {
            val title = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TITLE))
            val content = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CONTENT))
            val userText = Note(title, content)
            titleList.add(userText)
        }
        cursor.close()
        db?.close()
        return titleList
    }

    fun deleteDataFromDataBase(note: Note) {
        db = dataBaseHelper.writableDatabase //open DataBase for writing or deleting ha-ha-ha
        db?.delete(TABLE_NAME, "title = ? AND content = ?", arrayOf(note.title, note.content))
        db?.close()
    }
    fun updateNoteInDataBase(note: Note, title: String, content: String) {
        db = dataBaseHelper.writableDatabase
        val cursor = db?.query(TABLE_NAME, null, null, null, null, null, null)
        val position = cursor?.getColumnIndex(COLUMN_NAME_CONTENT)
        Log.d("MyLog", position.toString())
        val values = ContentValues().apply {
            put(COLUMN_NAME_TITLE, title)
            put(COLUMN_NAME_CONTENT, content)
        }
        db?.update(TABLE_NAME, values, "title = ? AND content = ?", arrayOf(note.title, note.content))
        cursor?.close()
        db?.close()
    }

}