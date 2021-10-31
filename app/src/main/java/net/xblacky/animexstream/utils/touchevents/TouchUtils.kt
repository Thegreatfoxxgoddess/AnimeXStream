package net.xblacky.animexstream.utils.touchevents

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.WindowManager
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView
import timber.log.Timber
import kotlin.math.abs

fun isAClick(startX: Float, endX: Float, startY: Float, endY: Float): Boolean {
    val CLICK_ACTION_THRESHOLD = 200
    val differenceX = abs(startX - endX)
    val differenceY = abs(startY - endY)
    return !(differenceX > CLICK_ACTION_THRESHOLD /* =5 */ || differenceY > CLICK_ACTION_THRESHOLD)
}

object TouchUtils {
    var startX = 0f
    var startY = 0f
    fun isAClick(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                startY = event.y
            }
            MotionEvent.ACTION_UP -> {
                val endX = event.x
                val endY = event.y
                if (isAClick(startX, endX, startY, endY)) {
                    return true
                }
            }
        }
        return false
    }

    fun calculateScale(player: PlayerView): Float {
        val scaleBy = player.width.toFloat() / player.videoSurfaceView?.width!!.toFloat()
        Timber.e("Scale By $scaleBy")
        return if (scaleBy > 1) scaleBy else 1f
    }
}



