package com.prog7313.microtrips

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prog7313.microtrips.navigation.Routes
import com.prog7313.microtrips.screens.AddDestScreen
import com.prog7313.microtrips.screens.HomeScreen
import com.prog7313.microtrips.screens.ViewDestScreen
import com.prog7313.microtrips.ui.theme.MicroTripsTheme
import com.prog7313.microtrips.viewmodel.DestinationViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MicroTripsTheme {
                NavGraph(onExit = { finish() })
            }
        }
    }
}

@Composable
fun NavGraph(onExit: () -> Unit) {
    val navController = rememberNavController()
    val viewModel: DestinationViewModel = viewModel()

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(
                onOpenAddDest = { navController.navigate(Routes.ADD_DESTINATIONS) },
                onOpenView = { navController.navigate(Routes.DESTINATIONS) },
                onExit = onExit
            )
        }

        composable(Routes.DESTINATIONS) {
            ViewDestScreen(
                destinationVm = viewModel,
                onBack = { navController.popBackStack() },
            )
        }

        composable(Routes.ADD_DESTINATIONS) {
            AddDestScreen(
                onBack = { navController.popBackStack() },
                onSave = {
                    viewModel.addDestination(it)
                    navController.popBackStack()
                }
            )
        }
    }
}