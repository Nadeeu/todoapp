package com.example.todoapp.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration

@Composable
fun Home(navController: NavHostController) {
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    var todos by remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }
    var newTask by remember { mutableStateOf("") }
    var editingTaskId by remember { mutableStateOf<String?>(null) }
    var snapshotListener: ListenerRegistration? by remember { mutableStateOf(null) }

    LaunchedEffect(user) {
        snapshotListener = db.collection("todos")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    return@addSnapshotListener
                }
                snapshot?.let {
                    todos = it.documents.map { doc ->
                        val task = doc.getString("task") ?: ""
                        val id = doc.id
                        mapOf("task" to task, "id" to id)
                    }
                }
            }
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            snapshotListener?.remove()
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Todo List", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = newTask,
            onValueChange = { newTask = it },
            label = { Text("Enter a task") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (newTask.isNotEmpty()) {
                        if (editingTaskId == null) {
                            db.collection("todos").add(mapOf("task" to newTask))
                        } else {
                            db.collection("todos").document(editingTaskId!!).update("task", newTask)
                        }
                        newTask = ""
                        editingTaskId = null
                    }
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (newTask.isNotEmpty()) {
                    if (editingTaskId == null) {
                        db.collection("todos").add(mapOf("task" to newTask))
                    } else {
                        db.collection("todos").document(editingTaskId!!).update("task", newTask)
                    }
                    newTask = ""
                    editingTaskId = null
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Task")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(todos) { todo ->
                val task = todo["task"] as String
                val id = todo["id"] as String
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Text(text = task, modifier = Modifier.weight(1f))
                    IconButton(onClick = {
                        newTask = task
                        editingTaskId = id
                    }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Task")
                    }
                    IconButton(onClick = {
                        db.collection("todos").document(id).delete()
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Task")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { auth.signOut(); navController.navigate("login") }) {
            Text("Sign Out")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val navController = rememberNavController()
    Home(navController = navController)
}