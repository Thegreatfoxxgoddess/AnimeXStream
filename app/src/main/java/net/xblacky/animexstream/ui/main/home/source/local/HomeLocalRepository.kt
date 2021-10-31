package net.xblacky.animexstream.ui.main.home.source.local

import io.realm.Realm
import io.realm.Sort
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import net.xblacky.animexstream.ui.main.home.source.HomeDataSource
import net.xblacky.animexstream.utils.constants.C
import net.xblacky.animexstream.utils.di.DispatcherModule
import net.xblacky.animexstream.utils.model.AnimeMetaModel
import net.xblacky.animexstream.utils.realm.InitalizeRealm
import javax.inject.Inject
import net.xblacky.animexstream.utils.Result

class HomeLocalRepository @Inject constructor(@DispatcherModule.IoDispatcher val dispatcher: CoroutineDispatcher) :
    HomeDataSource {
    override suspend fun getHomeData(page: Int, type: Int): Result<ArrayList<AnimeMetaModel>> {
        return withContext(dispatcher) {
            val realm: Realm = Realm.getInstance(InitalizeRealm.getConfig())

            val list: ArrayList<AnimeMetaModel> = ArrayList()
            try {
                val results =
                    realm.where(AnimeMetaModel::class.java)?.equalTo("typeValue", type)
                        ?.sort("insertionOrder", Sort.ASCENDING)?.findAll()
                results?.let {
                    list.addAll(realm.copyFromRealm(results))
                }
                Result.Success(list)

            } catch (exc: Exception) {
                Result.Error(exc)
            } finally {
                realm.close()
            }
        }

    }


    override suspend fun saveData(animeList: ArrayList<AnimeMetaModel>) {

        withContext(dispatcher) {
            val realm: Realm = Realm.getInstance(InitalizeRealm.getConfig())
            try {
                realm.executeTransaction { realm1: Realm ->
                    realm1.insertOrUpdate(animeList)
                }
            } catch (ignored: Exception) {
            } finally {
                realm.close()
            }
        }

    }

    override suspend fun removeData() {
        withContext(dispatcher) {
            val realm: Realm = Realm.getInstance(InitalizeRealm.getConfig())
            realm.executeTransaction {
                val results = it.where(AnimeMetaModel::class.java)
                    .lessThanOrEqualTo(
                        "timestamp",
                        System.currentTimeMillis() - C.MAX_TIME_FOR_ANIME
                    )
                    .findAll()
                results.deleteAllFromRealm()
            }
            realm.close()
        }

    }


}