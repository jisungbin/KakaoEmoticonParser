package com.sungbin.kakaoemoticonparser.emoticon

import androidx.compose.ui.ExperimentalComposeUiApi
import com.sungbin.kakaoemoticonparser.ui.search.SearchContent
import dagger.Component
import javax.inject.Singleton

@Singleton
@ExperimentalComposeUiApi
@Component(modules = [EmoticonModule::class])
interface EmoticonComponent {
    fun inject(searchContent: SearchContent)
}
