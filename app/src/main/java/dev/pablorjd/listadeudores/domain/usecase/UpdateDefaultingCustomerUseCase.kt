package dev.pablorjd.listadeudores.domain.usecase

import dev.pablorjd.listadeudores.data.repository.DefaultingCustomerRepository
import dev.pablorjd.listadeudores.domain.model.DefaultingCustomerModel
import javax.inject.Inject

class UpdateDefaultingCustomerUseCase @Inject constructor(private val customerRepository: DefaultingCustomerRepository) {

    suspend operator fun invoke(defaultingCustomerModel: DefaultingCustomerModel) {
        customerRepository.add(defaultingCustomerModel)
    }
}