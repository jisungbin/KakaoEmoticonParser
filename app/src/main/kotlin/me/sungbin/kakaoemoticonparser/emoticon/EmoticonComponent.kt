package me.sungbin.kakaoemoticonparser.emoticon

import androidx.compose.ui.ExperimentalComposeUiApi
import me.sungbin.kakaoemoticonparser.ui.search.SearchContent
import dagger.Component
import javax.inject.Singleton

@Singleton
@ExperimentalComposeUiApi
@Component(modules = [EmoticonModule::class])
interface EmoticonComponent {
    fun inject(searchContent: SearchContent)
}
