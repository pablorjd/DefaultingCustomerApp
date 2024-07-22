package dev.pablorjd.listadeudores.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.pablorjd.listadeudores.data.repository.DefaultingCustomerRepository
import dev.pablorjd.listadeudores.domain.model.DefaultingCustomerModel
import dev.pablorjd.listadeudores.domain.usecase.GetDefaultingCustomerUseCase
import dev.pablorjd.listadeudores.domain.usecase.InsertDefaultingCustomerUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDefaultingCustomerUseCase: GetDefaultingCustomerUseCase,
    private val insertDefaultingCustomerUseCase: InsertDefaultingCustomerUseCase
) : ViewModel() {

    val defaultingCustomer: Flow<List<DefaultingCustomerModel>> = getDefaultingCustomerUseCase()

    fun insertDefaultingCustomer(defaultingCustomerModel: DefaultingCustomerModel) {
        viewModelScope.launch {
            insertDefaultingCustomerUseCase(defaultingCustomerModel)
        }
    }

}