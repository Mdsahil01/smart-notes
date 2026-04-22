package com.example.smartnotesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val notes = remember { mutableStateListOf<Note>()}
            NavGraph(navController,notes)

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

    var title by remember {mutableStateOf("")}
    var content by remember {mutableStateOf("")}

    var  notes by remember {
        mutableStateOf(
            listOf(
                Note(1, "First Note", "This is my first note"),
                Note(2, "Second Note", "Learning Jetpack Compose"),
                Note(3, "Third Note", "Ship 70 challenge")
            )
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
     {

    OutlinedTextField(
            value = title,
            onValueChange = {title = it},
            label = {Text("Title")}
            )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content")}
        )

         Button(onClick = {
             val newNote = Note(
                 id = notes.size + 1,
                 title = title,
                 content = content
             )

             notes = notes + newNote

             // clear inputs
             title = ""
             content = ""
         }) {
             Text("Add Note")
         }


        Text(
            text = "Ship 70 - Day 3",
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

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = note.title, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = note.content, fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun NavGraph(
    navController: NavController,
    notes: MutableList<Note>
) {

    NavHost(
        navController = navController as NavHostController,
        startDestination = "list"
    ) {

        composable("list") {
            NotesListScreen(navController,notes)
        }

        composable("add") {
            AddNotesScreen(navController,notes)
        }
    }
}
@Composable
fun NotesListScreen(
    navController: NavController,
    notes: List<Note>
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        if (notes.isEmpty()) {
            Text(
                text = "No notes yet. Tap + to add one 👇",
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(notes) { note ->
                    NoteItem(note)
                }
            }
        }

        FloatingActionButton(
            onClick = {
                navController.navigate("add")
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Note"
            )
        }
    }
}
@Composable
fun AddNotesScreen(
    navController: NavHostController,
    notes: MutableList<Note>
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column( modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content") }
        )
        Button(
            onClick = {
                notes.add(
                    Note(
                        id = notes.size,
                        title = title,
                        content = content
                    )
                )
                navController.popBackStack()
            }
        ) {
            Text("Save Note")
        }
    }
}
