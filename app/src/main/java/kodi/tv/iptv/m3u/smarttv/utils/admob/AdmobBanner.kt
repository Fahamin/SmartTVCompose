package kodi.tv.iptv.m3u.smarttv.utils.admob

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.runtime.*
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView
import kodi.tv.iptv.m3u.smarttv.utils.Fun.Companion.sc
import kodi.tv.iptv.m3u.smarttv.R
import kodi.tv.iptv.m3u.smarttv.BuildConfig

import kodi.tv.iptv.m3u.smarttv.utils.Prefs

private var adView: com.facebook.ads.AdView? = null


@Composable
fun AdmobBanner(modifier: Modifier = Modifier) {
    var shouldShowResult = remember {
        mutableStateOf(false)
    }
    var pref = Prefs(LocalContext.current)

    Column {
        if (pref.isRemoveAd || sc == "0") {
        } else {
            AndroidView(modifier = modifier.fillMaxWidth(), factory = { context ->
                AdView(context).apply {
                    setAdSize(AdSize.BANNER)
                    adUnitId = BuildConfig.BANNER_ADD_ID
                    loadAd(AdRequest.Builder().build())

                }
            },
                update = {
                    it.apply {
                        loadAd(AdRequest.Builder().build())
                    }
                    it.adListener = object : AdListener() {
                        override fun onAdClicked() {

                        }

                        override fun onAdClosed() {

                        }


                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            shouldShowResult.value = true
                            //FacebookBannerAdsView(BuildConfig.FB_BANNER_ADD_ID)
                        }

                        override fun onAdImpression() {

                        }

                        override fun onAdLoaded() {

                        }

                        override fun onAdOpened() {

                        }
                    }
                })
        }

        // Spacer(modifier = Modifier.height(16.dp))
    }
    if (shouldShowResult.value) {
        FacebookBannerAdsView(BuildConfig.FB_BANNER_ADD_ID)
    }
}

@Composable
fun AdmobBannerAdaptive(modifier: Modifier = Modifier) {
    var shouldShowResult = remember {
        mutableStateOf(false)
    }
    var pref = Prefs(LocalContext.current)

    Column {

        if (pref.isRemoveAd || sc == "0") {
        } else {
            AndroidView(modifier = modifier.fillMaxWidth(), factory = { context ->
                AdView(context).apply {
                    var adSize =
                        AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(context, 320)

                    adUnitId = BuildConfig.BANNER_ADD_ID
                    setAdSize(adSize)
                    loadAd(AdRequest.Builder().build())
                    val adRequest = AdRequest.Builder().build()
                    loadAd(adRequest)
                }

            },
                update = {

                    it.adListener = object : AdListener() {
                        override fun onAdClicked() {}

                        override fun onAdClosed() {}

                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            shouldShowResult.value = true
                        }

                        override fun onAdImpression() {}

                        override fun onAdLoaded() {}

                        override fun onAdOpened() {}
                    }
                })
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (shouldShowResult.value) {
            FacebookBannerAdsView(BuildConfig.FB_BANNER_ADD_ID)
        }
    }


// Step 3: Load an ad.


}

@Composable
fun FacebookBannerAdsView(bannerId: String) {

    var pref = Prefs(LocalContext.current)
    if (pref.isRemoveAd) {

    } else {
        AndroidView(
            factory = { context ->
                // Create and configure your Android View here
                val view = LayoutInflater.from(context).inflate(R.layout.facebook_ads, null, false)
                val bannerContainer = view.findViewById<LinearLayout>(R.id.banner_container)
                adView =
                    com.facebook.ads.AdView(
                        context,
                        bannerId,
                        com.facebook.ads.AdSize.BANNER_HEIGHT_50
                    )
                bannerContainer.addView(adView)
                // Request an ad
                adView!!.loadAd()
                // do whatever you want...
                view
            },
            update = { view ->
                // Update the Android View if needed
            }
        )
    }

}


@Composable
fun NativeAdViewMedium(nativeAd: NativeAd?) {
    if (nativeAd != null) {
        AndroidView(
            factory = { context ->
                val adView = LayoutInflater.from(context)
                    .inflate(R.layout.native_ad_binding, null) as NativeAdView
                populateNativeAdView(nativeAd, adView)
                adView
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(180.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun NativeAdViewSmall(nativeAd: NativeAd?) {
    if (nativeAd != null) {
        AndroidView(
            factory = { context ->
                val adView = LayoutInflater.from(context)
                    .inflate(R.layout.native_ad_small, null) as NativeAdView
                populateNativeAdView(nativeAd, adView)
                adView
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(180.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

private fun populateNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {
    // Set the media view
    adView.mediaView = adView.findViewById(R.id.media_view)
    // Set other ad assets
    adView.headlineView = adView.findViewById(R.id.primary)
    adView.bodyView = adView.findViewById(R.id.body)
    adView.callToActionView = adView.findViewById(R.id.cta)
    adView.iconView = adView.findViewById(R.id.icon)
    adView.starRatingView = adView.findViewById(R.id.rating_bar)

    adView.iconView?.let {
        if (nativeAd.icon == null) {
            it.visibility = View.INVISIBLE
        } else {
            it.visibility = View.VISIBLE
            (it as ImageView).setImageDrawable(nativeAd.icon!!.drawable)
        }
    }

    adView.headlineView?.let {
        if (nativeAd.headline == null) {
            it.visibility = View.INVISIBLE
        } else {
            it.visibility = View.VISIBLE
            (it as TextView).text = nativeAd.headline
        }
    }

    adView.starRatingView?.let {
        if (nativeAd.starRating == null) {
            it.visibility = View.INVISIBLE
        } else {
            it.visibility = View.VISIBLE
            (it as RatingBar).rating = nativeAd.starRating!!.toFloat()
        }
    }

    // The headline is guaranteed to be in every NativeAd.
    adView.headlineView?.let {
        if (nativeAd.headline == null) {
            it.visibility = View.INVISIBLE
        } else {
            it.visibility = View.VISIBLE
            (it as TextView).text = nativeAd.headline
        }
    }
    // These assets aren't guaranteed to be in every NativeAd, so it's important to
    // check before trying to display them.
    adView.bodyView?.let {
        if (nativeAd.body == null) {
            it.visibility = View.INVISIBLE
        } else {
            it.visibility = View.VISIBLE
            (it as TextView).text = nativeAd.body
        }
    }

    adView.callToActionView?.let {
        if (nativeAd.callToAction == null) {
            it.visibility = View.INVISIBLE
        } else {
            it.visibility = View.VISIBLE
            (it as Button).text = nativeAd.callToAction
        }
    }

    // This method tells the Google Mobile Ads SDK that you have finished populating your
    // native ad view with this native ad.
    adView.setNativeAd(nativeAd)
}

@Composable
fun LoadNativeAd(context: Context): NativeAd? {
    val nativeAd = remember { mutableStateOf<NativeAd?>(null) }

    val adLoader = AdLoader.Builder(context, BuildConfig.ADMOB_NATIVE_ID)  // Test ad unit ID
        .forNativeAd { ad: NativeAd ->
            nativeAd.value = ad
        }
        .withAdListener(object : com.google.android.gms.ads.AdListener() {
            override fun onAdFailedToLoad(adError: com.google.android.gms.ads.LoadAdError) {
                // Handle the error
            }
        })
        .withNativeAdOptions(NativeAdOptions.Builder().build())
        .build()

    adLoader.loadAd(AdRequest.Builder().build())


    return nativeAd.value
}


/*@Composable
fun NativeAdView(nativeAd: NativeAd?) {
    val color = MaterialTheme.colorScheme.tertiary

    if (nativeAd != null) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation()
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = nativeAd.headline ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = color
                )
                Text(
                    text = nativeAd.body ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = color
                )

                if (nativeAd.images.isNotEmpty()) {
                    val image = nativeAd.images[0].drawable
                    Image(
                        bitmap = image!!.toBitmap().asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                nativeAd.callToAction?.let {
                    Spacer(modifier = Modifier.height(2.dp))
                    Button(onClick = {
                        nativeAd.performClick()
                    }) {
                        Text(text = it, color = color)
                    }
                }
            }
        }
    } else {
        // Placeholder when ad is not available
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(180.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}*/

