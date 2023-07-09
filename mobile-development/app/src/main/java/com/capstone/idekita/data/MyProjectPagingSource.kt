package com.capstone.idekita.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.capstone.idekita.api.ApiService
import com.capstone.idekita.response.DProjectsItem


class MyProjectPagingSource(
    private val token: String,
    private val apiService: ApiService,
    private val status: String,
) : PagingSource<Int, DProjectsItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DProjectsItem> {
        return try {

            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.myProject("Bearer $token", status)
            LoadResult.Page(
                data = responseData.projects,
                prevKey = if (position <= INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.projects.isEmpty()) null else position
            )
        } catch (exception: Exception) {
            Log.e("err", exception.toString())
            return LoadResult.Error(exception)

        }
    }

    override fun getRefreshKey(state: PagingState<Int, DProjectsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}