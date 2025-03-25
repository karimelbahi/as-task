package com.example.task.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.task.R
import com.example.task.common.utils.convertNullToEmpty
import com.example.task.ui.theme.colorNotActiveButtonBg
import com.example.task.ui.theme.colorNotActiveButtonTxt
import com.example.task.ui.theme.colorYellow

const val LOADING_DIALOG_TAG = "LoadingIndicator"

@Composable
fun LoadingDialog(isDialogOpen: Boolean = true) {
    if (isDialogOpen) {
        Dialog(
            onDismissRequest = { /* Dismiss the dialog when back button is pressed or tapped outside */ },
        ) {
            DefaultLoading()
        }
    }
}

@Composable
fun DefaultLoading() {
    Box(
        modifier = Modifier.size(50.dp),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.testTag(LOADING_DIALOG_TAG),
            color = colorYellow,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingDialogPreview() {
    LoadingDialog(true)
}

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
fun MyWrapShapeWithContent(
    modifier: Modifier = Modifier,
    txt: String,
    isActive: Boolean = true,
    onClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .clip(shape = RoundedCornerShape(24.dp))
                .background(color = if (isActive) Color(0xff70AB99) else colorNotActiveButtonBg)
                .clickable { onClick() }
                .padding(vertical = 2.dp, horizontal = 8.dp),
    ) {
        Text(
            text = txt,
            color = if (isActive) Color.White else colorNotActiveButtonTxt,
            textAlign = TextAlign.Center,
            lineHeight = 1.5.em,
            style =
                TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                ),
            modifier =
                Modifier
                    .wrapContentHeight(align = Alignment.CenterVertically),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyWrapShapeWithContentPreview() {
    MyWrapShapeWithContent(onClick = { }, txt = "text")
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
