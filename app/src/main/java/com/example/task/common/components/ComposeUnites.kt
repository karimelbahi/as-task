package com.example.task.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.task.R
import com.example.task.common.utils.convertNullToEmpty


@Composable
fun MyImageWithCoil(
    modifier: Modifier,
    imageUrl: String?,
    contentScale: ContentScale = ContentScale.Crop,
) {
    if (imageUrl.convertNullToEmpty().isNotEmpty()) {
        val painter =
            rememberAsyncImagePainter(
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(data = imageUrl.convertNullToEmpty())
                    .apply(block = fun ImageRequest.Builder.() {
                        placeholder(R.drawable.placeholder)
                        error(R.drawable.placeholder)
                    })
                    .build(),
            )
        Image(
            painter = painter,
            contentDescription = "Image content description",
            modifier = modifier,
            contentScale = contentScale,
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.placeholder),
            contentDescription = "Image content description",
            modifier = modifier,
            contentScale = ContentScale.Crop,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyImageWithCoilPreview() {
    MyImageWithCoil(
        modifier =
            Modifier
                .size(width = 80.dp, height = 52.dp),
        imageUrl = "url",
    )
}

@Composable
fun MySpace4() {
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun MySpace8() {
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun MySpace16() {
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun MySpace12() {
    Spacer(modifier = Modifier.height(12.dp))
}
