package com.waseem.sample.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.waseem.sample.MainActivity
import com.waseem.sample.feature.auth.domain.User
import com.waseem.sample.feature.home.presentation.HomeScreen
import com.waseem.sample.feature.home.presentation.HomeViewModel
import com.waseem.sample.feature.medicine.presentation.MedicineScreen
import dagger.hilt.android.EntryPointAccessors

@Composable
fun AppNavGraph(
    navController: NavHostController,
    user: User
) {
    NavHost(
        navController = navController,
        startDestination = Route.Home.route
    ) {
        addHomeRoute(navController = navController, user = user)
        addMedicineRoute(navController)
    }
}

//home route
private fun NavGraphBuilder.addHomeRoute(navController: NavController, user: User) {
    composable(route = Route.Home.route) {
        val factory = EntryPointAccessors.fromActivity(
            LocalContext.current as Activity,
            MainActivity.HomeViewModelFactoryProvider::class.java
        ).homeViewModelFactory()

        val homeViewModel: HomeViewModel = viewModel(
            key = user.id,
            factory = HomeViewModel.provideFactory(factory, user)
        )
        HomeScreen(
            viewModel = homeViewModel,
            onMedicineClick = {
                navController.navigate(Route.Medicine.createRoute(it))
            }
        )
    }
}

//medicine route
private fun NavGraphBuilder.addMedicineRoute(navController: NavController) {
    composable(
        route = Route.Medicine.route,
        arguments = listOf(navArgument("drugName") { type = NavType.StringType })
    ) {
        MedicineScreen(
            viewModel = hiltViewModel(),
            onBackClick = {
                navController.popBackStack()
            }
        )
    }
}
