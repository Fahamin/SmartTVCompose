package kodi.tv.iptv.m3u.smarttv

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TvApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}