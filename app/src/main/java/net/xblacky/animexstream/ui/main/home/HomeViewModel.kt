package net.xblacky.animexstream.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import net.xblacky.animexstream.ui.main.home.di.HomeRepositoryModule
import net.xblacky.animexstream.ui.main.home.source.HomeRepository
import net.xblacky.animexstream.utils.Result
import net.xblacky.animexstream.utils.Utils
import net.xblacky.animexstream.utils.constants.C
import net.xblacky.animexstream.utils.di.DispatcherModule
import net.xblacky.animexstream.utils.model.AnimeMetaModel
import net.xblacky.animexstream.utils.model.HomeScreenModel
import net.xblacky.animexstream.utils.model.UpdateModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @HomeRepositoryModule.HomeRepo private val repository: HomeRepository,
    @DispatcherModule.MainDispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _animeList: MutableLiveData<ArrayList<HomeScreenModel>> =
        MutableLiveData(makeEmptyArrayList())
    var animeList: LiveData<ArrayList<HomeScreenModel>> = _animeList


    private var _updateModel: MutableLiveData<UpdateModel> = MutableLiveData()
    var updateModel: LiveData<UpdateModel> = _updateModel
    private lateinit var database: DatabaseReference

    init {
        fetchHomeList()

    }

    fun fetchHomeList() {
        viewModelScope.async {
            fetchPopular()
            fetchNewSeason()
            fetchMovies()
            fetchRecentDub()
            fetchRecentSub()
        }
    }


    //TODO Move this code to Main Activity Repo
//    private fun queryDB() {
//        database = Firebase.database.reference
//        val query: Query = database.child("appdata")
//        query.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onCancelled(ignored: DatabaseError) {
//                Timber.e(ignored.message)
//            }
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                Timber.e(snapshot.toString())
//                _updateModel.value = UpdateModel(
//                    versionCode = snapshot.child("versionCode").value as Long,
//                    whatsNew = snapshot.child("whatsNew").value.toString()
//                )
//            }
//
//        })
//    }


    private fun updateData(result: Result<ArrayList<AnimeMetaModel>>, typeValue: Int) {
        if (result is Result.Success) {
            val homeScreenModel = HomeScreenModel(
                typeValue = typeValue,
                type = Utils.getTypeName(typeValue),
                animeList = result.data
            )
            val newList = animeList.value
            try {
                newList?.set(getPositionByType(typeValue), homeScreenModel)

            } catch (iobe: IndexOutOfBoundsException) {
            }
            _animeList.postValue(newList)
        }
    }

    private fun getPositionByType(typeValue: Int): Int {
        return when (typeValue) {
            C.TYPE_RECENT_SUB -> C.RECENT_SUB_POSITION
            C.TYPE_RECENT_DUB -> C.RECENT_DUB_POSITION
            C.TYPE_MOVIE -> C.MOVIE_POSITION
            C.TYPE_NEW_SEASON -> C.NEWEST_SEASON_POSITION
            C.TYPE_POPULAR_ANIME -> C.POPULAR_POSITION
            else -> 0
        }
    }


    private fun makeEmptyArrayList(): ArrayList<HomeScreenModel> {
        var i = 1
        val arrayList: ArrayList<HomeScreenModel> = ArrayList()
        while (i <= 6) {
            arrayList.add(
                HomeScreenModel(
                    typeValue = i
                )
            )
            i++
        }
        return arrayList
    }

    private suspend fun fetchRecentSub() {
        repository.fetchHomeData(1, C.RECENT_SUB).collect {
            updateData(it, C.TYPE_RECENT_SUB)
        }
    }

    private suspend fun fetchRecentDub() {
        repository.fetchHomeData(1, C.TYPE_RECENT_DUB).collect {
            updateData(it, C.TYPE_RECENT_DUB)
        }
    }

    private suspend fun fetchMovies() {
        repository.fetchHomeData(1, C.TYPE_MOVIE).collect {
            updateData(it, C.TYPE_MOVIE)
        }
    }

    private suspend fun fetchPopular() {
        repository.fetchHomeData(1, C.TYPE_POPULAR_ANIME).collect {
            updateData(it, C.TYPE_POPULAR_ANIME)
        }
    }

    override fun onCleared() {
        viewModelScope.launch(dispatcher) {
            repository.removeOldData()
        }
        super.onCleared()
    }

    private suspend fun fetchNewSeason() {
        repository.fetchHomeData(1, C.TYPE_NEW_SEASON).collect {
            updateData(it, C.TYPE_NEW_SEASON)
        }
    }

}