package net.xblacky.animexstream.utils.animation

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.android.synthetic.main.exo_player_custom_controls.*
import net.xblacky.animexstream.R

object CustomAnimation {

    fun zoomInByScale(scaleBy: Float, player: PlayerView) {
        val animation = ScaleAnimation(
            1f,
            scaleBy,
            1f,
            scaleBy,
            ScaleAnimation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        animation.fillAfter = true
        animation.duration = 200
        player.videoSurfaceView?.startAnimation(animation)
    }

    fun zoomOutByScale(scaleBy: Float, player: PlayerView) {

        val animation = ScaleAnimation(
            scaleBy, 1f, scaleBy, 1f, ScaleAnimation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        animation.duration = 200
        player.videoSurfaceView?.startAnimation(animation)
    }

    fun rotateForward(view: View) {
        view.animate().rotation(45f).setDuration(200).withEndAction {
            view.animate().rotation(0f).setDuration(100).start()
        }
    }

    fun rotateBackward(view: View) {
        view.animate().rotation(-45f).setDuration(200).withEndAction {
            view.animate().rotation(0f).setDuration(100).start()
        }
    }

    fun rewindAnimate(minusRewind: View, rewind: View) {
        val animObj = AnimationUtils.loadAnimation(minusRewind.context, R.anim.move_left)
        animObj.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                minusRewind.alpha = 1f
                rewind.alpha = 0f
                minusRewind.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation?) {
                minusRewind.animate().alpha(0f).setDuration(200).withEndAction {
                    rewind.alpha = 1f
                }

                minusRewind.visibility = View.INVISIBLE
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

        })
        minusRewind.startAnimation(animObj)
    }


    fun forwardAnimate(plusForward: View, forward: View) {
        val animObj = AnimationUtils.loadAnimation(plusForward.context, R.anim.move_right)
        animObj.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                plusForward.alpha = 1f
                forward.alpha = 0f
                plusForward.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation?) {
                plusForward.animate().alpha(0f).setDuration(200).withEndAction {
                    forward.alpha = 1f
                }

                plusForward.visibility = View.INVISIBLE
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

        })
        plusForward.startAnimation(animObj)
    }
}