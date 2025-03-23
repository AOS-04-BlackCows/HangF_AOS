package com.compose.hangf_aos

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.compose.hangf_aos.nevigation.HangFNavigation
import com.compose.hangf_aos.ui.theme.HangF_AOSTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HangF_AOSTheme {
                Scaffold(
                    bottomBar = { HangF_BottomAppBar() },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    //TODO "저장된 정보가 있으면 Home, 없으면 Info
                    HangFNavigation(
                        pageName = "Info",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HangF_AOSTheme {
        Greeting("Android")
    }
}

@Composable
fun HangF_BottomAppBar(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    var isShow = remember { mutableStateOf(false) }
    if (isShow.value) {
        FloatingDropdownMenu()
    }

    BottomAppBar(
        actions = {
            IconButton(onClick = {
                Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home Icon",
                )
            }
            IconButton(onClick = {
                Toast.makeText(context, "Profile", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile Icon",
                )
            }
        },
//        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
//        containerColor = MaterialTheme.colorScheme.surface,

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    isShow.value = !isShow.value
                }
            ){
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "FloatingActionButton",
                )
            }
        }
    )
}

@Composable
fun FloatingDropdownMenu(modifier: Modifier = Modifier) {
    val items = listOf("A", "B", "C", "D", "E", "F")
    var expanded by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.BottomEnd)//화면의 오른쪽 아래에 정렬
    ) {
        DropdownMenu(
            expanded = expanded, // 처음에 닫혀 있을지
            onDismissRequest = { expanded = false } //드롭다운 밖을 누르면 닫힘

        ){
            items.forEachIndexed { index, text ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                    },
                    text = {
                        Text(text = text)
                    }
                )
            }
        }
    }
}