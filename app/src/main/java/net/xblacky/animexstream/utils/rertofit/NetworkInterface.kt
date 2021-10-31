package net.xblacky.animexstream.utils.rertofit

import io.reactivex.Observable
import net.xblacky.animexstream.utils.constants.C
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Query
import retrofit2.http.Url

class NetworkInterface {


    interface HomeDataService{

        @GET("https://ajax.gogocdn.net/ajax/page-recent-release.html")
        suspend fun fetchRecentSubOrDub(
            @HeaderMap header: Map<String, String>,
            @Query("page") page: Int,
            @Query("type") type: Int
        ): ResponseBody

        @GET("https://ajax.gogocdn.net/ajax/page-recent-release-ongoing.html")
        suspend fun fetchPopularFromAjax(
            @HeaderMap header: Map<String, String>,
            @Query("page") page: Int
        ): ResponseBody

        @GET("/anime-movies.html")
        suspend fun fetchMovies(
            @HeaderMap header: Map<String, String>,
            @Query("page") page: Int
        ): ResponseBody

        @GET("/new-season.html")
        suspend fun fetchNewestSeason(
            @HeaderMap header: Map<String, String>,
            @Query("page") page: Int
        ): ResponseBody
    }

    interface FetchEpisodeMediaUrl {

        @GET
        fun get(
            @HeaderMap header: Map<String, String>,
            @Url url: String
        ): Observable<ResponseBody>

    }

    interface FetchAnimeInfo {
        @GET
        fun get(
            @HeaderMap header: Map<String, String>,
            @Url url: String
        ): Observable<ResponseBody>
    }

    interface FetchM3u8Url {

        @GET
        fun get(
            @HeaderMap header: Map<String, String>,
            @Url url: String
        ): Observable<ResponseBody>
    }

    interface FetchEpisodeList {

        @GET(C.EPISODE_LOAD_URL)
        fun get(
            @HeaderMap header: Map<String, String>,
            @Query("ep_start") startEpisode: Int = 0,
            @Query("ep_end") endEpisode: String,
            @Query("id") id: String,
            @Query("default_ep") defaultEp: Int = 0,
            @Query("alias") alias: String
        ): Observable<ResponseBody>
    }

    interface FetchSearchData {

        @GET(C.SEARCH_URL)
        fun get(
            @HeaderMap header: Map<String, String>,
            @Query("keyword") keyword: String,
            @Query("page") page: Int
        ): Observable<ResponseBody>
    }

}