package com.demo.presentation.widget.admob

import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@RequiresPermission("android.permission.INTERNET")
@Composable
fun AdMobBanner(
    modifier: Modifier = Modifier,
    adSize: AdSize = AdSize.MEDIUM_RECTANGLE,
    adId: String = "", // Replace with your actual Ad Unit ID
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            AdView(context).apply {
                setAdSize(adSize)
                adUnitId = adId
                loadAd(AdRequest.Builder().build())
            }
        }
    )

}