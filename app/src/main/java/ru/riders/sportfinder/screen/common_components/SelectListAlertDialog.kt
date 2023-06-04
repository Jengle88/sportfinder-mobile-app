package ru.riders.sportfinder.screen.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ru.riders.sportfinder.screen.ui.theme.LightGreen
import ru.riders.sportfinder.screen.ui.theme.SportFinderLightColorScheme


@Composable
fun SelectListAlertDialog(
    items: List<String>,
    onSaveClick: (List<Int>) -> Unit,
    onDismiss: () -> Unit
) {
    val checkedList =
        Array(items.size) { mutableStateOf(false) }

    Dialog(onDismissRequest = { onDismiss() }) {
        LazyColumn(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {
            for ((i, item) in items.withIndex()) {
                item {
                    Row(
                        modifier = Modifier.clickable {
                            checkedList[i].value = !checkedList[i].value
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = checkedList[i].value,
                            colors = CheckboxDefaults.colors(checkedColor = SportFinderLightColorScheme.primary),
                            onCheckedChange = { checkedList[i].value = it }
                        )
                        Text(item, fontSize = 22.sp)
                    }
                }
            }
            item {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = LightGreen),
                    onClick = {
                        val result = mutableListOf<Int>()
                        checkedList.forEachIndexed { index, state ->
                            if (state.value) {
                                result.add(index)
                            }
                        }
                        onSaveClick(result)
                        onDismiss()
                    }) {
                    Text(
                        "Сохранить",
                        color = SportFinderLightColorScheme.onPrimary
                    )
                }
            }
        }
    }
}