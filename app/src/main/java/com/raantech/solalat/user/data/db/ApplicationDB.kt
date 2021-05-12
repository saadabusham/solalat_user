package com.raantech.solalat.user.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.raantech.solalat.user.data.daos.local.cart.CartLocalDao
import com.raantech.solalat.user.data.db.typeconverter.CategoryConverter
import com.raantech.solalat.user.data.db.typeconverter.MediaConverter
import com.raantech.solalat.user.data.db.typeconverter.MediaListConverter
import com.raantech.solalat.user.data.db.typeconverter.PriceConverter
import com.raantech.solalat.user.data.models.accessories.Accessory

@Database(
        entities = [
            Accessory::class
        ], version = 1
)

@TypeConverters(
    CategoryConverter::class,
    MediaConverter::class,
    MediaListConverter::class,
    PriceConverter::class
)

abstract class ApplicationDB : RoomDatabase() {

    abstract fun cartLocalDao(): CartLocalDao

    companion object {
        const val DATABASE_NAME = "solalatuser.db"
        const val TABLE_CART = "cartTable"
    }

}