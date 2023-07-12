package com.gemastik.ideation.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gemastik.ideation.api.ApiService
import com.gemastik.ideation.response.ProjectsItem

class ProjectPagingSource(private val apiService: ApiService, val token: String, val kategori:String) :
    PagingSource<Int, ProjectsItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, ProjectsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProjectsItem> {
        return try {

            //val category = "Politik"
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getProjectByCategori(token, kategori,position, params.loadSize)

            LoadResult.Page(
                data = responseData.body()?.projects ?: emptyList(),
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.body()?.projects.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }


}