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
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sungbin.kakaoemoticonparser.R
import com.sungbin.kakaoemoticonparser.adapter.EmoticonDetailAdapter
import com.sungbin.kakaoemoticonparser.model.EmoticonData
import com.sungbin.kakaoemoticonparser.module.GlideApp
import com.sungbin.kakaoemoticonparser.utils.EmoticonUtils
import com.sungbin.kakaoemoticonparser.utils.ParseUtils
import com.sungbin.sungbintool.Utils
import com.sungbin.sungbintool.extensions.get

class EmoticonDetailBottomDialog constructor(val activity: Activity, val item: EmoticonData) :
    BottomSheetDialogFragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.layout_emoticon_detail, container)

        val loadingDialog = LoadingDialog(activity)
        loadingDialog.show()

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

        (layout[R.id.tv_name] as TextView).text = item.title

        (layout[R.id.rv_emoticon] as RecyclerView).adapter =
            EmoticonDetailAdapter(EmoticonUtils.getEmoticonList(code) ?: arrayListOf())

        loadingDialog.close()
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