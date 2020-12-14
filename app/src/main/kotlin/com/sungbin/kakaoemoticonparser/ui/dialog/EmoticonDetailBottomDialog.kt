package com.sungbin.kakaoemoticonparser.ui.dialog

/**
 * Created by SungBin on 2020-08-11.
 */

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.sungbin.androidutils.util.ToastLength
import com.sungbin.androidutils.util.ToastType
import com.sungbin.androidutils.util.ToastUtil
import com.sungbin.androidutils.util.Util
import com.sungbin.kakaoemoticonparser.R
import com.sungbin.kakaoemoticonparser.databinding.LayoutEmoticonDetailBinding
import com.sungbin.kakaoemoticonparser.model.EmoticonData
import com.sungbin.kakaoemoticonparser.module.GlideApp
import com.sungbin.kakaoemoticonparser.util.EmoticonUtil
import com.sungbin.kakaoemoticonparser.util.ParseUtil
import kotlinx.coroutines.*
import org.jetbrains.anko.support.v4.runOnUiThread


class EmoticonDetailBottomDialog(private val activity: Activity, private val item: EmoticonData) :
    SuperBottomSheetFragment() {

    private val downloadDialog by lazy { DownloadingDialog(activity) }
    private val layout by lazy { LayoutEmoticonDetailBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val address = "https://e.kakao.com/t/${item.originTitle}"
        val content = ParseUtil.getHtml(address)
        val code = EmoticonUtil.getEmotionCode(content)

        GlideApp
            .with(activity)
            .load("https://item.kakaocdn.net/dw/$code.gift.jpg")
            .into(layout.ivThumbnail)

        layout.svContainer.let {
            it.post {
                it.fullScroll(ScrollView.FOCUS_DOWN)
            }
        }

        layout.btnDownload.setOnClickListener {
            downloadDialog.show()

            CoroutineScope(Dispatchers.Default).launch {
                withContext(Dispatchers.IO) {
                    val items = async {
                        EmoticonUtil.getEmoticonList(code)
                    }

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

        return layout.root
    }

    override fun getCornerRadius() = Util.dp2px(activity, 16f)
    override fun isSheetAlwaysExpanded() = true
}