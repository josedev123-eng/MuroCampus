package com.rojas.murocampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rojas.murocampus.data.PostRepositoryRemote
import com.rojas.murocampus.data.remote.RetrofitClient

class PostsViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repo = PostRepositoryRemote(RetrofitClient.api)
        return PostsViewModel(repo) as T
    }
}
