package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.data.local.AppDatabase
import com.example.data.repository.AppRepository
import com.example.ui.AppNavigation
import com.example.ui.MainViewModel
import com.example.ui.theme.DXESPORTSTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    val db = Room.databaseBuilder(
      applicationContext,
      AppDatabase::class.java, "dxesports-db"
    ).fallbackToDestructiveMigration().build()

    val repository = AppRepository(db.appDao())
    val viewModel = MainViewModel(repository)

    setContent {
      DXESPORTSTheme {
        AppNavigation(viewModel)
      }
    }
  }
}
