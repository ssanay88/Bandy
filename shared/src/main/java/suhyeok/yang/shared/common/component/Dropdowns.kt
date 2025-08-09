package suhyeok.yang.shared.common.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import suhyeok.yang.shared.ui.theme.Gray
import suhyeok.yang.shared.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedSpinner(
    modifier: Modifier = Modifier,
    items: List<String>,
    selectedItemIdx: Int = 0,
    label: String = "",
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(items[selectedItemIdx]) }

    LaunchedEffect(items) {
        selectedItem = items[selectedItemIdx]
    }

    Column(modifier = modifier) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedItem,
                onValueChange = { },
                readOnly = true,
                label = { Text(text = label) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach {
                    DropdownMenuItem(
                        text = { Text(text = it) },
                        onClick = {
                            selectedItem = it
                            expanded = false
                            onValueChange(it)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoundedCornerSpinner(
    modifier: Modifier = Modifier,
    items: List<String> = listOf("Item 1", "Item 2", "Item 3"),
    selectedItemIdx: Int = 0,
    onValueChange: (String) -> Unit = {},
    textStyle: TextStyle = TextStyle.Default
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(items[selectedItemIdx]) }

    LaunchedEffect(items) {
        selectedItem = items[selectedItemIdx]
    }

    Column(modifier = modifier) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedItem,
                onValueChange = { },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = modifier.menuAnchor(),
                shape = RoundedCornerShape(50.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = White,
                    unfocusedContainerColor = White,
                    focusedIndicatorColor = Gray,
                    unfocusedIndicatorColor = Gray
                ),
                textStyle = textStyle
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach {item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = item,
                                style = textStyle
                            )
                        },
                        onClick = {
                            selectedItem = item
                            expanded = false
                            onValueChange(item)
                        }
                    )
                }
            }
        }
    }
}