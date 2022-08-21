package com.testapps.nycschools.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.testapps.nycschools.R

/**
 * App Scaffold with AppBar
 * Can be used on all of the composable screen
 */
@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    appBar: @Composable (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    appBar?.let {
        Scaffold(
            modifier = modifier,
            topBar = appBar,
            content = content
        )
    } ?: run {
        { content }
    }
}

/**
 * Default AppBar for NYC Schools
 */
@Composable
fun DefaultAppBar(
    title: String,
    navigationIcon: @Composable (() -> Unit)? = null
) {
    AppBar(
        title = title,
        navigationIcon =  navigationIcon
    )
}

/**
 * Creates TopAppBar
 */
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String = "",
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = { AppBarTitleAndSubtitleText(title = title, subTitle = subTitle, modifier = modifier)},
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions
    )
}

@Composable
fun AppBarTitleAndSubtitleText(title: String, subTitle: String, modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(1.dp),
        horizontalAlignment = Alignment.Start,
    )
    {
        Text(
            text = title,
            style = MaterialTheme.typography.subtitle1
        )
        if (subTitle.isNotEmpty()) {
            Text(
                text = subTitle,
                style = MaterialTheme.typography.caption
            )
        }
    }
}

/**
 * Composable with default navigation icon with back button
 */
@Composable
fun DefaultNavigationIcon(
    navController: NavController,
    icon: ImageVector = Icons.Default.ArrowBack,
    onClick: (() -> Unit) = { navController.navigateUp() }
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(id = R.string.navigation_back_icon_content_description)
        )
    }
}