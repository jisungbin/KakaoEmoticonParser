package me.sungbin.kakaoemoticonparser.emoticon

import androidx.compose.material.ExperimentalMaterialApi
import dagger.Component
import javax.inject.Singleton
import me.sungbin.kakaoemoticonparser.ui.emoticon.EmoticonContent

@ExperimentalMaterialApi
@Singleton
@Component(modules = [EmoticonModule::class])
interface EmoticonDetailComponent {
    fun inject(emoticonContent: EmoticonContent)
}
