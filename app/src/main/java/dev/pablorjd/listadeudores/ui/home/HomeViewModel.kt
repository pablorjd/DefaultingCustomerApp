package dev.pablorjd.listadeudores.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.pablorjd.listadeudores.domain.model.DefaultingCustomerModel
import dev.pablorjd.listadeudores.domain.usecase.DeleteDefaultingCustomerUseCase
import dev.pablorjd.listadeudores.domain.usecase.GetDefaultingCustomerUseCase
import dev.pablorjd.listadeudores.domain.usecase.InsertDefaultingCustomerUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDefaultingCustomerUseCase: GetDefaultingCustomerUseCase,
    private val insertDefaultingCustomerUseCase: InsertDefaultingCustomerUseCase,
    private val deleteDefaultingCustomerUseCase: DeleteDefaultingCustomerUseCase
) : ViewModel() {

    //val defaultingCustomer: Flow<List<DefaultingCustomerModel>> = getDefaultingCustomerUseCase()
    //val defaultingCustomer: Flow<List<DefaultingCustomerModel>> = getDefaultingCustomerUseCase()

    private val _items = MutableStateFlow<List<DefaultingCustomerModel>>(emptyList())
    val items: StateFlow<List<DefaultingCustomerModel>> = _items


    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog


    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch {
            getDefaultingCustomerUseCase().collect { itemList ->
                _items.value = itemList
            }
        }
    }

    fun insertDefaultingCustomer(defaultingCustomerModel: DefaultingCustomerModel) {
        Log.i("HomeViewModel", "insertDefaultingCustomer: $defaultingCustomerModel")
        viewModelScope.launch {
            _showDialog.value = false
            insertDefaultingCustomerUseCase(defaultingCustomerModel)
        }
    }

    fun deleteDefaultingCustomer(defaultingCustomerModel: DefaultingCustomerModel) {
        Log.i("HomeViewModel", "deleteDefaultingCustomer: $defaultingCustomerModel")
        viewModelScope.launch {
            deleteDefaultingCustomerUseCase(defaultingCustomerModel)
        }
    }

    fun onShowDialogClick() {
        _showDialog.value = true
    }


    fun onDialogClose() {
        _showDialog.value = false
    }

}