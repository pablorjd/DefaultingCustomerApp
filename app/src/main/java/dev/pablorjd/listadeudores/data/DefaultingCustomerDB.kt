package dev.pablorjd.listadeudores.data

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.pablorjd.listadeudores.data.dao.DefaultingCustomerDao
import dev.pablorjd.listadeudores.data.entities.DefaultingCustomerEntity

@Database(entities = [DefaultingCustomerEntity::class], version = 1)
abstract class DefaultingCustomerDB: RoomDatabase() {

    abstract fun defaultingCustomerDao(): DefaultingCustomerDao

}