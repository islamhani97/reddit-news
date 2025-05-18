package com.islam97.android.apps.redditnews.presentation.newsitemdetails

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import com.islam97.android.apps.redditnews.R
import kotlinx.serialization.Serializable

@Serializable
data class RouteNewsItemDetailsScreen(val title: String, val url: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsItemDetailsScreen(navController: NavController, backStackEntry: NavBackStackEntry) {
    val viewModel: NewsItemDetailsViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val route = backStackEntry.toRoute<RouteNewsItemDetailsScreen>()
    LaunchedEffect(Unit) {
        viewModel.navigationActionsFlow.collect {
            when (it) {
                is NewsItemDetailsIntent.NavigationIntent.Back -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(title = {
                Text(
                    text = route.title, maxLines = 1, overflow = TextOverflow.Ellipsis
                )
            }, navigationIcon = {
                Icon(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(20.dp)
                        .clickable {
                            viewModel.executeIntent(NewsItemDetailsIntent.NavigationIntent.Back)
                        },
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                )
            })
        }) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val (webViewReference, loadingIndicatorReference) = createRefs()
            AndroidView(modifier = Modifier
                .fillMaxSize()
                .constrainAs(webViewReference) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)

                }, factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(
                            view: WebView?, url: String?, favicon: android.graphics.Bitmap?
                        ) {
                            super.onPageStarted(view, url, favicon)
                            viewModel.executeIntent(NewsItemDetailsIntent.SetLoadingState(true))
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            viewModel.executeIntent(NewsItemDetailsIntent.SetLoadingState(false))
                        }
                    }
                    loadUrl(route.url)
                }
            })

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .constrainAs(
                            loadingIndicatorReference
                        ) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                        .size(60.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsItemDetailsScreen() {
    NewsItemDetailsScreen(
        navController = NavHostController(LocalContext.current),
        backStackEntry = NavHostController(LocalContext.current).currentBackStackEntry!!
    )
}