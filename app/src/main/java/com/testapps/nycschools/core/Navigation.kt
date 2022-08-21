package com.testapps.nycschools.core

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.testapps.nycschools.ui.screen.NycHighSchoolsDetailsScreen
import com.testapps.nycschools.ui.screen.NycHighSchoolsListScreen

sealed class Screen(val route: String) {
    object NycHighSchoolsListScreen : Screen("school_list_screen")
    object SchoolDetailsScreen :
        Screen("school_details_screen")
}

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.NycHighSchoolsListScreen.route
    ) {
        composable(route = Screen.NycHighSchoolsListScreen.route) {
            NycHighSchoolsListScreen(navController)
        }
        composable(
            // TODO Explore Parcelable Type instead of passing these many arguments
            route = "${Screen.SchoolDetailsScreen.route}/{dbn}/{schoolName}/{schoolOverview}/?readingScore={reading}/?writingScore={writing}/?mathScore={math}/?testTakers={takers}/{address}",
            arguments = listOf(
                navArgument("dbn") { type = NavType.StringType },
                navArgument("schoolName") { type = NavType.StringType },
                navArgument("schoolOverview") { type = NavType.StringType },
                navArgument("reading") { defaultValue = "" },
                navArgument("writing") { defaultValue = "" },
                navArgument("math") { defaultValue = "" },
                navArgument("takers") { defaultValue = "" },
                navArgument("address") { type = NavType.StringType },
            )
        ) {
            val dbn = it.arguments?.getString("dbn")
            val schoolName = it.arguments?.getString("schoolName")
            val schoolOverview = it.arguments?.getString("schoolOverview")
            val reading = it.arguments?.getString("reading")
            val writing = it.arguments?.getString("writing")
            val math = it.arguments?.getString("math")
            val takers = it.arguments?.getString("takers")
            val address = it.arguments?.getString("address")
            NycHighSchoolsDetailsScreen(
                navController = navController,
                dbn = dbn ?: "",
                schoolName = schoolName ?: "",
                schoolOverview = schoolOverview ?: "",
                reading = reading ?: "",
                writing = writing ?: "",
                math = math ?: "",
                takers = takers ?: "",
                address = address ?: ""
            )
        }
    }
}