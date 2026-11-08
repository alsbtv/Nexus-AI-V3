package com.nexus.ai.ui.chat
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nexus.ai.data.db.MessageEntity

@Composable
fun MessageRow(message: MessageEntity) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start) {
        if (message.isUser) {
            Surface(shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.surfaceVariant) {
                SelectionContainer { Text(text = message.text, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.sp, modifier = Modifier.padding(12.dp)) }
            }
        } else {
            SelectionContainer { Text(text = message.text, color = MaterialTheme.colorScheme.onBackground, fontSize = 16.sp, lineHeight = 24.sp, modifier = Modifier.padding(12.dp)) }
        }
    }
}
