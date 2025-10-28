package com.demo.presentation.widget.liveMap.map

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.demo.domain.entity.common.Coordinates

@SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
@Composable
fun LiveMap(
    liveMapUrl: String,
    isInteractive: Boolean,
    coordinates: Coordinates?,
    modifier: Modifier = Modifier,
) {
    AndroidView(
        modifier = modifier,
        factory = {
            WebView(it).apply {
                if (!isInteractive) {
                    isClickable = false
                    isFocusable = false
                    setOnTouchListener { _, _ -> true }
                }
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webChromeClient = WebChromeClient()
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        Log.d("LiveMap", "WebView page finished loading: $url")
                    }
                }
                settings.javaScriptEnabled = true
                loadUrl(liveMapUrl)
            }
        },
        update = { view ->
//            coordinates?.let {
//                val url = "javascript:liveMap.updateLocation(${coordinates.latitude}, ${coordinates.longitude});"
//                Log.d("LiveMap", "Update WebView page with coordinates: $coordinates \n $url")
//                view.loadUrl(url)
//            }
        },
        onReset = { view ->
            view.destroy()
        },
        onRelease = { view ->
            view.destroy()
        }
    )
}

@Preview
@Composable
fun LiveMapPreview() {
    LiveMap(liveMapUrl = "", coordinates = null, isInteractive = false)
}