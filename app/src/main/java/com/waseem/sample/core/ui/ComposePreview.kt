package com.waseem.sample.core.ui

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.waseem.sample.core.ui.theme.SampleTheme


@Composable
internal fun ThemedPreview(
    darkTheme: Boolean = false,
    uiMode: UiMode = UiMode.PhonePortrait,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalUiMode provides uiMode) {
        SampleTheme(darkTheme = darkTheme) {
            Surface {
                content()
            }
        }
    }
}
