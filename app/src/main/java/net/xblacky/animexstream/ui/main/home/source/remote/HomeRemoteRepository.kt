package net.xblacky.animexstream.ui.main.home.source.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import net.xblacky.animexstream.ui.main.home.source.HomeDataSource
import net.xblacky.animexstream.ui.main.home.source.InvalidAnimeTypeException
import net.xblacky.animexstream.utils.Result
import net.xblacky.animexstream.utils.constants.C
import net.xblacky.animexstream.utils.di.AppModules
import net.xblacky.animexstream.utils.di.DispatcherModule
import net.xblacky.animexstream.utils.model.AnimeMetaModel
import net.xblacky.animexstream.utils.parser.HtmlParser
import net.xblacky.animexstream.utils.rertofit.NetworkInterface
import okhttp3.ResponseBody
import java.lang.Exception
import javax.inject.Inject

@Suppress("BlockingMethodInNonBlockingContext")
class HomeRemoteRepository
@Inject constructor(
    private val homeNetworkService: NetworkInterface.HomeDataService,
    @AppModules.RequestHeader
    val header: Map<String, String>,
    @DispatcherModule.IoDispatcher
    val ioDispatcher: CoroutineDispatcher
) : HomeDataSource {


    override suspend fun getHomeData(page: Int, type: Int): Result<ArrayList<AnimeMetaModel>> {
        return withContext(ioDispatcher) {
            try {
                val response = callServiceBasedOnType(page, type).string()
                val data = HtmlParser.parseDataBasedOnType(response, type)
                Result.Success(data)
            } catch (exc: Exception) {
                Result.Error(exc)
            }
        }
    }

    override suspend fun saveData(animeList: ArrayList<AnimeMetaModel>) {
        //Will Implement Later for Network Saving
    }

    override suspend fun removeData() {
        //Will Implement for later Removing from Network
    }

    private suspend fun callServiceBasedOnType(page: Int, type: Int): ResponseBody {
        return when (type) {
            C.TYPE_RECENT_SUB, C.TYPE_RECENT_DUB -> homeNetworkService.fetchRecentSubOrDub(
                header,
                page,
                type
            )
            C.TYPE_MOVIE -> homeNetworkService.fetchMovies(header, page)
            C.TYPE_NEW_SEASON -> homeNetworkService.fetchNewestSeason(header, page)
            C.TYPE_POPULAR_ANIME -> homeNetworkService.fetchPopularFromAjax(header, page)
            else -> throw InvalidAnimeTypeException()
        }
    }

}