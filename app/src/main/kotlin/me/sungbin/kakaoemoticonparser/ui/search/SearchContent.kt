package me.sungbin.kakaoemoticonparser.ui.search

import android.Manifest
import android.app.Activity
import android.content.Context
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import me.sungbin.androidutils.util.PermissionUtil
import me.sungbin.kakaoemoticonparser.R
import me.sungbin.kakaoemoticonparser.emoticon.DaggerEmoticonComponent
import me.sungbin.kakaoemoticonparser.emoticon.EmoticonInterface
import me.sungbin.kakaoemoticonparser.emoticon.model.ContentItem
import me.sungbin.kakaoemoticonparser.theme.AppThemeState
import me.sungbin.kakaoemoticonparser.theme.typography
import me.sungbin.kakaoemoticonparser.ui.dialog.closeLoadingDialog
import me.sungbin.kakaoemoticonparser.ui.dialog.showLoadingDialog
import me.sungbin.kakaoemoticonparser.ui.emoticon.EmoticonContent
import me.sungbin.kakaoemoticonparser.util.parseColor
import retrofit2.Retrofit

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
class SearchContent {

    @Inject
    lateinit var client: Retrofit

    @Suppress("PrivatePropertyName")
    private val EmoticonContent by lazy { EmoticonContent() }
    private lateinit var errorMessage: MutableState<String>
    private lateinit var searchState: MutableState<SearchContentState>

    init {
        DaggerEmoticonComponent.builder()
            .build()
            .inject(this)
    }

    @Composable
    fun Bind(
        appThemeState: AppThemeState,
        errorMessage: MutableState<String>,
        searchState: MutableState<SearchContentState>
    ) {
        this.errorMessage = errorMessage
        this.searchState = searchState
        BindSearchContent(appThemeState, searchState)
    }

    @Composable
    private fun BindSearchContent(
        appThemeState: AppThemeState,
        searchState: MutableState<SearchContentState>
    ) {
        val context = LocalContext.current
        val keyboardController = LocalSoftwareKeyboardController.current
        val emoticonItems = remember { mutableListOf<ContentItem>() }
        var searchText by remember { mutableStateOf(TextFieldValue()) }

        PermissionUtil.request(
            context as Activity,
            stringResource(R.string.main_need_permission),
            listOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(R.dimen.margin_default),
                        end = dimensionResource(R.dimen.margin_default),
                        top = dimensionResource(R.dimen.margin_default)
                    ),
                value = searchText,
                label = { Text(text = stringResource(R.string.main_search_emoticon)) },
                onValueChange = { searchText = it },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.clickable {
                            // todo: option
                        }
                    )
                },
                maxLines = 1,
                singleLine = true,
                keyboardActions = KeyboardActions {
                    searchEmoticon(context, searchText.text, searchState, emoticonItems)
                    // todo: option - clear `searchText` after searching.
                    keyboardController?.hideSoftwareKeyboard()
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
            )
            Crossfade(searchState.value) { state ->
                when (state) {
                    SearchContentState.RESULT -> EmoticonContent.BindListContent(
                        emoticons = emoticonItems.toList(),
                        appThemeState = appThemeState,
                        searchState = searchState,
                        errorMessage = errorMessage
                    )
                    else -> SearchOtherContent(appThemeState, state)
                }
            }
        }
    }

    @Composable
    private fun SearchOtherContent(
        appThemeState: AppThemeState,
        searchState: SearchContentState
    ) {
        val animationSpec = when (searchState) {
            SearchContentState.HOME -> LottieAnimationSpec.RawRes(R.raw.search)
            SearchContentState.ERROR -> LottieAnimationSpec.RawRes(R.raw.error)
            else -> LottieAnimationSpec.RawRes(R.raw.empty) // SearchContentState.NULL
        }
        val animationState =
            rememberLottieAnimationState(
                autoPlay = true,
                repeatCount = if (searchState == SearchContentState.HOME) Integer.MAX_VALUE else 0
            )
        val text = when (searchState) {
            SearchContentState.HOME -> stringResource(R.string.main_search_first)
            SearchContentState.ERROR -> errorMessage.value
            else -> stringResource(R.string.main_search_empty) // SearchContentState.NULL
        }
        val width = when (searchState) {
            SearchContentState.ERROR -> 100.dp
            SearchContentState.HOME -> 250.dp
            else -> 150.dp // SearchContentState.NULL
        }
        val height = when (searchState) {
            SearchContentState.ERROR -> 100.dp
            SearchContentState.HOME -> 50.dp
            else -> 150.dp // SearchContentState.NULL
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .padding(dimensionResource(R.dimen.margin_default))
        ) {
            LottieAnimation(
                spec = animationSpec,
                animationState = animationState,
                modifier = Modifier
                    .size(width, height)
                    .padding(top = dimensionResource(R.dimen.margin_default))
            )
            Text(
                color = appThemeState.parseColor(),
                text = text,
                style = typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = dimensionResource(R.dimen.margin_twice))
            )
        }
    }

    private fun searchEmoticon(
        context: Context,
        query: String,
        searchState: MutableState<SearchContentState>,
        emoticonItems: MutableList<ContentItem>
    ) {
        client.create(EmoticonInterface::class.java).run {
            showLoadingDialog(context)
            emoticonItems.clear()
            getSearchData(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        emoticonItems.addAll(response.result.content)
                    },
                    {
                        searchState.value = SearchContentState.ERROR
                        errorMessage.value = it.message.toString()
                        closeLoadingDialog()
                    },
                    {
                        searchState.value = if (emoticonItems.isEmpty()) {
                            SearchContentState.NULL
                        } else SearchContentState.RESULT
                        closeLoadingDialog()
                    }
                )
        }
    }
}
