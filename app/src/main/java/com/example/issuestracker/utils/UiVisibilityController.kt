package com.example.issuestracker.utils

import android.view.View
import androidx.lifecycle.MutableLiveData

class UiVisibilityController {
    val content = MutableLiveData<Int>()
    val progress = MutableLiveData<Int>()
    val errorVisibility = MutableLiveData<Int>()
    val errorMessage = MutableLiveData<String>()
    fun showContent() {
        content.value = View.VISIBLE
        progress.value = View.GONE
        errorVisibility.value = View.GONE
    }

    fun showProgress() {
        content.value = View.GONE
        progress.value = View.VISIBLE
        errorVisibility.value = View.GONE
    }

    fun showError(msg: String) {
        content.value = View.GONE
        progress.value = View.GONE
        errorMessage.value = msg
        errorVisibility.value = View.VISIBLE
    }
}