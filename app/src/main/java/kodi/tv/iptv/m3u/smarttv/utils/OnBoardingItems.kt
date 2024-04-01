package kodi.tv.iptv.m3u.smarttv.utils

import kodi.tv.iptv.m3u.smarttv.R

class OnBoardingItems(
    val image: Int,
    val title: Int,
    val desc: Int
) {
    companion object {
        fun getData(): List<OnBoardingItems> {
            return listOf(
                OnBoardingItems(
                    R.drawable.in1,
                    R.string.onBoardingTitle1,
                    R.string.onBoardingText1
                ),                OnBoardingItems(
                    R.drawable.in2,
                    R.string.onBoardingTitle2,
                    R.string.onBoardingText2
                ),
                OnBoardingItems(
                    R.drawable.in3,
                    R.string.onBoardingTitle3,
                    R.string.onBoardingText3
                )
            )
        }
    }
}
