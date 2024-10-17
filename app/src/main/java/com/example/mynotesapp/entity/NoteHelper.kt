package com.example.mynotesapp.entity

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.mynotesapp.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.example.mynotesapp.db.DatabaseContract.NoteColumns.Companion._ID
import com.example.mynotesapp.db.DatabaseHelper
import java.sql.SQLException

// manipulasi data yang berada di dalam tabel seperti query
class NoteHelper (context: Context) {

    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: NoteHelper? = null // menginisiasi database
        fun getInstance(context: Context): NoteHelper =  // Singleton NoteHelper
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: NoteHelper(context) // sehingga tidak terjadi duplikasi instance
            }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }
    fun close() {
        dataBaseHelper.close()
        if (database.isOpen)
            database.close()
    }

    // mengambil data
    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC")
    }

    // mengambil data dengan id tertentu
    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    // menyimpan data
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    // update data
    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    // delete
    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }
}