package com.demo.presentation.widget.main.mediaContent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.demo.presentation.widget.main.loader.ContentLoader
import com.demo.presentation.widget.main.mediaContent.data.VideoPlayerState
import org.videolan.libvlc.util.VLCVideoLayout

@Composable
fun LiveStreamContent(
    state: VideoPlayerState,
    modifier: Modifier = Modifier,
    onAttachView: (VLCVideoLayout) -> Unit,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onDispose: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {

                Lifecycle.Event.ON_RESUME -> {
                    onPlay()
                }

                Lifecycle.Event.ON_PAUSE -> {
                    onPause()
                }

                Lifecycle.Event.ON_DESTROY -> {
                    onDispose()
                }

                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    AndroidView(
        factory = {
            VLCVideoLayout(context).apply {
                keepScreenOn = true
                onAttachView(this)
            }
        },
        modifier = modifier
    )

    if (state.showContentLoader) {
        ContentLoader(modifier = modifier)
    }
}