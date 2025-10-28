package com.demo.presentation.widget.main.mediaContent

import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import com.demo.presentation.widget.main.loader.ContentLoader

@Composable
fun MjpgStreamContent(
    url: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    var isLoading by remember { mutableStateOf(true) }

    AndroidView(
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                settings.javaScriptEnabled = false
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.cacheMode = WebSettings.LOAD_NO_CACHE

                webChromeClient = object : WebChromeClient() {
                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        super.onProgressChanged(view, newProgress)
                        if (newProgress >= 20) isLoading = false
                    }
                }

                webViewClient = object : WebViewClient() {
                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        super.onReceivedError(view, request, error)
                        isLoading = false
                    }
                }

                loadUrl(url)
            }
        },
        modifier = modifier.fillMaxSize(),
        onReset = { view ->
            view.destroy()
        },
        onRelease = { view ->
            view.destroy()
        }
    )

    if (isLoading) {
        ContentLoader(
            modifier = modifier.fillMaxSize()
        )
    }

    onClick?.let {
        Box(
            modifier = modifier
                .fillMaxSize()
                .zIndex(1f)
                .background(Color.Transparent)
                .pointerInput(Unit) {
                    detectTapGestures {
                        onClick()
                    }
                }
        )
    }
}