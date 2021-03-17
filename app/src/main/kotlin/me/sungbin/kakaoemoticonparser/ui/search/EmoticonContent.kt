package me.sungbin.kakaoemoticonparser.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Fullscreen
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.glide.GlideImage
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import me.sungbin.androidutils.util.Logger
import me.sungbin.kakaoemoticonparser.R
import me.sungbin.kakaoemoticonparser.emoticon.DaggerEmoticonDetailComponent
import me.sungbin.kakaoemoticonparser.emoticon.EmoticonInterface
import me.sungbin.kakaoemoticonparser.emoticon.model.ContentItem
import me.sungbin.kakaoemoticonparser.emoticon.model.detail.Result
import me.sungbin.kakaoemoticonparser.theme.shapes
import me.sungbin.kakaoemoticonparser.theme.typography
import retrofit2.Retrofit

class EmoticonContent {

    @Inject
    lateinit var client: Retrofit

    init {
        DaggerEmoticonDetailComponent.builder()
            .build()
            .inject(this)
    }

    private var listener: OnEmoticonClickListener? = null
    interface OnEmoticonClickListener {
        fun onEmoticonClicked(emoticon: Result)
    }

    fun setOnEmoticonClickListener(action: Result.() -> Unit) {
        listener = object : OnEmoticonClickListener {
            override fun onEmoticonClicked(emoticon: Result) {
                action(emoticon)
            }
        }
    }

    @Composable
    fun Bind(emoticon: ContentItem) {
        Card(
            shape = shapes.medium,
            modifier = Modifier
                .clickable {
                    // todo: I can`t use `ComposableFunction` at this scope.
                    client
                        .create(EmoticonInterface::class.java)
                        .run {
                            Logger.i("start")
                            getDetailData(emoticon.titleUrl)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                    { response ->
                                        listener?.onEmoticonClicked(response.result)
                                        Logger.i("result: ", response.result.giftImageUrl)
                                    },
                                    {
                                        Logger.e("error", it)
                                    },
                                    {
                                        Logger.i("end")
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
                    fadeIn = true,
                    modifier = Modifier.size(75.dp)
                )
                Column {
                    Column(
                        // todo: Should I use `Column` for Centering?
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
                        Icon(
                            imageVector = Icons.Outlined.MusicNote,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                            }
                        )
                        Icon(
                            imageVector = Icons.Outlined.Fullscreen,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = dimensionResource(R.dimen.margin_half))
                                .clickable {
                                }
                        )
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = dimensionResource(R.dimen.margin_half))
                                .clickable {
                                }
                        )
                    }
                }
            }
        }
    }
}
