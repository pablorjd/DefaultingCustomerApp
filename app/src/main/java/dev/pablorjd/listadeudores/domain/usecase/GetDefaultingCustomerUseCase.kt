package dev.pablorjd.listadeudores.domain.usecase

import dev.pablorjd.listadeudores.data.repository.DefaultingCustomerRepository
import dev.pablorjd.listadeudores.domain.model.DefaultingCustomerModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDefaultingCustomerUseCase @Inject constructor(private val customerRepository: DefaultingCustomerRepository) {

    operator fun invoke(): Flow<List<DefaultingCustomerModel>> = customerRepository.allDefaultingCustomers
}