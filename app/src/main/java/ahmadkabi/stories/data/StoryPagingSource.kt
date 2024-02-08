package ahmadkabi.stories.data

import ahmadkabi.stories.data.source.remote.ApiService
import ahmadkabi.stories.domain.model.Story
import androidx.paging.PagingSource
import androidx.paging.PagingState

class StoryPagingSource(private val token: String, private val apiService: ApiService) :
    PagingSource<Int, ahmadkabi.stories.domain.model.Story>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ahmadkabi.stories.domain.model.Story> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getStories(
                "Bearer $token",
                page,
                params.loadSize
            )

            LoadResult.Page(
                data = responseData.listStory,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.listStory.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ahmadkabi.stories.domain.model.Story>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}