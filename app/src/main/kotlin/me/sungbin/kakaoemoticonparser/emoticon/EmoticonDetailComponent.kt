package me.sungbin.kakaoemoticonparser.emoticon

import me.sungbin.kakaoemoticonparser.ui.search.EmoticonContent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [EmoticonModule::class])
interface EmoticonDetailComponent {
    fun inject(emoticonContent: EmoticonContent)
}
