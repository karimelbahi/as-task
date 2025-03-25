package com.example.task.presentation.ui.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.task.common.components.MyImageWithCoil
import com.example.task.data.api.model.Cat

@Composable
fun CatComponent(cat: Cat) {

    Row(
        Modifier
            .fillMaxWidth()
            .background(Color(0xfff8f8f8), shape = RoundedCornerShape(16.dp))
            .border(
                border = BorderStroke(2.dp, Color(0xffdfdfdf)),
                shape = RoundedCornerShape(16.dp)
            ).testTag("cat_item")
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MyImageWithCoil(
            modifier = Modifier
                .fillMaxWidth()
                .height(116.dp)
                .padding(4.dp)
                .clip(shape = RoundedCornerShape(14.dp))
                .testTag("cat_image_${cat.id}"),
            imageUrl = cat.url
        )

    }
}

@Preview
@Composable
private fun CatComponentPreview() {
    CatComponent(
        Cat(
            height = null, id = null, url = null, width = null
        )
    )
}