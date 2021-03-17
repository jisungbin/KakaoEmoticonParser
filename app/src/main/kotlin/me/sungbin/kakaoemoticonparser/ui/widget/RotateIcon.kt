package me.sungbin.kakaoemoticonparser.ui.widget

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun RotateIcon(
    state: Boolean,
    imageVector: ImageVector,
    angle: Float,
    duration: Int,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        modifier = modifier
            .graphicsLayer(
                rotationZ = animateFloatAsState(if (state) 0f else angle, tween(duration))
                    .value
            )
    )
}
