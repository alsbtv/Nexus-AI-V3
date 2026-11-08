package com.nexus.ai.ui.chat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NexusTopBar(chatTitle: String, modelName: String, onMenuClick: () -> Unit, onModelClick: () -> Unit, onNewChatClick: () -> Unit, onMontageClick: () -> Unit) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(chatTitle, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.width(4.dp))
                Text("·", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.width(4.dp))
                Text("$modelName ▾", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.clickable { onModelClick() })
            }
        },
        navigationIcon = { IconButton(onClick = onMenuClick) { Icon(Icons.Default.Menu, contentDescription = "Menu") } },
        actions = {
            IconButton(onClick = onMontageClick) { Icon(Icons.Default.Movie, contentDescription = "Montage") }
            IconButton(onClick = onNewChatClick) { Icon(Icons.Default.AddCircleOutline, contentDescription = "New Chat") }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
    )
}
