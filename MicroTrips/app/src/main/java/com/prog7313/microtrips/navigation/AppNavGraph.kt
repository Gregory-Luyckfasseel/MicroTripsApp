package com.prog7313.microtrips.navigation

import com.prog7313.microtrips.viewmodel.DestinationViewModel
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prog7313.microtrips.models.Destination
import com.prog7313.microtrips.screens.AddDestScreen
import com.prog7313.microtrips.screens.HomeScreen
import com.prog7313.microtrips.screens.ViewDestScreen
import com.prog7313.microtrips.screens.AddDestScreen

@Composable
fun AppNavGraph(
    onExit: () -> Unit
) {
    val destinationVm: DestinationViewModel = viewModel()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(
                onOpenView = {navController.navigate(Routes.DESTINATIONS) },
                onOpenAddDest = { navController.navigate(Routes.ADD_DESTINATIONS) },
                onExit = onExit
            )
        }

        composable(Routes.DESTINATIONS) {
            ViewDestScreen(
                destinationVm = destinationVm,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.ADD_DESTINATIONS) {
            AddDestScreen(
                onBack = { navController.popBackStack() },
                onSave = { newDestination: Destination ->
                    destinationVm.addDestination(newDestination)
                    navController.popBackStack()
                }
            )
        }
    }
}