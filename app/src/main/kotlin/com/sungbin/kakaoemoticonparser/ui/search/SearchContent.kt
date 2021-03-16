package com.sungbin.kakaoemoticonparser.ui.search

import android.Manifest
import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState
import com.sungbin.kakaoemoticonparser.R
import com.sungbin.kakaoemoticonparser.emoticon.DaggerEmoticonComponent
import com.sungbin.kakaoemoticonparser.emoticon.EmoticonInterface
import com.sungbin.kakaoemoticonparser.theme.typography
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import me.sungbin.androidutils.util.Logger
import me.sungbin.androidutils.util.PermissionUtil
import retrofit2.Retrofit
import javax.inject.Inject

@ExperimentalComposeUiApi
class SearchContent {

    @Inject
    lateinit var client: Retrofit

    init {
        DaggerEmoticonComponent.builder()
            .build()
            .inject(this)
    }

    @Composable
    fun Bind() {
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
                BindHomeContent()
            }
        )
    }

    @ExperimentalComposeUiApi
    @Composable
    private fun BindHomeContent() {
        val context = LocalContext.current
        val keyboardController = LocalSoftwareKeyboardController.current
        var searchText by remember { mutableStateOf(TextFieldValue()) }
        val animationSpec = remember { LottieAnimationSpec.RawRes(R.raw.search) }
        val animationState =
            rememberLottieAnimationState(autoPlay = true, repeatCount = Integer.MAX_VALUE)

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
            modifier = Modifier.padding(dimensionResource(R.dimen.margin_default))
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = searchText,
                label = { Text(text = stringResource(R.string.main_search_emoticon)) },
                onValueChange = { searchText = it },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null,
                        tint = Color.Gray,
                    )
                },
                maxLines = 1,
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        searchEmoticon(searchText.text)
                        keyboardController?.hideSoftwareKeyboard()
                    }
                )
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxHeight()
            ) {
                LottieAnimation(
                    spec = animationSpec,
                    animationState = animationState,
                    modifier = Modifier
                        .height(50.dp)
                        .width(250.dp)
                        .padding(top = dimensionResource(R.dimen.margin_default))
                )
                Text(
                    text = stringResource(R.string.main_search_first),
                    style = typography.body1,
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.margin_twice))
                )
            }
        }
    }

    @Composable
    private fun LoadingDialog(dismissRequest: () -> Unit) {
        AlertDialog(
            text = {
                val animationSpec = remember { LottieAnimationSpec.RawRes(R.raw.load) }
                val animationState =
                    rememberLottieAnimationState(autoPlay = true, repeatCount = Integer.MAX_VALUE)
                LottieAnimation(
                    spec = animationSpec,
                    animationState = animationState,
                    modifier = Modifier
                        .height(75.dp)
                        .width(75.dp)
                )
            },
            buttons = {},
            onDismissRequest = dismissRequest,
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        )
    }

    // @Composable
    private fun searchEmoticon(query: String) {
        client.create(EmoticonInterface::class.java).run {
            /*var showLoadingDialog by remember { mutableStateOf(true) }
            LoadingDialog { showLoadingDialog = false }*/
            Logger.i("start", query)
            getSearchData(query)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        Logger.i("result", response.result?.totalCount)
                    },
                    {
                        Logger.e("error", it)
                    },
                    {
                        // showLoadingDialog = false
                        Logger.i("end")
                    }
                )
        }
    }
}
