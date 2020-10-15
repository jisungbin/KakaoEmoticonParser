package com.sungbin.kakaoemoticonparser.ui.dialog

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sungbin.kakaoemoticonparser.R
import org.jetbrains.anko.support.v4.defaultSharedPreferences

class SearchOptionBottomDialog : BottomSheetDialogFragment() {

    interface OnSearchOptionDialogListener {
        fun onClosed()
    }

    private var listener: OnSearchOptionDialogListener? = null

    fun setSearchOptionDialogListener(listener: () -> Unit) {
        this.listener = object : OnSearchOptionDialogListener {
            override fun onClosed() {
                listener()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.layout_search_option, container, false)
        /*etStartDepth = layout[R.id.et_start_depth] as EditText
        etStartData = layout[R.id.et_start_date] as EditText
        etEndDate = layout[R.id.et_end_date] as EditText*/

        defaultSharedPreferences.run {
            // todo
        }

        return layout
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        defaultSharedPreferences.edit {
            // todo
        }

        listener?.onClosed()
    }

    companion object {
        private lateinit var searchOptionBottomDialog: SearchOptionBottomDialog

        fun instance(): SearchOptionBottomDialog {
            if (!::searchOptionBottomDialog.isInitialized) {
                searchOptionBottomDialog = SearchOptionBottomDialog()
            }
            return searchOptionBottomDialog
        }
    }
}