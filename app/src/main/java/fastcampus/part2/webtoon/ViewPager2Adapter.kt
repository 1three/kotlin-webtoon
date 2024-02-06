package fastcampus.part2.webtoon

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2Adapter(private val mainActivity: MainActivity) :
    FragmentStateAdapter(mainActivity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                // 백수세끼
                return WebViewFragment(
                    position,
                    "https://m.comic.naver.com/webtoon/detail?titleId=733074&no=1&week=mon&listSortOrder=DESC"
                ).apply {
                    listener = mainActivity
                }

            }

            1 -> {
                // 윈드브레이커
                return WebViewFragment(
                    position,
                    "https://m.comic.naver.com/webtoon/detail?titleId=602910&no=1&week=mon&listSortOrder=DESC"
                ).apply {
                    listener = mainActivity
                }
            }

            2 -> {
                // 김부장
                return WebViewFragment(
                    position,
                    "https://m.comic.naver.com/webtoon/detail?titleId=783053&no=1&week=tue&listSortOrder=DESC"
                ).apply {
                    listener = mainActivity
                }
            }

            else -> {
                return WebViewFragment(position, "https://m.comic.naver.com").apply {
                    listener = mainActivity
                }
            }
        }
    }
}