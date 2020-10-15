package com.sungbin.kakaoemoticonparser.util

import android.animation.AnimatorInflater
import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.sungbin.kakaoemoticonparser.R

object Animator {
    fun fadeIn(view: View, context: Context, doAfterEnd: () -> Unit = {}) {
        view.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in).apply {
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {}

                override fun onAnimationEnd(p0: Animation?) {
                    doAfterEnd()
                }

                override fun onAnimationStart(p0: Animation?) {}
            })

            duration = 250
        })
    }

    fun fadeOut(view: View, context: Context, doAfterEnd: () -> Unit = {}) {
        view.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_out).apply {
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {}

                override fun onAnimationEnd(p0: Animation?) {
                    doAfterEnd()
                }

                override fun onAnimationStart(p0: Animation?) {}
            })

            duration = 250
        })
    }

    fun flip(view: View, context: Context) {
        val anim = AnimatorInflater.loadAnimator(context, R.animator.animation_flip)
        anim.setTarget(view)
        anim.start()
    }

}