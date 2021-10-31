package net.xblacky.animexstream.ui.main.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import net.xblacky.animexstream.getOrAwaitValue
import net.xblacky.animexstream.ui.main.home.source.HomeFakeDefaultRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class HomeViewModelTest {


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }


    @Test
    // Repository Returns list of animeModel and check live data value
    fun fetchRecentSub_StaticData_CheckLiveDataInsertionLocation() {


        //Given
        val repository = HomeFakeDefaultRepository(HomeFakeDefaultRepository.ResponseType.SUCCESS)
        val viewmodel = HomeViewModel(repository, dispatcher = dispatcher)

        //When
        runBlockingTest {
            viewmodel.fetchHomeList()
        }


        //Check
        val list = viewmodel.animeList.getOrAwaitValue()
        assertThat(list[0].animeList?.isNotEmpty()).isEqualTo(true)
        assertThat(list[0].animeList?.get(0)?.title).contains("One Piece")
        assertThat(list[1].animeList?.isNotEmpty()).isEqualTo(true)
        assertThat(list[1].animeList?.get(0)?.title).contains("Naruto")

    }
}