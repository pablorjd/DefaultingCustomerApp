package dev.pablorjd.listadeudores.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.pablorjd.listadeudores.data.entities.DefaultingCustomerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DefaultingCustomerDao {

    @Query("SELECT * FROM defaulting_customer")
    suspend fun getAllDeudores(): MutableList<DefaultingCustomerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDefaultingCustomer(defaultingCustomerEntity: DefaultingCustomerEntity): Long

    @Delete()
    suspend fun deleteDefaultingCustomer(defaultingCustomerEntity: DefaultingCustomerEntity)

    @Query("SELECT * FROM defaulting_customer WHERE id = :id")
    fun getDefaultingCustomerByID(id: Int): Flow<DefaultingCustomerEntity>

    @Query("SELECT * from defaulting_customer ORDER BY name ASC")
    fun getAllDefaultingCustomer(): Flow<List<DefaultingCustomerEntity>>
}