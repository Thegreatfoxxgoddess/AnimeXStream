package net.xblacky.animexstream.ui.main.home.source

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import net.xblacky.animexstream.utils.Result
import net.xblacky.animexstream.utils.constants.C
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class HomeRepositoryTest {

    @Before
    fun setUp() {
//        Dispatchers.setMain(Dispatchers.Main)
    }


    @Test

    //Given a Static Response and check data received
    fun getDataBasedOnType_staticResponse_ExpectingList() {

        //Given
        val repository = HomeDefaultRepository(
            localRepository = HomeFakeDataSource(),
            remoteRepository = HomeFakeDataSource()
        )

        //WHEN
        runBlockingTest {
            val firstItem = repository.fetchHomeData(0, C.TYPE_RECENT_SUB).first()

            //Checking Result
            assertThat(firstItem).isInstanceOf(Result.Success::class.java)
            val data = (firstItem as Result.Success).data
            assertThat(data.size).isEqualTo(1)
            assertThat(data[0].title).contains("One")
        }

    }

    @Test
    //Given a wrong Anime Type check InvalidAnimeTypeException
    fun getDataBasedOnType_staticResponseWrongType_InvalidAnimeTypeException() {

        //Given
        val repository = HomeDefaultRepository(
            localRepository = HomeFakeDataSource(),
            remoteRepository = HomeFakeDataSource()
        )

        //WHEN
        runBlockingTest {
            val firstItem = repository.fetchHomeData(0, 99).first()

            //Checking Result
            assertThat(firstItem).isInstanceOf(Result.Error::class.java)
        }

    }

}