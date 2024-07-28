package dev.pablorjd.listadeudores.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button


import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.pablorjd.listadeudores.domain.model.DefaultingCustomerModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {

    val showDialog: Boolean by viewModel.showDialog.observeAsState(false)

        val items by viewModel.items.collectAsState()
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("Lista Deudores App", textAlign = TextAlign.Center)
                    },
                    actions = {
                        IconButton(onClick = {

                        }) {
                            Icon(Icons.Default.Search, contentDescription = "Settings")
                        }
                    }

                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { viewModel.onShowDialogClick() }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items) { item ->
                        ItemRow(item, onUpdateTap = {}, onDeleteTap = { viewModel.deleteDefaultingCustomer(item) })
                    }
                }

                AddTasksDialog(show = showDialog, onDismiss = { viewModel.onDialogClose() })
            }
        }
}

@Composable
fun ItemRow(
    item: DefaultingCustomerModel,
    onUpdateTap: () -> Unit = {},
    onDeleteTap: (item: DefaultingCustomerModel) -> Unit = {},

) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(120.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = item.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = item.detail?: "", style = MaterialTheme.typography.titleSmall)
            Text(text = item.amount.toString() ?: "", style = MaterialTheme.typography.titleSmall)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 0.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(colors = IconButtonDefaults.iconButtonColors(contentColor = Color.Yellow) , onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.Edit, contentDescription = "Settings")
                }
                IconButton(colors = IconButtonDefaults.iconButtonColors(contentColor = Color.Red) ,onClick = { onDeleteTap(item) }) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Settings")
                }
            }

        }
    }
}

@Composable
fun AddTasksDialog(show: Boolean, onDismiss: () -> Unit, viewModel: HomeViewModel = hiltViewModel()) {
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
                    //modifier = Modifier.align(LineHeightStyle.Alignment.CenterHorizontally),
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
                Button(enabled = if (name.isNotEmpty() && detail.isNotEmpty() && (amount.toLongOrNull()
                        ?: 0) > 0
                ) true else false, onClick = {
                    viewModel.insertDefaultingCustomer(
                        DefaultingCustomerModel( 0,name.uppercase(), detail.uppercase(), amount.toLong())
                    )
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Añadir Deudor")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyLazyColumnPreview() {
    HomeScreen()
}