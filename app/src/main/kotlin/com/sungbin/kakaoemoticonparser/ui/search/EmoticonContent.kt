package com.sungbin.kakaoemoticonparser.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Fullscreen
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.glide.GlideImage
import com.sungbin.kakaoemoticonparser.emoticon.model.ContentItem
import com.sungbin.kakaoemoticonparser.theme.typography

@Composable
fun EmoticonContent(emoticon: ContentItem) {
    Card(
        modifier = Modifier.fillMaxWidth().height(100.dp),
        elevation = 20.dp
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            GlideImage(
                data = emoticon.titleImageUrl!!,
                contentDescription = null,
                modifier = Modifier.size(75.dp)
            )
            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = emoticon.title!!,
                    style = typography.body1
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Icon(
                        imageVector = Icons.Outlined.MusicNote,
                        contentDescription = null
                    )
                    Icon(
                        imageVector = Icons.Outlined.Fullscreen,
                        contentDescription = null
                    )
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = null
                    )
                }
            }
        }
    }
}
