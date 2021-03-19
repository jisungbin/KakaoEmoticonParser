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

interface OnFavoriteLoadDone {
    @Composable
    fun OnLoadDone(emoticons: List<ContentItem>)
}

private lateinit var listener: OnFavoriteLoadDone

private fun setFavoriteOnLoadDoneListener(action: @Composable List<ContentItem>.() -> Unit) {
    listener = object : OnFavoriteLoadDone {
        @Composable
        override fun OnLoadDone(emoticons: List<ContentItem>) {
            action(emoticons)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun FavoriteContent(
    appThemeState: AppThemeState,
    searchState: MutableState<SearchContentState>,
    errorMessage: MutableState<String>
) {
    val context = LocalContext.current
    setFavoriteOnLoadDoneListener {
        EmoticonContent().BindListContent(
            emoticons = toList(),
            appThemeState = appThemeState,
            searchState = searchState,
            errorMessage = errorMessage
        )
    }

    CoroutineScope(Dispatchers.IO).launch {
        val emoticons = mutableListOf<ContentItem>()
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
        listener.OnLoadDone(emoticons)
    }
}
