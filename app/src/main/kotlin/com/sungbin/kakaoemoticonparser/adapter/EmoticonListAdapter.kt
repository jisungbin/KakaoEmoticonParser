package com.sungbin.kakaoemoticonparser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sungbin.kakaoemoticonparser.R
import com.sungbin.kakaoemoticonparser.databinding.LayoutEmoticonPanelBinding
import com.sungbin.kakaoemoticonparser.model.EmoticonData


/**
 * Created by SungBin on 2020-07-20.
 */

class EmoticonListAdapter constructor(
    private val items: List<EmoticonData>
) : RecyclerView.Adapter<EmoticonListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onClick(item: EmoticonData)
    }

    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(action: (EmoticonData) -> Unit) {
        listener = object : OnItemClickListener {
            override fun onClick(item: EmoticonData) {
                action(item)
            }
        }
    }

    class ViewHolder(private val itemBinding: LayoutEmoticonPanelBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bindViewHolder(item: EmoticonData) {
            itemBinding.item = item
            itemBinding.tvName.isSelected = true
        }

        fun setOnClick(listener: OnItemClickListener?, item: EmoticonData) {
            itemBinding.cvPanel.setOnClickListener {
                listener?.onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.layout_emoticon_panel, viewGroup, false
            )
        )

    override fun onBindViewHolder(@NonNull viewholder: ViewHolder, position: Int) {
        viewholder.bindViewHolder(items[position])
        viewholder.setOnClick(listener, items[position])
    }

    override fun getItemCount() = items.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
}