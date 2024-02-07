package fastcampus.part2.webtoon

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import fastcampus.part2.webtoon.databinding.FragmentWebViewBinding

/**
 * getSharedPreferences:
 *
 * 1. 용도: 앱 내에서 작은 데이터를 저장 및 불러오기
 *
 * Fragment에서의 사용 방법:
 *
 * 1. `activity`나 `context`를 통해 `Context` 얻기
 * 2. `getSharedPreferences` 메소드 호출 및 SharedPreferences 객체 생성
 * 3. 생성한 SharedPreferences 객체를 통해 데이터를 저장 혹은 불러오기
 *
 * 이를 통해 앱의 사용자 설정, 상태 정보 등 효율적 관리 가능
 * */

class WebViewFragment(private val position: Int, private val webViewUrl: String) : Fragment() {
    private lateinit var binding: FragmentWebViewBinding
    var listener: OnTabLayoutNameChanged? = null

    companion object {
        const val SHARED_PREFERENCE = "WEB_HISTORY"
    }

    // onCreateView : View 생성 시점
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebViewBinding.inflate(inflater)
        return binding.root
    }

    // onViewCreated : View 생성 이후
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // WebViewClient : (로딩 중, 로딩 완료 등) 상태 관리
        binding.webView.webViewClient = WebtoonWebViewClient(binding.progressBar) { url ->
            activity?.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE)?.edit {
                putString("tab$position", url)
                apply()
            }
        }
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(webViewUrl)
        setupUI()
    }

    private fun setupUI() {
        binding.backToLastPointButton.setOnClickListener {
            val sharedPreference =
                activity?.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE)
            val url = sharedPreference?.getString("tab$position", "")

            if (url.isNullOrEmpty()) {
                Toast.makeText(context, "마지막 저장 시점이 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
            } else {
                binding.webView.loadUrl(url)
            }
        }

        binding.changeTabNameButton.setOnClickListener {
            AlertDialog.Builder(context).apply {
                val editText = EditText(context)
                setView(editText)
                setTitle("탭 이름 변경")
                setPositiveButton("변경하기") { _, _ ->
                    activity?.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE)?.edit {
                        putString("tab${position}_name", editText.text.toString())
                        listener?.nameChanged(
                            position,
                            editText.text.toString()
                        ) // 탭 이름 변경 Listener
                    }
                }
                setNegativeButton("취소하기") { dialogInterface, _ ->
                    dialogInterface.cancel()
                }
            }.show()
        }
    }

    fun canGoBack(): Boolean = binding.webView.canGoBack()

    fun goBack() = binding.webView.goBack()


    interface OnTabLayoutNameChanged {
        fun nameChanged(position: Int, name: String)
    }
}