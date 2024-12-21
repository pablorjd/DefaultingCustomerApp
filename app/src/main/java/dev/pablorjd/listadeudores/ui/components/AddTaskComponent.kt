package dev.pablorjd.listadeudores.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import dev.pablorjd.listadeudores.domain.model.DefaultingCustomerModel
import dev.pablorjd.listadeudores.ui.home.HomeViewModel

@Composable
fun AddTasksDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    var name by rememberSaveable { mutableStateOf("") }
    var detail by rememberSaveable { mutableStateOf("") }
    var amount by rememberSaveable { mutableStateOf("") }
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Añade tu Deudor",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    singleLine = true,
                    maxLines = 1,
                    label = { Text("Nombre del Deudor") }
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    value = detail,
                    onValueChange = { detail = it },
                    singleLine = true,
                    maxLines = 1,
                    label = { Text("Descripción de la Deuda") }
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    value = amount,
                    onValueChange = { amount = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    maxLines = 1,
                    label = { Text("Monto de la Deuda") }
                )
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    enabled = if (name.isNotEmpty() && detail.isNotEmpty() && (amount.toLongOrNull()
                            ?: 0) > 0
                    ) true else false, onClick = {
                        viewModel.insertDefaultingCustomer(
                            DefaultingCustomerModel(
                                0,
                                name.uppercase(),
                                detail.uppercase(),
                                amount.toLong()
                            )
                        )
                        name = ""
                        detail = ""
                        amount = ""
                    }, modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Añadir Deudor")
                }
            }
        }
    }
}