@file:Suppress("UNCHECKED_CAST")

package com.example.issuestracker.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.issuestracker.viewModels.CommentsViewModel
import com.example.issuestracker.viewModels.MainViewModel

class MyViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            MainViewModel::class.java -> MainViewModel(repository) as T
            CommentsViewModel::class.java -> CommentsViewModel(repository) as T
            else -> throw ClassNotFoundException()
        }
    }
}