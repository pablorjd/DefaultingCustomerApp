package dev.pablorjd.listadeudores.data.repository

import dev.pablorjd.listadeudores.data.dao.DefaultingCustomerDao
import dev.pablorjd.listadeudores.domain.model.DefaultingCustomerModel
import dev.pablorjd.listadeudores.domain.model.toCustomerModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultingCustomerRepository @Inject constructor(private val defaultingCustomerDao: DefaultingCustomerDao){

    val allDefaultingCustomers: Flow<List<DefaultingCustomerModel>> = defaultingCustomerDao.getAllDefaultingCustomer().map { item ->
        item.map {
            DefaultingCustomerModel(it.id,it.name, it.detail, it.amount)
        }
    }
    suspend fun add(defaultingCustomerModel: DefaultingCustomerModel) {
        defaultingCustomerDao.insertDefaultingCustomer(defaultingCustomerModel.toCustomerModel())
    }
    suspend fun update(defaultingCustomerModel: DefaultingCustomerModel) {
        defaultingCustomerDao.insertDefaultingCustomer(defaultingCustomerModel.toCustomerModel())
    }
    suspend fun delete(defaultingCustomerModel: DefaultingCustomerModel) {
        defaultingCustomerDao.deleteDefaultingCustomer(defaultingCustomerModel.toCustomerModel())
    }



}