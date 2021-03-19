package me.sungbin.kakaoemoticonparser.ui.favorite

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.sungbin.kakaoemoticonparser.emoticon.model.ContentItem
import me.sungbin.kakaoemoticonparser.emoticon.room.EmoticonDatabase
import me.sungbin.kakaoemoticonparser.theme.AppThemeState
import me.sungbin.kakaoemoticonparser.ui.emoticon.EmoticonContent
import me.sungbin.kakaoemoticonparser.ui.search.SearchContentState

@ExperimentalMaterialApi
@Composable
fun FavoriteContent(
    appThemeState: AppThemeState,
    searchState: MutableState<SearchContentState>,
    errorMessage: MutableState<String>
) {
    val context = LocalContext.current
    val emoticons = mutableListOf<ContentItem>()
    CoroutineScope(Dispatchers.IO).launch {
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
                titleUrl = ""
            )
            emoticons.add(contentItem)
        }
    }
    EmoticonContent().BindListContent(
        emoticons = emoticons.toList(),
        appThemeState = appThemeState,
        searchState = searchState,
        errorMessage = errorMessage
    )
}
