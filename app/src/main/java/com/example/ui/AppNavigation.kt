package com.example.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.screens.*

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Auth : Screen("auth")
    object Profile : Screen("profile")
    object TournamentDetail : Screen("tournament_detail/{id}")
    object Registration : Screen("registration/{id}")
    object RegistrationConfirmation : Screen("registration_confirmation/{regId}")
    object Leaderboard : Screen("leaderboard/{tournamentId}")
    object AdminLogin : Screen("admin_login")
    object AdminDashboard : Screen("admin_dashboard")
    object ManageTournaments : Screen("manage_tournaments")
    object ManageRegistrations : Screen("manage_registrations")
}

@Composable
fun AppNavigation(viewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onTournamentClick = { id -> navController.navigate("tournament_detail/$id") },
                onAdminClick = { navController.navigate(Screen.AdminLogin.route) },
                onProfileClick = { 
                    if (viewModel.currentUser.value == null) {
                        navController.navigate(Screen.Auth.route)
                    } else {
                        navController.navigate(Screen.Profile.route)
                    }
                }
            )
        }

        composable(Screen.Auth.route) {
            AuthScreen(
                viewModel = viewModel,
                onSuccess = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onLogout = { 
                    viewModel.logout()
                    navController.popBackStack(Screen.Home.route, false)
                }
            )
        }

        composable(
            route = Screen.TournamentDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) {
            TournamentDetailScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onJoin = { 
                    if (viewModel.currentUser.value == null) {
                        navController.navigate(Screen.Auth.route)
                    } else {
                        navController.navigate("registration/${viewModel.selectedTournament.value?.id}")
                    }
                },
                onLeaderboardClick = { id -> navController.navigate("leaderboard/$id") }
            )
        }

        composable(
            route = Screen.Registration.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) {
            RegistrationScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onSuccess = { regId -> 
                    navController.navigate("registration_confirmation/$regId") {
                        popUpTo("tournament_detail/{id}") { inclusive = false }
                    }
                }
            )
        }

        composable(
            route = Screen.RegistrationConfirmation.route,
            arguments = listOf(navArgument("regId") { type = NavType.LongType })
        ) { backStackEntry ->
            val regId = backStackEntry.arguments?.getLong("regId") ?: 0L
            RegistrationConfirmationScreen(
                regId = regId,
                onDone = { navController.popBackStack(Screen.Home.route, false) }
            )
        }

        composable(
            route = Screen.Leaderboard.route,
            arguments = listOf(navArgument("tournamentId") { type = NavType.LongType })
        ) {
            LeaderboardScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.AdminLogin.route) {
            AdminLoginScreen(
                viewModel = viewModel,
                onSuccess = { navController.navigate(Screen.AdminDashboard.route) {
                    popUpTo(Screen.Home.route) { inclusive = false }
                } },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.AdminDashboard.route) {
            AdminDashboardScreen(
                viewModel = viewModel,
                onManageTournaments = { navController.navigate(Screen.ManageTournaments.route) },
                onRegistrations = { navController.navigate(Screen.ManageRegistrations.route) },
                onLogout = { 
                    viewModel.logout()
                    navController.popBackStack(Screen.Home.route, false)
                }
            )
        }

        composable(Screen.ManageTournaments.route) {
            TournamentManagementScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.ManageRegistrations.route) {
            RegistrationManagementScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
