package com.sungbin.kakaoemoticonparser.ui.activity

import android.Manifest
import android.app.Service
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.sungbin.kakaoemoticonparser.R
import com.sungbin.kakaoemoticonparser.`interface`.EmoticonInterface
import com.sungbin.kakaoemoticonparser.adapter.EmoticonAdapter
import com.sungbin.kakaoemoticonparser.ui.dialog.ProgressDialog
import com.sungbin.kakaoemoticonparser.utils.ParseUtils
import com.sungbin.sungbintool.LogUtils
import com.sungbin.sungbintool.PermissionUtils
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var client: Retrofit

    private val imm by lazy {
        applicationContext.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private val loadingDialog by lazy {
        ProgressDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setSubtitle(R.string.copyright)

        PermissionUtils.request(this, null, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE))

        et_search.imeOptions = EditorInfo.IME_ACTION_SEARCH
        et_search.setOnEditorActionListener { _, actionId, _ ->
            imm.hideSoftInputFromWindow(
                et_search.windowToken,
                InputMethodManager.RESULT_UNCHANGED_SHOWN
            )
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    emoticonSearch(et_search.text.toString())
                    return@setOnEditorActionListener true
                }
                else -> {
                    return@setOnEditorActionListener false
                }
            }
        }
    }

    private fun emoticonSearch(query: String) {
        client
            .create(EmoticonInterface::class.java).run {
                loadingDialog.show()
                getSearchData(query)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        val content = response.string()
                        ParseUtils.getSearchedData(content)?.let {
                            rv_emoticon.adapter = EmoticonAdapter(it)
                            LogUtils.w(it.random().url)
                        } ?: showSearchNull()
                    }, { throwable ->
                        throwable.printStackTrace()
                        loadingDialog.setError(throwable)
                    }, {
                        loadingDialog.close()
                    })
            }
    }

    private fun showSearchNull(){}

}