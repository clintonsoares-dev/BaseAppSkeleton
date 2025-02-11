package com.csdev.baseappskeleton.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.csdev.baseappskeleton.utils.Resource
import com.csdev.baseappskeleton.utils.set
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(
    private val application: BaseApplication? = null
) : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    protected fun startLoading() {
        _isLoading.postValue(true)
    }

    protected fun stopLoading() {
        _isLoading.postValue(false)
    }

    protected fun showError(errorMessage: String?) {
        viewModelScope.launch(Dispatchers.Default) {
            _errorMessage.set(errorMessage)
        }
    }

    protected fun launch(
        context: CoroutineContext = Dispatchers.IO,
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(context) {
            block()
        }
    }

    protected fun <T: Any , R: Resource<T>> Flow<R>.onAsyncResponseWithoutLoader(
        onSuccess: suspend (data: T?) -> Unit,
        onError: suspend (R) -> Unit = {},
        onOverrideError: (suspend (R) -> Unit)? = null
    ) {
        this.onEach { result ->
            when (result) {
                is Resource.Success<*> -> {
                    onSuccess(result.data)
                }
                is Resource.Error<*> -> {
                    onOverrideError?.let {
                        it(result)
                    } ?: run {
                        onError(result)
                        _isLoading.postValue(false)
                        _errorMessage.set(result.message)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    protected fun <T: Any , R: Resource<T>> Flow<R>.onAsyncResponse(
        onSuccess: suspend (data: T?) -> Unit,
        onError: suspend (R) -> Unit = {},
        onOverrideError: (suspend (R) -> Unit)? = null
    ) {
        this.onEach { result ->
            when (result) {
                is Resource.Success<*> -> {
                    _isLoading.postValue(false)
                    onSuccess(result.data)
                }
                is Resource.Error<*> -> {
                    onOverrideError?.let {
                        it(result)
                    } ?: run {
                        onError(result)
                        _isLoading.postValue(false)
                        _errorMessage.set(result.message)
                    }
                }
                is Resource.Loading<*> -> {
                    _isLoading.postValue(true)
                }
            }
        }.launchIn(viewModelScope)
    }

}