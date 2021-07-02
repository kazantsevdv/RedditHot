package com.kazantsev.reddithot.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kazantsev.reddithot.model.Post
import com.kazantsev.reddithot.repo.RedditRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Provider


class RadditViewModel @Inject constructor(private val repository: RedditRepo) : ViewModel() {

    fun getPosts(): Flow<PagingData<Post>> {
        return repository.getPosts().cachedIn(viewModelScope)
    }


    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val viewModerProvider: Provider<RadditViewModel>
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            require(modelClass == RadditViewModel::class.java)
            return viewModerProvider.get() as T
        }
    }
}
