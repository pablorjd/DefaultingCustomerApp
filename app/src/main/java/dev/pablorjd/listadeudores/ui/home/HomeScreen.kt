package dev.pablorjd.listadeudores.ui.home


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.pablorjd.listadeudores.domain.model.DefaultingCustomerModel
import dev.pablorjd.listadeudores.ui.components.AddTasksDialog
import dev.pablorjd.listadeudores.ui.components.TopAppBarComponent

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {

    val showDialog: Boolean by viewModel.showDialog.observeAsState(false)

    val items by viewModel.items.collectAsState()
    Scaffold(
        topBar = {
            TopAppBarComponent()
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
                itemsIndexed(items) { index, item ->
                    ItemRow(
                        item.id,
                        item
                    )
                }
            }

            AddTasksDialog(show = showDialog, onDismiss = { viewModel.onDialogClose() })
        }
    }
}

@Composable
fun ItemRow(
    index: Int,
    item: DefaultingCustomerModel,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    var openMenuIndex by remember { mutableStateOf<Int?>(null) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)

    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .width(360.dp)
                    .padding(16.dp)
            ) {
                Text(text = item.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = item.detail ?: "", style = MaterialTheme.typography.titleSmall)
                Text(
                    text = item.amount.toString() ?: "",
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Column {
                IconButton(
                    onClick = { openMenuIndex = index },
                    modifier = Modifier.fillMaxWidth(1f)
                ) {
                    Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "")
                }
                CustomOverflowMenu(
                    show = openMenuIndex == index,
                    item = item,
                    onDismiss = { openMenuIndex = null})
            }

        }
    }
}

@Composable
fun CustomOverflowMenu(
    show: Boolean,
    item: DefaultingCustomerModel,
    onDismiss: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    DropdownMenu(
        expanded = show,
        onDismissRequest = { onDismiss() }
    ) {
        DropdownMenuItem(
            text = {
                Row {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Editar",
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text("Editar")
                }
            },
            onClick = {
                onDismiss()
            }
        )
        DropdownMenuItem(
            text = {
                Row {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete"
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text("Eliminar")
                }
            },
            onClick = {
                onDismiss()
                viewModel.deleteDefaultingCustomer(item)
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun MyLazyColumnPreview(viewModel: HomeViewModel = hiltViewModel()) {
    HomeScreen(viewModel = viewModel)
}