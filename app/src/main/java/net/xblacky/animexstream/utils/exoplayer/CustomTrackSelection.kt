package net.xblacky.animexstream.utils.exoplayer

import android.content.Context
import android.text.Selection
import androidx.appcompat.app.AlertDialog
import com.google.android.exoplayer2.source.TrackGroup
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector.SelectionOverride
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo
import com.google.android.exoplayer2.trackselection.TrackSelectionUtil
import com.google.android.exoplayer2.ui.TrackSelectionDialogBuilder.DialogCallback
import com.google.android.exoplayer2.util.Assertions
import timber.log.Timber

class CustomTrackSelection(
    val context: Context,
    val title: CharSequence,
    val trackSelector: DefaultTrackSelector,
    private val rendererIndex: Int
) {


    private val mappedTrackInfo: MappedTrackInfo = trackSelector.currentMappedTrackInfo!!


    //get TrackGroups for Quality renderer=0 Represent Quality.

    private val rendererTracksGroup = mappedTrackInfo.getTrackGroups(0)

    //Get Tracks Parameter for  isOverriding Disabled or any overridden quality or audio
    private val selectionParameters = trackSelector.parameters
    val isDisabled = selectionParameters.getRendererDisabled(0)

    //This gives us a map of Overridden <TrackGroup , Index Mapping>
    private val selectionOverride =
        selectionParameters.getSelectionOverride(rendererIndex, rendererTracksGroup)


    fun showQuality() {
        //Returns TrackGroup Which Contains an array of Formats
        val trackGroup = rendererTracksGroup.get(0)
        var selectedTrack: Int = -1


        // getting index of selected track from Override tracks Its array because some videos allow multiple track selection for single track group
        selectionOverride?.tracks
        selectionOverride?.tracks?.let {
            selectedTrack = it[selectionOverride.length - 1]
        }
        val qualities = arrayListOf("Auto")
        //Getting all Formats and also checking which is selected
        for (i in trackGroup.length - 1 downTo 0) {
            val format = trackGroup.getFormat(i)
            qualities.add("${format.height}p")
            if (selectedTrack == i) {
                Timber.e("Selected Track is ${format.height}")
            } else {
                Timber.e("${format.height}")
            }
        }
        val builder = AlertDialog.Builder(context)
        builder.apply {
            setTitle("Set your playback speed")
            setSingleChoiceItems(qualities.toTypedArray(), selectedTrack) { _, which ->

            }
            setPositiveButton("OK") { dialog, _ ->
                val newOverride = SelectionOverride(0, 1)
                trackSelector.setParameters(
                    trackSelector.buildUponParameters()
                        .setSelectionOverride(rendererIndex, rendererTracksGroup, newOverride)
                )
                dialog.dismiss()
            }
            setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        }
        val dialog = builder.create()
        dialog.show()

    }
}