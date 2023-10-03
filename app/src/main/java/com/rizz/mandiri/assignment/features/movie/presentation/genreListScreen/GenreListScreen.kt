@file:OptIn(ExperimentalMaterial3Api::class)

package com.rizz.mandiri.assignment.features.movie.presentation.genreListScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rizz.mandiri.assignment.core.utils.extension.Empty
import com.rizz.mandiri.assignment.features.movie.domain.entities.GenreEntity
import com.rizz.mandiri.assignment.ui.components.LoadingDialog
import com.rizz.mandiri.assignment.ui.theme.MandiriAssignmentTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreListScreen(
    state: GenreState,
    onEvent: (GenreEvent) -> Unit,
) {
    LaunchedEffect(Unit) {
        onEvent(GenreEvent.GetGenres)
    }

    if (state.isLoading) {
        LoadingDialog()
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Genre List",
                        style = typography.bodyLarge,
                    )
                },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            if (!state.genresResult.first && state.genresResult.second == null) {
                Text(
                    text = "Failed to load data",
                    style = typography.labelSmall,
                )
                IconButton(onClick = { onEvent(GenreEvent.GetGenres) }) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = String.Empty
                    )
                }
            } else {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(2),
                ) {
                    items(state.genresResult.second?.size ?: 0) {
                        state.genresResult.second?.get(it)?.let { genre ->
                            GenreItem(
                                genre = genre,
                                onClick = {
                                    onEvent(GenreEvent.NavigateToMovieList(genre))
                                }
                            )
                        }
                    }


                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreItem(
    genre: GenreEntity,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
    ) {
        Card(onClick = onClick) {
            Text(
                genre.name ?: String.Empty,
                style = typography.labelSmall,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    MandiriAssignmentTheme {
        GenreListScreen(
            state = GenreState(),
            onEvent = {}
        )
    }
}