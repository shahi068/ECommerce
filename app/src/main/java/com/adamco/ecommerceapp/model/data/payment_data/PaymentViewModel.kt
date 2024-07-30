package com.adamco.ecommerceapp.model.data.payment_data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PaymentViewModel : ViewModel() {
    private val _paymentMethod = MutableLiveData<String>()
    val paymentMethod: LiveData<String> get() = _paymentMethod

    private val _selectedPosition = MutableLiveData<Int>()
    val selectedPosition: LiveData<Int> get() = _selectedPosition

    fun selectFinalPayment(paymentOption: String, position: Int) {
        Log.d("PaymentViewModel", "Posting payment method: $paymentOption")
        _paymentMethod.postValue(paymentOption)
        _selectedPosition.postValue(position)
    }
}
