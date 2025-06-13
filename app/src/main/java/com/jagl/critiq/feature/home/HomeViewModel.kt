package com.jagl.critiq.feature.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jagl.critiq.core.repository.repository.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    mediaRepository: MediaRepository
) : ViewModel() {

    private val language: MutableLiveData<String?> = MutableLiveData(null)

    val items = mediaRepository.getPagingMedia(language.value).cachedIn(viewModelScope)

}