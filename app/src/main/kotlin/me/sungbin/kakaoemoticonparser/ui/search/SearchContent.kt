package me.sungbin.kakaoemoticonparser.ui.search

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState
import com.google.accompanist.glide.GlideImage
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.sungbin.androidutils.util.Logger
import me.sungbin.androidutils.util.MediaUtil
import me.sungbin.androidutils.util.PermissionUtil
import me.sungbin.androidutils.util.StorageUtil
import me.sungbin.kakaoemoticonparser.R
import me.sungbin.kakaoemoticonparser.emoticon.DaggerEmoticonComponent
import me.sungbin.kakaoemoticonparser.emoticon.EmoticonInterface
import me.sungbin.kakaoemoticonparser.emoticon.model.ContentItem
import me.sungbin.kakaoemoticonparser.emoticon.model.detail.Result
import me.sungbin.kakaoemoticonparser.theme.AppThemeState
import me.sungbin.kakaoemoticonparser.theme.shapes
import me.sungbin.kakaoemoticonparser.theme.typography
import me.sungbin.kakaoemoticonparser.util.parseColor
import retrofit2.Retrofit

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
class SearchContent {

    @Inject
    lateinit var client: Retrofit

    lateinit var context: Context
    lateinit var alert: AlertDialog
    private val emoticonContent by lazy { EmoticonContent() }

    private var errorMessage = ""
    private val emoticonItems: MutableList<ContentItem?> = mutableListOf()

    init {
        DaggerEmoticonComponent.builder()
            .build()
            .inject(this)
    }

    @Composable
    fun Bind(appThemeState: AppThemeState) {
        context = LocalContext.current
        val searchState = rememberSaveable { mutableStateOf(SearchContentState.HOME) }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = stringResource(R.string.app_name),
                                style = typography.body1
                            )
                            Text(
                                text = stringResource(R.string.copyright),
                                style = typography.caption
                            )
                        }
                    },
                    elevation = dimensionResource(R.dimen.margin_half)
                )
            },
            content = {
                BindSearchContent(appThemeState, searchState)
            }
        )
    }

    @Composable
    private fun BindSearchContent(
        appThemeState: AppThemeState,
        searchState: MutableState<SearchContentState>
    ) {
        val context = LocalContext.current
        val keyboardController = LocalSoftwareKeyboardController.current
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
                    searchEmoticon(searchText.text, searchState)
                    // todo: option - clear `searchText` after searching.
                    keyboardController?.hideSoftwareKeyboard()
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
            )
            Crossfade(searchState.value) { state ->
                when (state) {
                    SearchContentState.RESULT -> SearchResultContent(appThemeState)
                    else -> SearchOtherContent(appThemeState, state)
                }
            }
        }
    }

    @Composable
    private fun SearchResultContent(appThemeState: AppThemeState) {
        val sheetType = remember { mutableStateOf(EmoticonSheetState.DETAIL) }
        var emoticon by remember { mutableStateOf<Result?>(null) }
        val coroutineScope = rememberCoroutineScope()
        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
        )
        emoticonContent.setOnEmoticonClickListener {
            Logger.w("onEmoticonClicked")
            emoticon = this
            coroutineScope.launch {
                if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                    bottomSheetScaffoldState.bottomSheetState.expand()
                }
            }
        }
        BottomSheetScaffold(
            sheetShape = shapes.large,
            sheetElevation = dimensionResource(R.dimen.margin_twice_half),
            scaffoldState = bottomSheetScaffoldState,
            sheetPeekHeight = 0.dp,
            sheetContent = {
                when (sheetType.value) {
                    EmoticonSheetState.DETAIL -> EmoticonDetailContent(
                        emoticon = emoticon,
                        emoticonSheetState = sheetType
                    )
                    EmoticonSheetState.DOWNLOADING -> EmoticonDownloadingContent(
                        appThemeState = appThemeState,
                        emoticon = emoticon,
                        coroutineScope = coroutineScope,
                        emoticonSheetState = sheetType
                    )
                    EmoticonSheetState.DOWNLOADDONE -> EmoticonDownloadDoneContent(
                        appThemeState = appThemeState,
                        coroutineScope = coroutineScope,
                        bottomSheetScaffoldState = bottomSheetScaffoldState,
                        emoticonSheetState = sheetType
                    )
                }
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = dimensionResource(R.dimen.margin_half),
                        top = dimensionResource(R.dimen.margin_half)
                    )
            ) {
                items(
                    items = emoticonItems,
                    itemContent = { emoticon ->
                        Box(
                            Modifier.padding(
                                start = dimensionResource(R.dimen.margin_default),
                                end = dimensionResource(R.dimen.margin_default),
                                bottom = dimensionResource(R.dimen.margin_half),
                                top = dimensionResource(R.dimen.margin_half)
                            )
                        ) {
                            emoticonContent.Bind(emoticon!!)
                        }
                    }
                )
            }
        }
    }

    @Composable
    private fun EmoticonDetailContent(
        emoticon: Result?,
        emoticonSheetState: MutableState<EmoticonSheetState>
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.margin_default)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            GlideImage(
                data = emoticon?.giftImageUrl ?: "",
                contentDescription = null,
                modifier = Modifier.size(300.dp, 550.dp)
            )
            Button(
                onClick = {
                    emoticonSheetState.value = EmoticonSheetState.DOWNLOADING
                },
                modifier = Modifier.padding(
                    top = dimensionResource(R.dimen.margin_default),
                    bottom = dimensionResource(R.dimen.margin_default)
                )
            ) {
                Text(text = stringResource(R.string.bottomsheet_download))
            }
        }
    }

    @Composable
    private fun EmoticonDownloadingContent(
        appThemeState: AppThemeState,
        emoticon: Result?,
        coroutineScope: CoroutineScope,
        emoticonSheetState: MutableState<EmoticonSheetState>
    ) {
        val animationSpec = remember { LottieAnimationSpec.RawRes(R.raw.downloading) }
        val animationState =
            rememberLottieAnimationState(autoPlay = true, repeatCount = Integer.MAX_VALUE)

        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                runCatching {
                    var downloadIndex = 0
                    val downloadPath =
                        "${StorageUtil.sdcard}/KakaoEmoticonParser/${emoticon?.title}"
                    StorageUtil.createFolder(downloadPath)
                    fun download(url: String) {
                        URL(url).openStream().use { input ->
                            FileOutputStream(File("$downloadPath/${++downloadIndex}.png")).use { output ->
                                input.copyTo(output)
                            }
                        }
                    }
                    emoticon?.thumbnailUrls!!.forEach(::download)
                    MediaUtil.scanning(context, downloadPath)
                    emoticonSheetState.value = EmoticonSheetState.DOWNLOADDONE
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.margin_default)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LottieAnimation(
                spec = animationSpec,
                animationState = animationState,
                modifier = Modifier.size(100.dp)
            )
            Text(
                color = appThemeState.parseColor(),
                text = stringResource(R.string.bottomsheet_downloading),
                modifier = Modifier.padding(
                    top = dimensionResource(R.dimen.margin_default),
                    bottom = dimensionResource(R.dimen.margin_default)
                )
            )
        }
    }

    @Composable
    private fun EmoticonDownloadDoneContent(
        appThemeState: AppThemeState,
        coroutineScope: CoroutineScope,
        bottomSheetScaffoldState: BottomSheetScaffoldState,
        emoticonSheetState: MutableState<EmoticonSheetState>
    ) {
        val animationSpec = remember { LottieAnimationSpec.RawRes(R.raw.download_done) }
        val animationState =
            rememberLottieAnimationState(autoPlay = true)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.margin_default)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LottieAnimation(
                spec = animationSpec,
                animationState = animationState,
                modifier = Modifier.size(100.dp)
            )
            Text(
                color = appThemeState.parseColor(),
                text = stringResource(R.string.bottomsheet_download_done),
                modifier = Modifier
                    .clickable {
                        coroutineScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                            emoticonSheetState.value = EmoticonSheetState.DETAIL
                        }
                    }
                    .padding(
                        top = dimensionResource(R.dimen.margin_default),
                        bottom = dimensionResource(R.dimen.margin_default)
                    )
            )
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
            SearchContentState.ERROR -> errorMessage
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
                    .width(width)
                    .height(height)
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

    private fun searchEmoticon(query: String, searchState: MutableState<SearchContentState>) {
        client.create(EmoticonInterface::class.java).run {
            showLoadingDialog() // todo: I can`t use `ComposableFunction` at this scope.
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
                        errorMessage = it.message.toString()
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

    private fun showLoadingDialog() {
        val dialog = AlertDialog.Builder(context)
        dialog.setView(
            LottieAnimationView(context).apply {
                setAnimation(R.raw.loading)
                repeatCount = LottieDrawable.INFINITE
                // todo: setColorFilter
                playAnimation()
            }
        )
        dialog.setCancelable(false)
        alert = dialog.create()
        alert.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        alert.show()
    }

    private fun closeLoadingDialog() {
        alert.cancel()
    }
}
