package fastcampus.part2.webtoon

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.tabs.TabLayoutMediator
import fastcampus.part2.webtoon.databinding.ActivityMainBinding

/**
 * 웹툰 앱 : Web Browser 통해 웹툰 가져오는 어플리케이션
 *
 * 1) ViewPage2를 통해 n개 Fragment 구성
 * 2) 각 Fragment : WebView 전체 화면 구성
 * 3) TableLayout - ViewPager2 연동
 * 4) Tab name 동적 변경 가능
 * 5) 웹툰의 마지막 조회 시점 : 로컬 저장 및 앱 실행 시 불러오기
 * */

/**
 * WebView
 * ViewPager2
 * Fragment
 * SharedPreference
 * Dialog
 * */

class MainActivity : AppCompatActivity(), WebViewFragment.OnTabLayoutNameChanged {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tab 이름 설정
        val sharedPreferences = getSharedPreferences(WebViewFragment.Companion.SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val tab0 = sharedPreferences?.getString("tab0_name", "백수세끼")
        val tab1 = sharedPreferences?.getString("tab1_name", "윈드브레이커")
        val tab2 = sharedPreferences?.getString("tab2_name", "김부장")

        // ViewPager2 : RecyclerView로 구성
        binding.viewPager2.adapter = ViewPager2Adapter(this)

        // TabLayout
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            run {
                tab.text = when(position) {
                    0 -> tab0
                    1 -> tab1
                    2 -> tab2
                    else -> sharedPreferences?.getString("tab_name", "네이버 웹툰")
                }
            }
        }.attach()
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.fragments[binding.viewPager2.currentItem]
        if (currentFragment is WebViewFragment && currentFragment.canGoBack()) { // WebView + 뒤로가기 페이지 존재할 경우
            currentFragment.goBack()
        } else {
            Toast.makeText(this, "앱을 종료합니다.", Toast.LENGTH_SHORT).show()
            super.onBackPressed()
        }
    }

    override fun nameChanged(position: Int, name: String) {
        val tab = binding.tabLayout.getTabAt(position)
        tab?.text = name
    }
}