package ahmadkabi.stories.ui.home.story

import ahmadkabi.stories.data.StoryRepository
import ahmadkabi.stories.domain.model.Story
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn

class StoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun getStories(): LiveData<PagingData<ahmadkabi.stories.domain.model.Story>> =
        storyRepository.getStories().cachedIn(viewModelScope)

}