package me.sungbin.kakaoemoticonparser.ui.emoticon

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Fullscreen
import androidx.compose.material.icons.outlined.MusicNote
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.sungbin.androidutils.util.MediaUtil
import me.sungbin.androidutils.util.StorageUtil
import me.sungbin.kakaoemoticonparser.R
import me.sungbin.kakaoemoticonparser.emoticon.DaggerEmoticonDetailComponent
import me.sungbin.kakaoemoticonparser.emoticon.EmoticonInterface
import me.sungbin.kakaoemoticonparser.emoticon.model.ContentItem
import me.sungbin.kakaoemoticonparser.emoticon.model.detail.Result
import me.sungbin.kakaoemoticonparser.emoticon.room.EmoticonDatabase
import me.sungbin.kakaoemoticonparser.emoticon.room.EmoticonEntity
import me.sungbin.kakaoemoticonparser.theme.AppThemeState
import me.sungbin.kakaoemoticonparser.theme.shapes
import me.sungbin.kakaoemoticonparser.theme.typography
import me.sungbin.kakaoemoticonparser.ui.dialog.closeLoadingDialog
import me.sungbin.kakaoemoticonparser.ui.dialog.showLoadingDialog
import me.sungbin.kakaoemoticonparser.ui.search.SearchContentState
import me.sungbin.kakaoemoticonparser.util.parseColor
import retrofit2.Retrofit

@ExperimentalMaterialApi
class EmoticonContent {

    @Inject
    lateinit var client: Retrofit

    init {
        DaggerEmoticonDetailComponent.builder()
            .build()
            .inject(this)
    }

    @Composable
    fun BindListContent(
        emoticons: List<ContentItem>,
        appThemeState: AppThemeState,
        searchState: MutableState<SearchContentState>,
        errorMessage: MutableState<String>
    ) {
        val sheetType = remember { mutableStateOf(EmoticonSheetState.DETAIL) }
        val clickedEmoticon = remember { mutableStateOf<Result?>(null) }
        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
        )

        BottomSheetScaffold(
            sheetShape = shapes.large,
            sheetElevation = dimensionResource(R.dimen.margin_twice_half),
            scaffoldState = bottomSheetScaffoldState,
            sheetPeekHeight = 0.dp,
            sheetContent = {
                Crossfade(sheetType.value) { type ->
                    when (type) {
                        EmoticonSheetState.DETAIL -> EmoticonDetailContent(
                            emoticon = clickedEmoticon.value,
                            emoticonSheetState = sheetType
                        )
                        EmoticonSheetState.DOWNLOADING -> EmoticonDownloadingContent(
                            appThemeState = appThemeState,
                            emoticon = clickedEmoticon.value,
                            emoticonSheetState = sheetType
                        )
                        EmoticonSheetState.DOWNLOADDONE -> EmoticonDownloadDoneContent(
                            appThemeState = appThemeState,
                            bottomSheetScaffoldState = bottomSheetScaffoldState,
                            emoticonSheetState = sheetType
                        )
                    }
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
                    items = emoticons,
                    itemContent = { emoticon ->
                        Box(
                            Modifier.padding(
                                start = dimensionResource(R.dimen.margin_default),
                                end = dimensionResource(R.dimen.margin_default),
                                bottom = dimensionResource(R.dimen.margin_half),
                                top = dimensionResource(R.dimen.margin_half)
                            )
                        ) {
                            BindEmoticonPanel(
                                searchState,
                                errorMessage,
                                emoticon,
                                bottomSheetScaffoldState,
                                clickedEmoticon
                            )
                        }
                    }
                )
            }
        }
    }

    @Composable
    private fun BindEmoticonPanel(
        searchState: MutableState<SearchContentState>,
        errorMessage: MutableState<String>,
        emoticon: ContentItem,
        bottomSheetScaffoldState: BottomSheetScaffoldState,
        clickedEmoticon: MutableState<Result?>
    ) {
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        val emoticonDatabase = remember { EmoticonDatabase.instance(context).dao() }
        Card(
            shape = shapes.medium,
            modifier = Modifier
                .clickable {
                    client
                        .create(EmoticonInterface::class.java)
                        .run {
                            showLoadingDialog(context)
                            getDetailData(emoticon.titleUrl)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                    { response ->
                                        clickedEmoticon.value = response.result
                                        coroutineScope.launch {
                                            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                                bottomSheetScaffoldState.bottomSheetState.expand()
                                            }
                                        }
                                    },
                                    {
                                        searchState.value = SearchContentState.ERROR
                                        errorMessage.value = it.message.toString()
                                        closeLoadingDialog()
                                    },
                                    {
                                        closeLoadingDialog()
                                    }
                                )
                        }
                }
                .fillMaxWidth()
                .height(100.dp),
            elevation = dimensionResource(R.dimen.margin_twice_half)
        ) {
            Row(modifier = Modifier.padding(8.dp)) {
                GlideImage(
                    data = emoticon.titleImageUrl,
                    contentDescription = null,
                    modifier = Modifier.size(75.dp)
                )
                Column {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            text = emoticon.title,
                            fontWeight = FontWeight.Bold,
                            style = typography.body1
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        var isFavorite by rememberSaveable { mutableStateOf(false) }
                        coroutineScope.launch {
                            val favoriteEmoticon =
                                emoticonDatabase.getFavoriteEmoticon(emoticon.title)
                            isFavorite = favoriteEmoticon != null
                        }
                        Icon(
                            imageVector = Icons.Outlined.MusicNote,
                            contentDescription = null,
                            tint = if (emoticon.isSound) Color.Black else Color.Gray
                        )
                        Icon(
                            imageVector = Icons.Outlined.Fullscreen,
                            contentDescription = null,
                            modifier = Modifier.padding(start = dimensionResource(R.dimen.margin_half)),
                            tint = if (emoticon.isBigEmo) Color.Black else Color.Gray
                        )
                        Icon(
                            imageVector = if (!isFavorite) Icons.Outlined.FavoriteBorder else Icons.Outlined.Favorite,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = dimensionResource(R.dimen.margin_half))
                                .clickable {
                                    coroutineScope.launch {
                                        val entity = EmoticonEntity(
                                            title = emoticon.title,
                                            imageUrl = emoticon.titleImageUrl,
                                            isBigEmo = emoticon.isBigEmo,
                                            isSound = emoticon.isSound,
                                        )
                                        if (!isFavorite) {
                                            emoticonDatabase.insert(entity)
                                        } else {
                                            emoticonDatabase.delete(entity)
                                        }
                                        isFavorite = !isFavorite
                                    }
                                }
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun EmoticonDetailContent(
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
    fun EmoticonDownloadingContent(
        appThemeState: AppThemeState,
        emoticon: Result?,
        emoticonSheetState: MutableState<EmoticonSheetState>
    ) {
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
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
    fun EmoticonDownloadDoneContent(
        appThemeState: AppThemeState,
        bottomSheetScaffoldState: BottomSheetScaffoldState,
        emoticonSheetState: MutableState<EmoticonSheetState>
    ) {
        val coroutineScope = rememberCoroutineScope()
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
}
