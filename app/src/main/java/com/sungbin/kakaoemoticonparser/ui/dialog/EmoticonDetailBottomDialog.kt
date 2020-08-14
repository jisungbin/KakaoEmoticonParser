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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sungbin.kakaoemoticonparser.R
import com.sungbin.kakaoemoticonparser.model.EmoticonData
import com.sungbin.kakaoemoticonparser.module.GlideApp
import com.sungbin.kakaoemoticonparser.utils.EmoticonUtils
import com.sungbin.kakaoemoticonparser.utils.ParseUtils
import com.sungbin.sungbintool.ToastUtils
import com.sungbin.sungbintool.Utils
import com.sungbin.sungbintool.extensions.get
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.runOnUiThread


class EmoticonDetailBottomDialog constructor(val activity: Activity, val item: EmoticonData) :
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
        Utils.setUserAgent(ParseUtils.MOBILE_USER_AGENT)
        val content = Utils.getHtml(address)!!
        val code =
            content.split("data-item-code=\"")[2].split("\"")[0].trim()
                .toLong()

        GlideApp
            .with(activity)
            .load("https://item.kakaocdn.net/dw/$code.gift.jpg")
            .into(layout[R.id.iv_thumbnail] as ImageView)

        (layout[R.id.btn_download] as Button).setOnClickListener {
            downloadDialog.show()

            CoroutineScope(Dispatchers.Default).launch {
                val items = async {
                    EmoticonUtils.getEmoticonList(code)
                }.await() ?: arrayListOf()

                async {
                    for ((index, url) in items.withIndex())  {
                        EmoticonUtils.download(activity, item, url, index)
                    }
                }.await()

                downloadDialog.close()

                runOnUiThread {
                    ToastUtils.show(activity, "다운로드 완료!", ToastUtils.SHORT, ToastUtils.SUCCESS)
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