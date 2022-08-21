package com.testapps.nycschools.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.testapps.nycschools.R
import com.testapps.nycschools.core.NetworkResult
import com.testapps.nycschools.core.Screen
import com.testapps.nycschools.network.model.NycSchool
import com.testapps.nycschools.network.model.SatScores
import com.testapps.nycschools.ui.common.AppScaffold
import com.testapps.nycschools.ui.common.DefaultAppBar
import com.testapps.nycschools.viewmodel.NycHighSchoolListViewModel

private const val MAX_LINES = 2

/**
 * School Lists Screen
 */
@Composable
fun NycHighSchoolsListScreen(
    navController: NavController,
    viewModel: NycHighSchoolListViewModel = hiltViewModel()
) {
    AppScaffold(
        appBar = {
            DefaultAppBar(
                title = stringResource(id = R.string.nyc_high_schools_appbar_title)
            )
        },
        content = { NycHighSchools(viewModel, navController) }
    )
}

@Composable
fun NycHighSchools(
    viewModel: NycHighSchoolListViewModel,
    navController: NavController
) {
    val schoolsList by viewModel.schoolsListLiveData.observeAsState()
    LaunchedEffect(Unit) {
        viewModel.loadNycHighSchoolsList()
    }
    when (val list = schoolsList?.schoolList) {
        is NetworkResult.Error -> {
            ShowError(isDisplayed = true)
        }
        is NetworkResult.Success -> {
            ShowSchoolsList(
                navController = navController,
                isDisplayed = true,
                schoolsList = list.data,
                satScores = schoolsList?.schoolSATScores
            )
        }
        else -> {
            ShowLoading(isDisplayed = true)
        }
    }
}

/**
 * Handles Loading
 */
@Composable
fun ShowLoading(isDisplayed: Boolean = false) {
    if (isDisplayed) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

/**
 * Shows Schools List
 */
@Composable
fun ShowSchoolsList(
    navController: NavController,
    isDisplayed: Boolean = false,
    schoolsList: List<NycSchool>,
    satScores: NetworkResult<List<SatScores>>?
) {
    if (isDisplayed) {
        LazyColumn {
            items(
                items = schoolsList,
                itemContent = {
                    SchoolListItem(schoolData = it, navController, satScores)
                }
            )
        }
    }
}

/**
 * School List Item View
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SchoolListItem(
    schoolData: NycSchool,
    navController: NavController,
    satScores: NetworkResult<List<SatScores>>?
) {
    val dataNotAvailableString = stringResource(
        id = R.string.data_not_available
    )
    val schoolDetailsErrorString = stringResource(
        id = R.string.error_nyc_school_detail)
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        onClick = {
            handleSchoolDetailsOnClick(
                navController,
                satScores,
                schoolData,
                dataNotAvailableString,
                schoolDetailsErrorString,
                context
            )
        }
    ) {
        Row {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = schoolData.schoolName ?: "",
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = "${schoolData.neighborhood}, ${schoolData.city}, ${schoolData.zip}",
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = schoolData.overviewParagraph ?: "",
                    style = MaterialTheme.typography.caption,
                    maxLines = MAX_LINES,
                    overflow = TextOverflow.Clip
                )
                Text(
                    text = stringResource(id = R.string.show_more),
                    style = MaterialTheme.typography.overline
                )
            }
        }
    }
}

/**
 * Navigates to School Details on successful cases
 * on Error, shows error toast
 */
fun handleSchoolDetailsOnClick(
    navController: NavController,
    satScores: NetworkResult<List<SatScores>>?,
    schoolData: NycSchool,
    dataNotAvailable: String,
    toastErrorText: String,
    context: Context,
) {
    when (satScores) {
        is NetworkResult.Success -> {
            satScores.data.firstOrNull { it.dbn == schoolData.dbn }.also {
                navController.navigate(
                    route = "${Screen.SchoolDetailsScreen.route}/${schoolData.dbn}/${schoolData.schoolName}/${schoolData.overviewParagraph}/?readingScore=${it?.reading ?: dataNotAvailable}/?writingScore=${it?.writing ?: dataNotAvailable}/?mathScore=${it?.math ?: dataNotAvailable}/?testTakers=${it?.avgTestTakers ?: dataNotAvailable}/${schoolData.address1},${schoolData.city}, ${schoolData.state}, ${schoolData.zip}"
                )
            }
        }
        is NetworkResult.Error -> {
            Toast.makeText(context, toastErrorText, Toast.LENGTH_LONG).show()
        }
        else -> { /* exhaustive */
        }
    }
}

/**
 * Handles Error
 * TODO Add Retry functionality
 */
@Composable
fun ShowError(isDisplayed: Boolean = false) {
    if (isDisplayed) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row {
                Image(imageVector = Icons.Rounded.Info, contentDescription = "")
                Text(
                    text = stringResource(id = R.string.error_nyc_schools_list)
                )
            }
        }
    }
}


