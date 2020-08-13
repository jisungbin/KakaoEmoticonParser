package com.sungbin.kakaoemoticonparser.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.sungbin.kakaoemoticonparser.R
import com.sungbin.kakaoemoticonparser.module.GlideApp
import com.sungbin.sungbintool.extensions.get

class EmoticonDetailAdapter constructor(private val items: ArrayList<String>) :
    RecyclerView.Adapter<EmoticonDetailAdapter.ViewHolder>() {

    inner class ViewHolder(viewholder: View) : RecyclerView.ViewHolder(viewholder) {
        val imageView = viewholder[R.id.iv_image] as ImageView
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(
                R.layout.layout_emoticon_item,
                viewGroup,
                false
            )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull viewholder: ViewHolder, position: Int) {
        GlideApp
            .with(viewholder.imageView.context)
            .load(items[position])
            .into(viewholder.imageView)
    }

    override fun getItemCount(): Int = items.size
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getItemViewType(position: Int): Int = position
}