package com.gocardless.app.ui.feature.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gocardless.gocardlesssdk.GoCardlessSDK
import com.gocardless.gocardlesssdk.model.BillingRequest
import com.gocardless.gocardlesssdk.model.Customer
import com.gocardless.gocardlesssdk.network.ApiError
import com.gocardless.gocardlesssdk.network.ApiSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MainUiState {
    object Init : MainUiState()
    object Loading : MainUiState()
    data class Success(val customers: String) : MainUiState()
    data class Error(val message: String) : MainUiState()
}

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Init)
    val uiState: StateFlow<MainUiState> = _uiState

    init {
        fetchCustomers()
    }

    fun fetchCustomers() {
        _uiState.value = MainUiState.Loading
        viewModelScope.launch {
            val response = GoCardlessSDK.billingRequestService.createBillingRequest(
                BillingRequest(

                )
            )

            when(response) {
                is ApiSuccess -> {
                    //_uiState.value = MainUiState.Success(response.value)
                }
                is ApiError -> {
                    _uiState.value = MainUiState.Error("Couldn't fetch")
                }
            }
        }
    }

    fun deleteCustomer(customer: Customer) {
        _uiState.value = MainUiState.Loading
        viewModelScope.launch {
        }
    }

    fun addCustomer(first: String, second: String) {
        Log.i("gunhanx", " add: $first $second")
    }
}