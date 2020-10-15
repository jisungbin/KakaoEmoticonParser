package com.sungbin.kakaoemoticonparser.ui.activity

import android.Manifest
import android.app.Service
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.sungbin.kakaoemoticonparser.R
import com.sungbin.kakaoemoticonparser.`interface`.EmoticonInterface
import com.sungbin.kakaoemoticonparser.adapter.EmoticonListAdapter
import com.sungbin.kakaoemoticonparser.ui.dialog.EmoticonDetailBottomDialog
import com.sungbin.kakaoemoticonparser.ui.dialog.LoadingDialog
import com.sungbin.kakaoemoticonparser.util.ParseUtil
import com.sungbin.sungbintool.LogUtils
import com.sungbin.sungbintool.PermissionUtils
import com.sungbin.sungbintool.extensions.hide
import com.sungbin.sungbintool.extensions.show
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Named("SEARCH")
    @Inject
    lateinit var client: Retrofit

    private val imm by lazy {
        applicationContext.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private val loadingDialog by lazy {
        LoadingDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PermissionUtils.request(
            this,
            "이모티콘 다운로드 요청 시 내부메모리에 접근 권한이 필요합니다.",
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )

        supportActionBar?.setSubtitle(R.string.copyright)

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
                        ParseUtil.getSearchedData(response.string())?.let {
                            rv_emoticon.show()
                            cl_empty.hide(true)
                            cl_search.hide(true)
                            rv_emoticon.adapter = EmoticonListAdapter(it).apply {
                                setOnItemClickListener { item ->
                                    EmoticonDetailBottomDialog(this@MainActivity, item).show(
                                        supportFragmentManager,
                                        ""
                                    )
                                }
                            }
                        } ?: showSearchNull()
                    }, { throwable ->
                        throwable.printStackTrace()
                        loadingDialog.setError(throwable)
                    }, {
                        loadingDialog.close()
                    })
            }
    }

    private fun showSearchNull() {
        cl_empty.show()
        cl_search.hide(true)
        rv_emoticon.hide(true)
        LogUtils.w("null value")
    }

}