package net.xblacky.animexstream.ui.main.animeinfo.epoxy

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.View
import com.airbnb.epoxy.TypedEpoxyController
import net.xblacky.animexstream.R
import net.xblacky.animexstream.ui.main.player.VideoPlayerActivity
import net.xblacky.animexstream.utils.model.EpisodeModel

class AnimeInfoController(val episodeListener: EpisodeClickListener) :
    TypedEpoxyController<ArrayList<EpisodeModel>>() {
    var animeName: String = ""
    private lateinit var isWatchedHelper: net.xblacky.animexstream.utils.helper.WatchedEpisode
    override fun buildModels(data: ArrayList<EpisodeModel>?) {
        data?.forEach {
            EpisodeModel_()
                .id(it.episodeurl)
                .episodeModel(it)
                .clickListener { model, _, _, _ ->
                    episodeListener.onEpisodeClick(model.episodeModel())
                }
                .spanSizeOverride { totalSpanCount, _, _ ->
                    totalSpanCount / totalSpanCount
                }
                .watchedProgress(isWatchedHelper.getWatchedDuration(it.episodeurl.hashCode()))
                .addTo(this)
        }
    }

    fun setAnime(animeName: String) {
        this.animeName = animeName
        isWatchedHelper = net.xblacky.animexstream.utils.helper.WatchedEpisode(animeName)
    }

    fun isWatchedHelperUpdated(): Boolean {
        return ::isWatchedHelper.isInitialized
    }

    interface EpisodeClickListener {
        fun onEpisodeClick(episodeModel: EpisodeModel)
    }

}