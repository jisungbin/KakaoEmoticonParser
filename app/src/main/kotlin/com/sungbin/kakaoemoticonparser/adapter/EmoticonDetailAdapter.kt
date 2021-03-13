package com.sungbin.kakaoemoticonparser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.sungbin.androidutils.extensions.get
import com.sungbin.kakaoemoticonparser.R
import com.sungbin.kakaoemoticonparser.module.GlideApp

class EmoticonDetailAdapter(private val items: ArrayList<String>) :
    RecyclerView.Adapter<EmoticonDetailAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView = view[R.id.iv_image, ImageView::class.java]
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
