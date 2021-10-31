package net.xblacky.animexstream.ui.main.home.source

import net.xblacky.animexstream.utils.Result
import net.xblacky.animexstream.utils.model.AnimeMetaModel

interface HomeDataSource {
    suspend fun getHomeData(page: Int, type: Int): Result<ArrayList<AnimeMetaModel>>
    suspend fun saveData(animeList: ArrayList<AnimeMetaModel>)
    suspend fun removeData()
}

class InvalidAnimeTypeException(message: String = "Invalid Anime Type") : Exception(message)