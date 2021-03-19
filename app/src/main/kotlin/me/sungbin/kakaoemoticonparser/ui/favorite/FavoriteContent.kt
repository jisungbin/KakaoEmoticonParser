package me.sungbin.kakaoemoticonparser.ui.favorite

import androidx.compose.animation.Crossfade
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import me.sungbin.kakaoemoticonparser.emoticon.model.ContentItem
import me.sungbin.kakaoemoticonparser.emoticon.room.EmoticonDatabase
import me.sungbin.kakaoemoticonparser.theme.AppThemeState
import me.sungbin.kakaoemoticonparser.ui.NavigationType
import me.sungbin.kakaoemoticonparser.ui.dialog.closeLoadingDialog
import me.sungbin.kakaoemoticonparser.ui.dialog.showLoadingDialog
import me.sungbin.kakaoemoticonparser.ui.emoticon.EmoticonContent
import me.sungbin.kakaoemoticonparser.ui.search.SearchContentState

@ExperimentalMaterialApi
@Composable
fun FavoriteContent(
    appThemeState: AppThemeState,
    searchState: MutableState<SearchContentState>,
    errorMessage: MutableState<String>,
    contentType: MutableState<NavigationType>
) {
    val context = LocalContext.current
    val isLoadingDone = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val emoticons = remember { mutableListOf<ContentItem>() }
    coroutineScope.launch {
        EmoticonDatabase.instance(context).dao().getAllFavoriteEmoticon().forEach {
            val contentItem = ContentItem(
                title = it.title,
                titleImageUrl = it.imageUrl,
                isBigEmo = it.isBigEmo,
                isSound = it.isSound,
                isLike = false,
                artist = "",
                isToday = false,
                isPackage = false,
                isNew = false,
                isOnSale = false,
                titleDetailUrl = "",
                titleUrl = it.titleUrl
            )
            emoticons.add(contentItem)
        }
        isLoadingDone.value = true
    }
    Crossfade(isLoadingDone.value) { isLoaded ->
        if (isLoaded) {
            closeLoadingDialog()
            EmoticonContent().BindListContent(
                emoticons = emoticons.toList(),
                appThemeState = appThemeState,
                searchState = searchState,
                errorMessage = errorMessage,
                contentType = contentType
            )
        } else {
            showLoadingDialog(context)
        }
    }
}
