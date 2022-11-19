package ahmadkabi.storyapp.data

import ahmadkabi.storyapp.data.source.remote.ApiResponse
import ahmadkabi.storyapp.data.source.remote.ApiService
import ahmadkabi.storyapp.data.source.remote.model.LoginBody
import ahmadkabi.storyapp.data.source.remote.model.RegisterBody
import ahmadkabi.storyapp.data.source.remote.model.Story
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository(private val token: String, private val apiService: ApiService) {

    fun getStories(): LiveData<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(token, apiService)
            }
        ).liveData
    }

    suspend fun getMappedStories() =
        ApiResponse.success(apiService.getMappedStories("Bearer $token").listStory)

    suspend fun login(body: LoginBody) =
        ApiResponse.success(apiService.login(body))

    suspend fun register(body: RegisterBody) =
        ApiResponse.success(apiService.register(body))

    suspend fun addNewStory(
        file: MultipartBody.Part,
        description: RequestBody
    ) = ApiResponse.success(
        apiService.addStory(
            "Bearer $token",
            file,
            description
        )
    )

}