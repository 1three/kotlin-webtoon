package fastcampus.part2.webtoon

import android.graphics.Bitmap
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar

// WebViewClient : (로딩 중, 로딩 완료 등) 상태 관리

class WebtoonWebViewClient(
    private val progressBar: ProgressBar,
    private val saveData: (String) -> Unit // () -> Unit : 함수를 인자로 받을 경우
) : WebViewClient() {

    // 웹페이지 로딩 진행 시 호출
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        progressBar.visibility = View.VISIBLE
    }

    // 웹페이지 로딩 완료 시 호출
    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        progressBar.visibility = View.GONE
    }

    // URL 로드 전 호출
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        // WEBTOON detail 범위 제한
        if (request != null && request.url.toString().contains("comic.naver.com/webtoon/detail")) {
            saveData(request.url.toString())
        }

        return super.shouldOverrideUrlLoading(view, request)
    }
}