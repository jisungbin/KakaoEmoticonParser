package com.sungbin.kakaoemoticonparser.ui.dialog

/**
 * Created by SungBin on 2020-08-11.
 */

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ScrollView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sungbin.kakaoemoticonparser.R
import com.sungbin.kakaoemoticonparser.model.EmoticonData
import com.sungbin.kakaoemoticonparser.module.GlideApp
import com.sungbin.kakaoemoticonparser.util.EmoticonUtil
import com.sungbin.kakaoemoticonparser.util.ParseUtil
import com.sungbin.sungbintool.extensions.get
import com.sungbin.sungbintool.util.ToastLength
import com.sungbin.sungbintool.util.ToastType
import com.sungbin.sungbintool.util.ToastUtil
import kotlinx.coroutines.*
import org.jetbrains.anko.support.v4.runOnUiThread


class EmoticonDetailBottomDialog(val activity: Activity, val item: EmoticonData) :
    BottomSheetDialogFragment() {

    private val downloadDialog by lazy {
        DownloadingDialog(activity)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.layout_emoticon_detail, container)
        val address = "https://e.kakao.com/t/${item.originTitle}"
        val content = ParseUtil.getHtml(address)
        val code = EmoticonUtil.getEmotionCode(content)

        GlideApp
            .with(activity)
            .load("https://item.kakaocdn.net/dw/$code.gift.jpg")
            .into(layout[R.id.iv_thumbnail] as ImageView)

        (layout[R.id.sv_container] as ScrollView).let {
            it.post {
                it.fullScroll(ScrollView.FOCUS_DOWN)
            }
        }

        (layout[R.id.btn_download] as Button).setOnClickListener {
            downloadDialog.show()

            CoroutineScope(Dispatchers.Default).launch {
                val items = async {
                    EmoticonUtil.getEmoticonList(code)
                }

                withContext(Dispatchers.IO) {
                    for ((index, url) in (items.await() ?: arrayListOf()).withIndex()) {
                        EmoticonUtil.download(activity, item, url, index)
                    }
                }

                downloadDialog.close()

                runOnUiThread {
                    ToastUtil.show(
                        activity,
                        getString(R.string.dialog_download_done),
                        ToastLength.SHORT,
                        ToastType.SUCCESS
                    )
                }
            }
        }

        return layout
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), theme)
        bottomSheetDialog.setOnShowListener {
            val bottomSheet =
                (it as BottomSheetDialog).findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheet).apply {
                state = BottomSheetBehavior.STATE_EXPANDED
                skipCollapsed = true
                isHideable = true
            }
        }
        return bottomSheetDialog
    }

}