package com.islam97.android.apps.redditnews.presentation.news

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.islam97.android.apps.redditnews.R
import com.islam97.android.apps.redditnews.presentation.newsitemdetails.RouteNewsItemDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
object RouteNewsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(navController: NavController) {
    val viewModel: NewsViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val pullRefreshState = rememberPullToRefreshState()

    LaunchedEffect(Unit) {
        viewModel.navigationActionsFlow.collect {
            when (it) {
                is NewsIntent.NavigationIntent.NavigateToDetails -> {
                    navController.navigate(
                        RouteNewsItemDetailsScreen(
                            it.item.title, it.item.url
                        )
                    )
                }
            }
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(title = {
                Text(text = stringResource(R.string.title_news))
            })
        }) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)

        ) {
            val (loadingIndicatorReference, messageReference, newsListReference) = createRefs()

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

            state.errorMessage?.let { errorMessage ->
                Text(
                    modifier = Modifier.constrainAs(
                        messageReference
                    ) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.percent(0.9f)
                    }, text = errorMessage, textAlign = TextAlign.Center
                )
                true
            }

            state.errorMessage?.let { errorMessage ->
                Text(
                    modifier = Modifier.constrainAs(
                        messageReference
                    ) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.percent(0.9f)
                    }, text = errorMessage, textAlign = TextAlign.Center
                )
            }

            state.news?.let { news ->
                PullToRefreshBox(
                    modifier = Modifier
                        .constrainAs(
                            newsListReference
                        ) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                        .fillMaxSize(),
                    isRefreshing = state.isRefreshing,
                    state = pullRefreshState,
                    onRefresh = {
                        viewModel.executeIntent(NewsIntent.GetNews(true))
                    }) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(news.size) { index ->
                            NewsItemView(
                                modifier = Modifier
                                    .fillMaxWidth(0.95f)
                                    .padding(top = if (index != 0) 20.dp else 0.dp),
                                newsItem = news[index]
                            ) {
                                viewModel.executeIntent(
                                    NewsIntent.NavigationIntent.NavigateToDetails(
                                        news[index]
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsScreen() {
    NewsScreen(navController = NavHostController(LocalContext.current))
}