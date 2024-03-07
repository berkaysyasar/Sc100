package com.berkay.loginscreens.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.berkay.loginscreens.classes.CategoryMaker

val database_name = "Veritabanim"
val table_name = "Kategoriler"
val col_name = "kategoriname"
val col_switch = "kategoriswitch"
val col_id = "id"

class dbhelper (var context: Context): SQLiteOpenHelper(context, database_name,null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        // Veritabanı oluştuğunda bir kez çalışır.
        var createTable = " CREATE TABLE " + table_name + "(" +
                col_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                col_name + " VARCHAR(256)," +
                col_switch + " BOOLEAN)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Veritabanı yükseltmek için kullanılır.
    }

    // Veri kaydetmek için fonksiyon tanımlama.

    fun insertData(categoryMaker: CategoryMaker) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(col_name, categoryMaker.categoryname)
        cv.put(col_switch, categoryMaker.switch)
        var sonuc = db.insert(table_name, null, cv)
        if (sonuc == (-1).toLong()) {
            Toast.makeText(context, "Hatalı", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Başarılı", Toast.LENGTH_LONG).show()
        }
        db.close()
    }

    // Verileri okumak için fonksiyon tanımlama.
    fun readData(): MutableList<CategoryMaker> {
        val list: MutableList<CategoryMaker> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM " + table_name
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                val categoryMaker = CategoryMaker()
                // Sütun var mı kontrolü ekleyin
                val idColumnIndex = result.getColumnIndex(col_id)
                val nameColumnIndex = result.getColumnIndex(col_name)
                val switchColumnIndex = result.getColumnIndex(col_switch)

                if (idColumnIndex >= 0) {
                    categoryMaker.id = result.getString(idColumnIndex).toInt()
                }

                if (nameColumnIndex >= 0) {
                    categoryMaker.categoryname = result.getString(nameColumnIndex)
                }

                if (switchColumnIndex >= 0) {
                    categoryMaker.switch = result.getString(switchColumnIndex).toBoolean()
                }

                list.add(categoryMaker)
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }

    fun deleteData(categoryId: String): Boolean {
        val db = this.writableDatabase
        val whereClause = "$col_name = ?"
        val whereArgs = arrayOf(categoryId.toString())

        val result = db.delete(table_name, whereClause, whereArgs)

        db.close()

        // Eğer silme işlemi başarılıysa, result sıfırdan büyük olacaktır.
        return result > 0
    }

}