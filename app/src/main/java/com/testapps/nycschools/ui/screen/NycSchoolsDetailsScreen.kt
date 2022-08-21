package com.testapps.nycschools.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.testapps.nycschools.R
import com.testapps.nycschools.ui.common.AppScaffold
import com.testapps.nycschools.ui.common.DefaultAppBar
import com.testapps.nycschools.ui.common.DefaultNavigationIcon

/**
 * School Detail Screen to show SAT Scores
 */
@Composable
fun NycHighSchoolsDetailsScreen(
    navController: NavController,
    dbn: String,
    schoolName: String,
    schoolOverview: String,
    reading: String,
    writing: String,
    math: String,
    takers: String,
    address: String
) {
    AppScaffold(
        appBar = {
            DefaultAppBar(
                title = stringResource(id = R.string.nyc_high_school_details_appbar_title),
                navigationIcon = {
                    DefaultNavigationIcon(navController = navController)
                }
            )
        },
        content = {
            NycHighSchoolDetails(
                navController,
                SchoolDetails(
                    dbn = dbn,
                    schoolName = schoolName,
                    schoolOverview = schoolOverview,
                    reading = reading,
                    writing = writing,
                    math = math,
                    takers = takers,
                    address = address
                )
            )
        }
    )
}

@Composable
fun NycHighSchoolDetails(navController: NavController, schoolDetails: SchoolDetails) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState(), enabled = true)
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
    ) {
        Text(
            text = schoolDetails.schoolName,
            style = MaterialTheme.typography.h5
        )
        Text(
            text = schoolDetails.address,
            style = MaterialTheme.typography.subtitle2
        )
        SchoolOverView(overview = schoolDetails.schoolOverview)
        AverageSATScores(schoolDetails)

    }
}

@Composable
fun SchoolOverView(overview: String) {
    Text(
        text = stringResource(id = R.string.school_overview),
        style = MaterialTheme.typography.subtitle1,
        modifier = Modifier.padding(top = 16.dp)
    )
    Text(
        text = overview,
        style = MaterialTheme.typography.body2,
        letterSpacing = 1.sp,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
    )
}

@Composable
fun AverageSATScores(schoolDetails: SchoolDetails) {
    Text(
        style = MaterialTheme.typography.subtitle1,
        modifier = Modifier.padding(top = 16.dp),
        text = "${stringResource(id = R.string.sat_scores)} (${stringResource(id = R.string.sat_takers)}: ${schoolDetails.takers})"
    )
    ScoreTableCell(stringResource(id = R.string.avg_reading_score),schoolDetails.reading)
    ScoreTableCell(stringResource(id = R.string.avg_writing_score),schoolDetails.writing)
    ScoreTableCell(stringResource(id = R.string.avg_math_score),schoolDetails.math)


}

@Composable
fun ScoreTableCell(
    title: String,
    scoreValue: String
) {
    val columnWeight = .5f
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .defaultMinSize(60.dp)
    ) {
        TableCell(text = title, weight = columnWeight)
        TableCell(text = scoreValue, weight = columnWeight)
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp),
        style = MaterialTheme.typography.caption
    )
}

data class SchoolDetails(
    val dbn: String,
    val schoolName: String,
    val schoolOverview: String,
    val reading: String,
    val writing: String,
    val math: String,
    val takers: String,
    val address: String
)