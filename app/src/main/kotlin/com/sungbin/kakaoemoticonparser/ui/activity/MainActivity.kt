package com.sungbin.kakaoemoticonparser.ui.activity

import android.Manifest
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.sungbin.androidutils.extensions.hide
import com.sungbin.androidutils.extensions.hideKeyboard
import com.sungbin.androidutils.extensions.show
import com.sungbin.androidutils.util.Logger
import com.sungbin.androidutils.util.PermissionUtil
import com.sungbin.kakaoemoticonparser.R
import com.sungbin.kakaoemoticonparser.`interface`.EmoticonInterface
import com.sungbin.kakaoemoticonparser.adapter.EmoticonListAdapter
import com.sungbin.kakaoemoticonparser.databinding.ActivityMainBinding
import com.sungbin.kakaoemoticonparser.ui.dialog.EmoticonDetailBottomDialog
import com.sungbin.kakaoemoticonparser.ui.dialog.LoadingDialog
import com.sungbin.kakaoemoticonparser.util.ParseUtil
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Named("search")
    @Inject
    lateinit var client: Retrofit

    private val layout by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val loadingDialog by lazy { LoadingDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.root)

        PermissionUtil.request(
            this,
            getString(R.string.main_need_permission),
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )

        supportActionBar?.setSubtitle(R.string.copyright)

        layout.etSearch.imeOptions = EditorInfo.IME_ACTION_SEARCH
        layout.etSearch.setOnEditorActionListener { _, actionId, _ ->
            layout.etSearch.hideKeyboard()
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    emoticonSearch(layout.etSearch.text.toString())
                    true
                }
                else -> false
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
                            layout.rvEmoticon.show()
                            layout.clEmpty.hide(true)
                            layout.clSearch.hide(true)
                            layout.rvEmoticon.adapter = EmoticonListAdapter(it).apply {
                                setOnItemClickListener { item ->
                                    EmoticonDetailBottomDialog(this@MainActivity, item).show(
                                        supportFragmentManager,
                                        ""
                                    )
                                }
                            }
                        } ?: showSearchNull()
                    }, { throwable ->
                        loadingDialog.setError(throwable)
                    }, {
                        loadingDialog.close()
                    })
            }
    }

    private fun showSearchNull() {
        layout.clEmpty.show()
        layout.clSearch.hide(true)
        layout.rvEmoticon.hide(true)
        Logger.w("null value")
    }

}