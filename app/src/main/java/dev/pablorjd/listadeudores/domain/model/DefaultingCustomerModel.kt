package dev.pablorjd.listadeudores.domain.model

import dev.pablorjd.listadeudores.data.entities.DefaultingCustomerEntity

data class DefaultingCustomerModel(
    var id: Int,
    var name: String,
    var detail: String? = "" ,
    var amount: Long
)

fun DefaultingCustomerModel.toCustomerModel(): DefaultingCustomerEntity {
    return DefaultingCustomerEntity(
        id = id,
        name = name,
        detail = detail,
        amount = amount)
}
