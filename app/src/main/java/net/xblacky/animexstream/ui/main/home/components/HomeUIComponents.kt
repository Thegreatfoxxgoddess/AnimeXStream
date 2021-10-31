package net.xblacky.animexstream.ui.main.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import net.xblacky.animexstream.R
import net.xblacky.animexstream.utils.Utils
import net.xblacky.animexstream.utils.constants.C
import net.xblacky.animexstream.utils.model.AnimeMetaModel
import net.xblacky.animexstream.utils.model.HomeScreenModel


@ExperimentalCoilApi
@Composable
fun AnimeHomeElement(
    modifier: Modifier = Modifier,
    animeData: AnimeMetaModel
) {
    Column(modifier = modifier) {
        AnimeHomeImage(
            modifier = Modifier
                .width(105.dp)
                .height(180.dp)
                .clip(CutCornerShape(0.dp,24.dp)),
            imageUrl = animeData.imageUrl,
            isEpisode = !animeData.episodeUrl.isNullOrEmpty()
        )
        AnimeHomeTitleAndSubTitle(
            modifier = Modifier.width(105.dp),
            title = animeData.title,
            subtitle = if (!animeData.episodeNumber.isNullOrEmpty()) animeData.episodeNumber else animeData.releasedDate
        )

    }

}


@ExperimentalCoilApi
@Composable
fun HomeScreen(modifier: Modifier = Modifier, list: ArrayList<HomeScreenModel>) {
    LazyColumn(modifier = modifier) {

        items(list) { homeData: HomeScreenModel ->
            if (!homeData.animeList.isNullOrEmpty()) {
                AnimeMiniHeader(animeType = homeData.typeValue)

                homeData.animeList?.let { animeList ->
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
                    ) {
                        items(animeList) { animeData: AnimeMetaModel ->
                            AnimeHomeElement(animeData = animeData)
                        }
                    }
                }
            }
        }
    }

}


@Composable
fun AnimeMiniHeader(modifier: Modifier = Modifier, animeType: Int) {
    Row(modifier = modifier.fillMaxWidth()) {

        Text(
            modifier = Modifier.padding(16.dp, 24.dp),
            text = Utils.getTypeName(animeType),
            fontSize = 18.sp,
            color = colorResource(id = R.color.recycler_mini_header_title)
        )

    }
}


@ExperimentalCoilApi
@Composable
fun AnimeHomeImage(modifier: Modifier, imageUrl: String, isEpisode: Boolean) {
    Box(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = rememberImagePainter(imageUrl),
            contentDescription = "Anime Image",
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )

        if (isEpisode) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0xCC000000)
                            )
                        )
                    )
            )
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.ic_play_button),
                    contentDescription = "Play Icon"
                )

            }
        }


    }
}

@Composable
fun AnimeHomeTitleAndSubTitle(modifier: Modifier = Modifier, title: String, subtitle: String?) {
    Column(
        modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .heightIn(min = 32.dp)
                .padding(horizontal = 4.dp, vertical = 2.dp),
            text = title,
            fontSize = 14.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = colorResource(id = R.color.recycler_anime_title),
            textAlign = TextAlign.Center
        )
        subtitle?.let {
            Text(
                text = it,
                fontSize = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = colorResource(id = R.color.recycler_releases_date),
                textAlign = TextAlign.Center
            )
        }
    }
}


@ExperimentalCoilApi
@Preview
@Composable
fun AnimeInfoModelPreview() {
    AnimeHomeElement(
        animeData = AnimeMetaModel(
            title = "One Piece",
            typeValue = C.TYPE_RECENT_SUB,
            categoryUrl = "URL",
            imageUrl = "https://gogocdn.net/images/anime/One-piece.jpg",
            genreList = null,
            ID = 123,
            episodeNumber = "900",
            episodeUrl = "URL"
        )
    )
}