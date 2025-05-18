package com.islam97.android.apps.redditnews.presentation.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.islam97.android.apps.redditnews.domain.models.NewsItem
import com.islam97.android.apps.redditnews.domain.models.Thumbnail

@Composable
fun NewsItemView(
    modifier: Modifier = Modifier, newsItem: NewsItem, onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = onClick
    ) {
        Box(modifier = Modifier) {
            val imageModifier = Modifier
            // Consider aspect ratio according to the with and height if they exist
            newsItem.thumbnail.let { thumbnail ->
                thumbnail.width?.let { width ->
                    thumbnail.height?.let { height ->
                        imageModifier.aspectRatio(width.toFloat() / height.toFloat())
                    }
                }
            }

            AsyncImage(
                modifier = imageModifier.fillMaxWidth(),
                model = newsItem.thumbnail.url,
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()

                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xB3000000),
                                Color(0x99000000),
                                Color(0x80000000),
                            )
                        )
                    )
                    .padding(12.dp),
                text = newsItem.title,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White, fontWeight = FontWeight.Bold
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsItemView() {
    NewsItemView(
        modifier = Modifier, newsItem = NewsItem(
            title = "News Title",
            thumbnail = Thumbnail(url = null, width = null, height = null),
            url = "https://www.google.com"
        )
    )
}