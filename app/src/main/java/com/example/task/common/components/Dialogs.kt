package com.example.task.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.task.R
import com.example.task.common.utils.TestTags.LOADING_DIALOG
import com.example.task.common.utils.TestTags.RETRY_BUTTON
import com.example.task.ui.theme.colorRed


@Composable
fun LoadingDialog(isDialogOpen: Boolean = true) {
    if (isDialogOpen) {
        Dialog(
            onDismissRequest = {},
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
            modifier = Modifier.testTag(LOADING_DIALOG),
            color = colorRed,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingDialogPreview() {
    LoadingDialog(true)
}

@Composable
fun ErrorDialog(
    errorMessage: String,
    onRetryClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = errorMessage,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.size(10.dp))
                Button(
                    onClick = {
                        onRetryClick()
                        onDismiss()
                    },
                    modifier = Modifier.testTag(RETRY_BUTTON),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = stringResource(R.string.retry))
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ErrorDialogPreview() {
    ErrorDialog("error", onRetryClick = { }, onDismiss = { })
}