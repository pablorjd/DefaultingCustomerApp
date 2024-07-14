package dev.pablorjd.listadeudores.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "defaulting_customer")
data class DefaultingCustomerEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "detail")
    var detail: String? = "" ,

    @ColumnInfo(name = "amount")
    var amount: Long
)
