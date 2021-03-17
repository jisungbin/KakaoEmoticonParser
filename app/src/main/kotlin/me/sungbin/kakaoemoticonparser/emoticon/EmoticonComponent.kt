package me.sungbin.kakaoemoticonparser.emoticon

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import dagger.Component
import me.sungbin.kakaoemoticonparser.ui.search.SearchContent
import javax.inject.Singleton

@Singleton
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Component(modules = [EmoticonModule::class])
interface EmoticonComponent {
    fun inject(searchContent: SearchContent)
}
