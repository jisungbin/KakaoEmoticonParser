package me.sungbin.kakaoemoticonparser.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import me.sungbin.kakaoemoticonparser.R

lateinit var alert: AlertDialog

fun showLoadingDialog(context: Context) {
    if (!::alert.isInitialized) {
        val dialog = AlertDialog.Builder(context)
        dialog.setView(
            LottieAnimationView(context).apply {
                setAnimation(R.raw.loading)
                repeatCount = LottieDrawable.INFINITE
                // todo: setColorFilter
                playAnimation()
            }
        )
        dialog.setCancelable(false)
        alert = dialog.create()
        alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    alert.show()
}

fun closeLoadingDialog() {
    alert.cancel()
}
