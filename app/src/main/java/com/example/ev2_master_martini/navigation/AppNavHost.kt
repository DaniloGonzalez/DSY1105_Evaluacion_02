package com.example.ev2_master_martini.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ev2_master_martini.ui.screens.CatalogScreen
import com.example.ev2_master_martini.ui.screens.ContactSalesScreen
import com.example.ev2_master_martini.ui.screens.ContentDetailScreen
import com.example.ev2_master_martini.ui.screens.EventsScreen
import com.example.ev2_master_martini.ui.screens.HomeScreen
import com.example.ev2_master_martini.ui.screens.InterestsScreen
import com.example.ev2_master_martini.ui.screens.ProfileScreen
import com.example.ev2_master_martini.ui.screens.RegisterScreen
import com.example.ev2_master_martini.ui.screens.SplashScreen
import com.example.ev2_master_martini.viewmodel.RegisterViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = AppRoute.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(AppRoute.Splash.route) {
            SplashScreen(
                onContinue = {
                    navController.navigate(AppRoute.Register.route)
                }
            )
        }

        composable(AppRoute.Register.route) {
            val registerViewModel: RegisterViewModel = viewModel()
            val state by registerViewModel.uiState.collectAsState()

            RegisterScreen(
                state = state,
                onNameChange = registerViewModel::onNameChange,
                onEmailChange = registerViewModel::onEmailChange,
                onPhoneChange = registerViewModel::onPhoneChange,
                onAddressChange = registerViewModel::onAddressChange,
                onRutChange = registerViewModel::onRutChange,
                onContinue = {
                    if (registerViewModel.validate()) {
                        navController.navigate(AppRoute.Interest.route)
                    }
                }
            )
        }

        composable(AppRoute.Interest.route) {
            InterestsScreen(
                onContinue = {
                    navController.navigate(AppRoute.Home.route) {
                        popUpTo(AppRoute.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(AppRoute.Home.route) {
            HomeScreen(
                onCatalogClick = {
                    navController.navigate(AppRoute.Catalog.route)
                },
                onEventsClick = {
                    navController.navigate(AppRoute.Events.route)
                },
                onProfileClick = {
                    navController.navigate(AppRoute.Profile.route)
                }
            )
        }

        composable(AppRoute.Catalog.route) {
            CatalogScreen(
                onBack = {
                    navController.popBackStack()
                },
                onContentClick = {
                    navController.navigate(AppRoute.ContentDetail.route)
                }
            )
        }

        composable(AppRoute.ContentDetail.route) {
            ContentDetailScreen(
                onBack = {
                    navController.popBackStack()
                },
                onContactSales = {
                    navController.navigate(AppRoute.ContactSales.route)
                }
            )
        }

        composable(AppRoute.ContactSales.route) {
            ContactSalesScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppRoute.Events.route) {
            EventsScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppRoute.Profile.route) {
            ProfileScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
