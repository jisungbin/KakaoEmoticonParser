package com.sungbin.kakaoemoticonparser.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.glide.GlideImage
import com.sungbin.kakaoemoticonparser.R

@Composable
fun EmoticonContent(emoticonCoverUri: String, emoticonName: String) {
    Card(modifier = Modifier.padding(dimensionResource(R.dimen.margin_half))) {
        Row {
            GlideImage(
                data = emoticonCoverUri,
                content = {},
                modifier = Modifier
                    .size(75.dp)
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.margin_twice_half)))
            )
            Column(modifier = Modifier.padding(start = dimensionResource(R.dimen.margin_half))) {
                Text(text = emoticonName)
            }
        }
    }
}
