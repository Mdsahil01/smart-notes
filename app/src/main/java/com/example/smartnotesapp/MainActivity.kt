package com.example.smartnotesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartnotesapp.ui.theme.SmartNotesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()

        }    }
}

data class Note(
    val id: Int,
    val title: String,
    val content: String
)

@Composable
fun MainScreen() {

    var message by remember { mutableStateOf("Started") }

    val notes = listOf(
        Note(1, "First Note", "This is my first note"),
        Note(2, "Second Note", "Learning Jetpack Compose"),
        Note(3, "Third Note", "Ship 70 challenge")
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Ship 70 - Day 2",
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = message,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            message = "In Progress..."
        }) {
            Text("Continue")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "My Notes",
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {
            items(notes) { note ->
                NoteItem(note)
            }
        }
    }
}

@Composable
fun NoteItem(note: Note) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Text(
            text = note.title,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = note.content
        )
    }
}