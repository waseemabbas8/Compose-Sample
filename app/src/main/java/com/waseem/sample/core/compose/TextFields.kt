package com.waseem.sample.core.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.waseem.sample.R
import com.waseem.sample.core.ui.ThemedPreview

@Composable
fun EditText(
    modifier: Modifier = Modifier,
    text: String,
    hint: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = text,
        placeholder = { Text(text = hint) },
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        shape = MaterialTheme.shapes.small,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun PreviewTextFields() {
    ThemedPreview {
        Column {
            EditText(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.onboarding_padding)),
                hint = stringResource(id = R.string.hint_email),
                text = "",
                onValueChange = {}
            )
        }
    }
}