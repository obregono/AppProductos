package com.unison.appproductos.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.unison.appproductos.models.Producto

@Database(entities = [Producto::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDatabaseDao
}
