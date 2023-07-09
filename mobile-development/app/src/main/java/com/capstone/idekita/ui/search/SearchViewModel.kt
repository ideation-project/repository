package com.capstone.idekita.ui.search

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.capstone.idekita.data.ProjectRepository
import com.capstone.idekita.response.ProjectsItem

class SearchViewModel(private val projectRepository: ProjectRepository):ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

//    val project = currentQuery.switchMap { queryString->
//        projectRepository.searchProject(queryString).cachedIn(viewModelScope)
//    }
//
//    fun searchProject(token:String): LiveData<PagingData<ProjectsItem>>{
//      return projectRepository.searchProject(token,"A").cachedIn(viewModelScope)
//    }


    companion object {
        private const val DEFAULT_QUERY = "A"
    }
}