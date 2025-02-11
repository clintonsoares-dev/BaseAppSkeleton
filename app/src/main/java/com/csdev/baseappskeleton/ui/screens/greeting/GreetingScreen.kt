package com.csdev.baseappskeleton.ui.screens.greeting

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.csdev.baseappskeleton.ui.theme.BaseAppSkeletonTheme

@Composable
fun GreetingScreen(name: String, modifier: Modifier = Modifier) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Text(
            text = "Hello $name!",
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BaseAppSkeletonTheme {
        GreetingScreen("Clinton")
    }
}