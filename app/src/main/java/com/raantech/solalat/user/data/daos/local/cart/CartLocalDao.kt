package com.raantech.solalat.user.data.daos.local.cart

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raantech.solalat.user.data.db.ApplicationDB.Companion.TABLE_CART
import com.raantech.solalat.user.data.models.accessories.Accessory

@Dao
interface CartLocalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCart(accessory: Accessory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCarts(accessories: List<Accessory>)

    @Query("SELECT * FROM $TABLE_CART ORDER BY id ASC")
    suspend fun getCarts(): List<Accessory>

    @Query("SELECT * FROM $TABLE_CART WHERE id = :id")
    suspend fun getCarts(id: Int): Accessory

    @Query("SELECT SUM(count) FROM $TABLE_CART")
    fun getCartsCount(): LiveData<Int>

    @Query("DELETE FROM $TABLE_CART WHERE id = :id")
    suspend fun deleteCart(id: Int)

    @Query("DELETE FROM $TABLE_CART")
    suspend fun clearCart()
}