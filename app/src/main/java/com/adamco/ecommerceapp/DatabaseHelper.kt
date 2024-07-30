package com.adamco.ecommerceapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.adamco.ecommerceapp.model.DatabaseConstants
import com.adamco.ecommerceapp.model.data.item.ItemTotal

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DatabaseConstants.DATABASE_NAME,
    null,
    DatabaseConstants.DATABASE_VERSION
) {
    override fun onCreate(database: SQLiteDatabase) {
        val createTableQuery = """
         CREATE TABLE ${DatabaseConstants.TABLE_NAME} (
         ${DatabaseConstants.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
         ${DatabaseConstants.COLUMN_NAME} TEXT,
         ${DatabaseConstants.COLUMN_DESCRIPTION} TEXT,
         ${DatabaseConstants.COLUMN_PRICE} DOUBLE,
         ${DatabaseConstants.COLUMN_QUANTITY} INTEGER,
         ${DatabaseConstants.COLUMN_IMAGE} INTEGER 
         )
     """.trimIndent()

        database.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS ${DatabaseConstants.TABLE_NAME}")
            onCreate(db)
        }
    }

    fun insertData(item: ItemTotal): Long {
        val values = ContentValues().apply {
            put(DatabaseConstants.COLUMN_NAME, item.name)
            put(DatabaseConstants.COLUMN_DESCRIPTION, item.description)
            put(DatabaseConstants.COLUMN_PRICE, item.itemPrice)
            put(DatabaseConstants.COLUMN_QUANTITY, item.itemQuantity)
            put(DatabaseConstants.COLUMN_IMAGE, item.itemImg)
        }

        return writableDatabase.insert(DatabaseConstants.TABLE_NAME, null, values)
    }

    fun readData(): List<ItemTotal> {
        val itemTotalList = mutableListOf<ItemTotal>()
        val cursor: Cursor = readableDatabase.query(DatabaseConstants.TABLE_NAME, null, null, null, null, null, null)
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(DatabaseConstants.COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(DatabaseConstants.COLUMN_NAME))
                val description = getString(getColumnIndexOrThrow(DatabaseConstants.COLUMN_DESCRIPTION))
                val price = getDouble(getColumnIndexOrThrow(DatabaseConstants.COLUMN_PRICE))
                val quantity = getInt(getColumnIndexOrThrow(DatabaseConstants.COLUMN_QUANTITY))
                val image = getString(getColumnIndexOrThrow(DatabaseConstants.COLUMN_IMAGE))

                itemTotalList.add(ItemTotal(itemID = id, name = name, description = description, itemPrice = price, itemQuantity = quantity, itemImg = image))
            }
        }
        return itemTotalList
    }

    fun updateItem(items: ItemTotal): Long {
        val values = ContentValues().apply {
            put(DatabaseConstants.COLUMN_NAME, items.name)
            put(DatabaseConstants.COLUMN_DESCRIPTION, items.description)
            put(DatabaseConstants.COLUMN_PRICE, items.itemPrice)
            put(DatabaseConstants.COLUMN_QUANTITY, items.itemQuantity)
            put(DatabaseConstants.COLUMN_IMAGE, items.itemImg)
        }

        val selection = "${DatabaseConstants.COLUMN_ID} = ?"
        val selectionArg = arrayOf(items.itemID.toString())

        return writableDatabase.update(DatabaseConstants.TABLE_NAME, values, selection, selectionArg).toLong()
    }

    fun deleteItem(id: Long): Long {
        val selection = "${DatabaseConstants.COLUMN_ID} = ?"
        val selectionArg = arrayOf(id.toString())

        return writableDatabase.delete(DatabaseConstants.TABLE_NAME, selection, selectionArg).toLong()
    }

    fun getItemByName(name: String): ItemTotal? {
        val cursor = readableDatabase.query(
            DatabaseConstants.TABLE_NAME,
            null,
            "${DatabaseConstants.COLUMN_NAME} = ?",
            arrayOf(name),
            null,
            null,
            null
        )

        return if (cursor.moveToFirst()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_ID))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_DESCRIPTION))
            val price = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_PRICE))
            val quantity = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_QUANTITY))
            val image = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_IMAGE))

            ItemTotal(itemID = id, name = name, description = description, itemPrice = price, itemQuantity = quantity, itemImg = image)
        } else {
            null
        }
    }
}
