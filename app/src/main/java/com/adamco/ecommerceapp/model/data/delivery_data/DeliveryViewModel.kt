package com.adamco.ecommerceapp.model.data.delivery_data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DeliveryViewModel : ViewModel() {
    private val _finalAddress = MutableLiveData<Address>()
    val finalAddress: LiveData<Address> get() = _finalAddress

    private val _selectedPosition = MutableLiveData<Int>()
    val selectedPosition: LiveData<Int> get() = _selectedPosition

    fun selectFinalAddress(address: Address, position: Int) {
        Log.d("DeliveryViewModel", "Posting address: $address")
        _finalAddress.postValue(address)
        _selectedPosition.postValue(position)
    }
}
