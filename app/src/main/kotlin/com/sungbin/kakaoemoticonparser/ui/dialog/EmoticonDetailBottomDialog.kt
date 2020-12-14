package com.sungbin.kakaoemoticonparser.ui.dialog

/**
 * Created by SungBin on 2020-08-11.
 */

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.sungbin.androidutils.util.Util
import com.sungbin.kakaoemoticonparser.databinding.LayoutEmoticonDetailBinding
import com.sungbin.kakaoemoticonparser.model.EmoticonData


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

        // todo: Flexbox Layout 써서 이쁘장하게 센터링해서 하기

        /*val content = ParseUtil.getHtml(item.url)
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
        }*/

        return layout.root
    }

    override fun getCornerRadius() = Util.dp2px(activity, 32f)
    override fun isSheetAlwaysExpanded() = true
}