package fastcampus.part2.webtoon

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2Adapter(private val mainActivity: MainActivity) :
    FragmentStateAdapter(mainActivity) {

    private val sharedPreferences =
        mainActivity.getSharedPreferences(WebViewFragment.SHARED_PREFERENCE, Context.MODE_PRIVATE)

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        val urlKey = "tab$position"
        val defaultUrls = listOf(
            "https://m.comic.naver.com/webtoon/detail?titleId=733074&no=1&week=mon&listSortOrder=DESC",
            "https://m.comic.naver.com/webtoon/detail?titleId=602910&no=1&week=mon&listSortOrder=DESC",
            "https://m.comic.naver.com/webtoon/detail?titleId=783053&no=1&week=tue&listSortOrder=DESC",
        )
        val url = sharedPreferences.getString(urlKey, defaultUrls[position])

        return WebViewFragment(position, url!!).apply {
            listener = mainActivity
        }
    }
}