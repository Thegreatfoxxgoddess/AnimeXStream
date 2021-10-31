package net.xblacky.animexstream.ui.main.home.source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.xblacky.animexstream.utils.Result
import net.xblacky.animexstream.utils.constants.C
import net.xblacky.animexstream.utils.model.AnimeMetaModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection
import javax.inject.Inject

class HomeFakeDefaultRepository @Inject constructor() : HomeRepository {

    val responseType = ResponseType.SUCCESS
    private val data = ArrayList(
        listOf(
            AnimeMetaModel(
                title = "One Piece",
                typeValue = C.TYPE_RECENT_SUB,
                categoryUrl = "URL",
                imageUrl = "https://gogocdn.net/images/anime/One-piece.jpg",
                genreList = null,
                ID = 123,
                episodeNumber = "900",
            )
        )
    )


    private val data2 = ArrayList(
        listOf(
            AnimeMetaModel(
                title = "Naruto",
                typeValue = C.TYPE_RECENT_DUB,
                categoryUrl = "URL",
                imageUrl = "https://gogocdn.net/images/anime/N/naruto.jpg",
                genreList = null,
                ID = 123,
                episodeNumber = "900",
            )
        )
    )

    private val responseBody = "Some Response Body".toResponseBody("plain/text".toMediaTypeOrNull())

    override suspend fun fetchHomeData(
        page: Int,
        type: Int
    ): Flow<Result<ArrayList<AnimeMetaModel>>> {
        return flow {
            when (responseType) {
                ResponseType.SUCCESS -> if (type == C.RECENT_SUB) emit(Result.Success(data)) else emit(
                    Result.Success(data2)
                )
                ResponseType.HTTP_ERROR -> emit(
                    Result.Error(
                        HttpException(
                            Response.error<ResponseBody>(
                                HttpURLConnection.HTTP_CLIENT_TIMEOUT,
                                responseBody
                            )
                        )
                    )
                )
                else -> Result.Error(Exception())
            }
        }
    }

    override suspend fun removeOldData() {
        TODO("Not yet implemented")
    }

    enum class ResponseType {
        SUCCESS, HTTP_ERROR, ANIME_PARSE_ERROR, EMPTY_RESPONSE
    }
}