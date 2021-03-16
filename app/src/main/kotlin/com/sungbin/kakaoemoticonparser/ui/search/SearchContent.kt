package com.sungbin.kakaoemoticonparser.ui.search

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState
import com.sungbin.kakaoemoticonparser.R
import com.sungbin.kakaoemoticonparser.emoticon.DaggerEmoticonComponent
import com.sungbin.kakaoemoticonparser.emoticon.EmoticonInterface
import com.sungbin.kakaoemoticonparser.theme.typography
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import me.sungbin.androidutils.extensions.doDelay
import me.sungbin.androidutils.util.Logger
import me.sungbin.androidutils.util.PermissionUtil
import retrofit2.Retrofit

@ExperimentalComposeUiApi
class SearchContent {

    @Inject
    lateinit var client: Retrofit

    lateinit var context: Context
    lateinit var alert: AlertDialog

    init {
        DaggerEmoticonComponent.builder()
            .build()
            .inject(this)
    }

    @Composable
    fun Bind() {
        context = LocalContext.current
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
                keyboardActions = KeyboardActions {
                    searchEmoticon(searchText.text)
                    keyboardController?.hideSoftwareKeyboard()
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
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
                        .size(250.dp, 50.dp)
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

    private fun searchEmoticon(query: String) {
        client.create(EmoticonInterface::class.java).run {
            showLoadingDialog()
            doDelay(1000) {
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
                            closeLoadingDialog()
                        }
                    )
            }
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
        alert = dialog.create()
        alert.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        alert.show()
    }

    private fun closeLoadingDialog() {
        alert.cancel()
    }
}
