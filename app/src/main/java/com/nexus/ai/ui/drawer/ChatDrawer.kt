package com.nexus.ai.ui.drawer
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nexus.ai.vm.DrawerViewModel
import com.nexus.ai.vm.ChatViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDrawer(drawerState: DrawerState, viewModel: DrawerViewModel, chatViewModel: ChatViewModel, onSettingsClick: () -> Unit, content: @Composable () -> Unit) {
    val scope = rememberCoroutineScope()
    val chats by viewModel.chats.collectAsStateWithLifecycle(initialValue = emptyList())

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = MaterialTheme.colorScheme.background) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text("المحادثات", modifier = Modifier.padding(16.dp), fontSize = 18.sp)
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(chats) { chat ->
                            Row(modifier = Modifier.fillMaxWidth().clickable { 
                                chatViewModel.selectChat(chat.id) 
                                scope.launch { drawerState.close() } 
                            }.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Text(chat.title, modifier = Modifier.weight(1f), maxLines = 1, overflow = TextOverflow.Ellipsis)
                                IconButton(onClick = { viewModel.deleteChat(chat.id) }) { Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.onSurfaceVariant) }
                            }
                        }
                    }
                    HorizontalDivider()
                    Row(modifier = Modifier.fillMaxWidth().clickable { onSettingsClick() }.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Settings, contentDescription = null)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("الإعدادات")
                    }
                }
            }
        },
        content = content
    )
}
