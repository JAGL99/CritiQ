package com.jagl.critiq.core.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.jagl.critiq.core.model.UiMessage
import com.jagl.critiq.core.model.getMessage
import com.jagl.critiq.core.ui.extensions.fullScreen

@Composable
fun NotAvable(
    modifier: Modifier = Modifier,
    text: UiMessage?
) {
    val text = text?.getMessage(LocalContext.current) ?: return
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            fontSize = 24.sp,
            fontFamily = FontFamily.SansSerif,
            text = text
        )
    }

}

@Preview(showBackground = true)
@Composable
fun NotAvablePreview() {
    val text =
        UiMessage.Text("This media is not available.")
    NotAvable(
        modifier = Modifier.fullScreen(),
        text = text
    )
}