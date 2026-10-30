package com.example.ev2_master_martini.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.ev2_master_martini.ui.screens.LoginScreen
import com.example.ev2_master_martini.ui.screens.ProfileScreen
import com.example.ev2_master_martini.ui.screens.RegisterScreen
import com.example.ev2_master_martini.ui.screens.SplashScreen
import com.example.ev2_master_martini.viewmodel.InterestsViewModel
import com.example.ev2_master_martini.viewmodel.LoginViewModel
import com.example.ev2_master_martini.viewmodel.ProfileViewModel
import com.example.ev2_master_martini.viewmodel.RegisterViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = AppRoute.Splash.route
) {
    fun navigateToMain(route: String) {
        navController.navigate(route) {
            popUpTo(AppRoute.Home.route) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(AppRoute.Splash.route) {
            SplashScreen(
                onFinished = {
                    navController.navigate(AppRoute.Login.route) {
                        popUpTo(AppRoute.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(AppRoute.Login.route) {
            val loginViewModel: LoginViewModel = viewModel()
            val state by loginViewModel.uiState.collectAsState()

            LaunchedEffect(state.loginSuccess) {
                if (state.loginSuccess) {
                    navController.navigate(AppRoute.Interest.route) {
                        popUpTo(AppRoute.Login.route) {
                            inclusive = true
                        }
                    }
                }
            }

            LoginScreen(
                state = state,
                onEmailChange = loginViewModel::onEmailChange,
                onPasswordChange = loginViewModel::onPasswordChange,
                onLogin = loginViewModel::login,
                onRegister = {
                    navController.navigate(AppRoute.Register.route)
                }
            )
        }

        composable(AppRoute.Register.route) {
            val registerViewModel: RegisterViewModel = viewModel()
            val state by registerViewModel.uiState.collectAsState()

            LaunchedEffect(state.registrationSaved) {
                if (state.registrationSaved) {
                    navController.navigate(AppRoute.Login.route) {
                        popUpTo(AppRoute.Register.route) {
                            inclusive = true
                        }
                    }
                }
            }

            RegisterScreen(
                state = state,
                onBack = {
                    navController.popBackStack()
                },
                onNameChange = registerViewModel::onNameChange,
                onEmailChange = registerViewModel::onEmailChange,
                onPhoneCountryCodeChange =
                    registerViewModel::onPhoneCountryCodeChange,
                onPhoneNumberChange =
                    registerViewModel::onPhoneNumberChange,
                onAddressChange = registerViewModel::onAddressChange,
                onRutChange = registerViewModel::onRutChange,
                onPasswordChange = registerViewModel::onPasswordChange,
                onConfirmPasswordChange = registerViewModel::onConfirmPasswordChange,
                onContinue = registerViewModel::saveRegistration
            )
        }

        composable(AppRoute.Interest.route) {
            val interestsViewModel: InterestsViewModel = viewModel()
            val state by interestsViewModel.uiState.collectAsState()

            LaunchedEffect(state.interestsSaved) {
                if (state.interestsSaved) {
                    navController.navigate(AppRoute.Home.route) {
                        popUpTo(AppRoute.Interest.route) {
                            inclusive = true
                        }
                    }
                }
            }

            InterestsScreen(
                state = state,
                onBusinessTypeSelected =
                    interestsViewModel::onBusinessTypeSelected,
                onBusinessSizeSelected =
                    interestsViewModel::onBusinessSizeSelected,
                onCatalogOptionToggle =
                    interestsViewModel::onCatalogOptionToggle,
                onContinue = interestsViewModel::saveInterests
            )
        }

        composable(AppRoute.Home.route) {
            HomeScreen(
                onCatalogClick = {
                    navigateToMain(AppRoute.Catalog.route)
                },
                onEventsClick = {
                    navigateToMain(AppRoute.Events.route)
                },
                onProfileClick = {
                    navigateToMain(AppRoute.Profile.route)
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
                },
                onHomeClick = {
                    navigateToMain(AppRoute.Home.route)
                },
                onEventsClick = {
                    navigateToMain(AppRoute.Events.route)
                },
                onProfileClick = {
                    navigateToMain(AppRoute.Profile.route)
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
                },
                onHomeClick = {
                    navigateToMain(AppRoute.Home.route)
                },
                onCatalogClick = {
                    navigateToMain(AppRoute.Catalog.route)
                },
                onProfileClick = {
                    navigateToMain(AppRoute.Profile.route)
                }
            )
        }

        composable(AppRoute.Profile.route) {
            val profileViewModel: ProfileViewModel = viewModel()
            val profileState by profileViewModel.uiState.collectAsState()

            ProfileScreen(
                state = profileState,
                onBack = {
                    navController.popBackStack()
                },
                onHomeClick = {
                    navigateToMain(AppRoute.Home.route)
                },
                onCatalogClick = {
                    navigateToMain(AppRoute.Catalog.route)
                },
                onEventsClick = {
                    navigateToMain(AppRoute.Events.route)
                }
            )
        }
    }
}
