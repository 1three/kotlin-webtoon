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
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tab 이름 설정
        val sharedPreferences =
            getSharedPreferences(WebViewFragment.Companion.SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val tabNames = listOf("tab0_name", "tab1_name", "tab2_name")
        val defaultNames = listOf("백수세끼", "윈드브레이커", "김부장")

        // ViewPager2와 TabLayout
        binding.viewPager2.adapter = ViewPager2Adapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            val name = sharedPreferences.getString(tabNames[position], defaultNames[position])
            tab.text = name
        }.attach()
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.fragments[binding.viewPager2.currentItem]

        if (currentFragment is WebViewFragment && currentFragment.canGoBack()) {
            this.doubleBackToExitPressedOnce = false
            currentFragment.goBack()
        } else if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        } else {
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "뒤로가기를 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun nameChanged(position: Int, name: String) {
        val tab = binding.tabLayout.getTabAt(position)
        tab?.text = name
    }
}