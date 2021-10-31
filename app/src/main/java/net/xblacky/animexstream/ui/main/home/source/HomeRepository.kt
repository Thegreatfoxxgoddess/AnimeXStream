package net.xblacky.animexstream.ui.main.home.source

import kotlinx.coroutines.flow.Flow
import net.xblacky.animexstream.utils.Result
import net.xblacky.animexstream.utils.model.AnimeMetaModel

interface HomeRepository {
    suspend fun fetchHomeData(page: Int, type: Int): Flow<Result<ArrayList<AnimeMetaModel>>>
    suspend fun removeOldData()
}