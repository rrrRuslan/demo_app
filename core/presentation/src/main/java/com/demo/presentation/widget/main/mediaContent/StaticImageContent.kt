package com.demo.presentation.widget.main.mediaContent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Precision
import coil.size.Size
import com.demo.presentation.widget.main.loader.ContentLoader

@Composable
fun StaticImageContent(
    url: String
) {
    var imageState: AsyncImagePainter.State by remember { mutableStateOf(AsyncImagePainter.State.Empty) }
    val model = ImageRequest.Builder(LocalContext.current)
        .data(url)
        .precision(Precision.INEXACT)
        .size(Size.ORIGINAL)
        .crossfade(true)
        .build()

    val painter = rememberAsyncImagePainter(
        model = model,
        onState = { state -> imageState = state }
    )

    when (imageState) {
        is AsyncImagePainter.State.Success -> {
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxSize()
            )
        }

        else -> {
            ContentLoader(
                Modifier.fillMaxSize()
            )
        }
    }
}