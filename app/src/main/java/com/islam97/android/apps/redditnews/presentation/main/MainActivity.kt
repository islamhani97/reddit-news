package com.islam97.android.apps.redditnews.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.islam97.android.apps.redditnews.presentation.news.NewsScreen
import com.islam97.android.apps.redditnews.presentation.news.RouteNewsScreen
import com.islam97.android.apps.redditnews.presentation.newsitemdetails.NewsItemDetailsScreen
import com.islam97.android.apps.redditnews.presentation.newsitemdetails.RouteNewsItemDetailsScreen
import com.islam97.android.apps.redditnews.presentation.ui.theme.RedditNewsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RedditNewsTheme {
                MainNavigation()
            }
        }
    }
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = RouteNewsScreen
    ) {
        composable<RouteNewsScreen> {
            NewsScreen(navController = navController)
        }

        composable<RouteNewsItemDetailsScreen> {
            NewsItemDetailsScreen(
                navController = navController, backStackEntry = it
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainActivity() {
    RedditNewsTheme { MainNavigation() }
}