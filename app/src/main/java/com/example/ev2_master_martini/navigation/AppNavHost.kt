package com.example.ev2_master_martini.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ev2_master_martini.data.AppContainer
import com.example.ev2_master_martini.data.remote.NetworkLogStore
import com.example.ev2_master_martini.model.StartDestination
import com.example.ev2_master_martini.ui.screens.CatalogScreen
import com.example.ev2_master_martini.ui.screens.ContactSalesScreen
import com.example.ev2_master_martini.ui.screens.ContentDetailScreen
import com.example.ev2_master_martini.ui.screens.EventDetailScreen
import com.example.ev2_master_martini.ui.screens.EventsScreen
import com.example.ev2_master_martini.ui.screens.HomeScreen
import com.example.ev2_master_martini.ui.screens.InspirationDetailScreen
import com.example.ev2_master_martini.ui.screens.InterestsScreen
import com.example.ev2_master_martini.ui.screens.LoginScreen
import com.example.ev2_master_martini.ui.screens.ProfileScreen
import com.example.ev2_master_martini.ui.screens.RecipeDetailScreen
import com.example.ev2_master_martini.ui.screens.RecipesScreen
import com.example.ev2_master_martini.ui.screens.RegisterScreen
import com.example.ev2_master_martini.ui.screens.SplashScreen
import com.example.ev2_master_martini.viewmodel.CatalogViewModel
import com.example.ev2_master_martini.viewmodel.EventsViewModel
import com.example.ev2_master_martini.viewmodel.HomeViewModel
import com.example.ev2_master_martini.viewmodel.InspirationDetailViewModel
import com.example.ev2_master_martini.viewmodel.InspirationViewModel
import com.example.ev2_master_martini.viewmodel.InterestsViewModel
import com.example.ev2_master_martini.viewmodel.LoginViewModel
import com.example.ev2_master_martini.viewmodel.ProfileViewModel
import com.example.ev2_master_martini.viewmodel.RecipesViewModel
import com.example.ev2_master_martini.viewmodel.RegisterViewModel
import com.example.ev2_master_martini.viewmodel.SplashViewModel
import com.example.ev2_master_martini.viewmodel.ViewModelFactory

/**
 * CAMBIO REALIZADO (separación de responsabilidades):
 * El NavHost ya no lee DataStore. Recibe el [AppContainer] y construye los
 * ViewModel a través de [ViewModelFactory]; toda decisión de negocio (destino
 * de entrada, post-login, contenido del catálogo/recetas/eventos) la resuelven
 * los ViewModel / repositorios.
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    container: AppContainer,
    startDestination: String = AppRoute.Splash.route
) {
    val factory = remember(container) {
        ViewModelFactory(container)
    }

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
        startDestination = startDestination,
        // Transiciones de navegación (animaciones funcionales)
        enterTransition = {
            fadeIn(tween(300)) + slideInVertically(tween(300)) { it / 4 }
        },
        exitTransition = {
            fadeOut(tween(200)) + slideOutVertically(tween(200)) { -it / 8 }
        },
        popEnterTransition = {
            fadeIn(tween(300)) + slideInVertically(tween(300)) { -it / 8 }
        },
        popExitTransition = {
            fadeOut(tween(200)) + slideOutVertically(tween(200)) { it / 4 }
        }
    ) {
        composable(AppRoute.Splash.route) {
            val splashViewModel: SplashViewModel = viewModel(factory = factory)
            val destination by splashViewModel.destination.collectAsState()

            LaunchedEffect(destination) {
                destination?.let { resolved ->
                    navController.navigate(routeFor(resolved)) {
                        popUpTo(AppRoute.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            }

            SplashScreen(
                onFinished = splashViewModel::resolveStartDestination
            )
        }

        composable(AppRoute.Login.route) {
            val loginViewModel: LoginViewModel = viewModel(factory = factory)
            val state by loginViewModel.uiState.collectAsState()

            LaunchedEffect(state.postLoginDestination) {
                state.postLoginDestination?.let { resolved ->
                    navController.navigate(routeFor(resolved)) {
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
            val registerViewModel: RegisterViewModel = viewModel(factory = factory)
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
            val interestsViewModel: InterestsViewModel = viewModel(factory = factory)
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
            val homeViewModel: HomeViewModel = viewModel(factory = factory)
            val state by homeViewModel.uiState.collectAsState()

            // CAMBIO REALIZADO (integración de API externa - TheMealDB):
            // ViewModel de la sección "Inspírate" y registros de red para la
            // evidencia de las llamadas (chip de estado + Consola de red).
            val inspirationViewModel: InspirationViewModel = viewModel(factory = factory)
            val inspirationState by inspirationViewModel.uiState.collectAsState()
            val networkCalls by NetworkLogStore.calls.collectAsState()

            HomeScreen(
                state = state,
                inspirationState = inspirationState,
                networkCalls = networkCalls,
                onCatalogClick = {
                    navigateToMain(AppRoute.Catalog.route)
                },
                onEventsClick = {
                    navigateToMain(AppRoute.Events.route)
                },
                onProfileClick = {
                    navigateToMain(AppRoute.Profile.route)
                },
                onRecommendationClick = {
                    navigateToMain(AppRoute.Catalog.route)
                },
                onEventClick = { eventId ->
                    navController.navigate(
                        "${AppRoute.EventDetail.route}/$eventId"
                    )
                },
                onInspirationQueryChange = inspirationViewModel::onQueryChange,
                onInspirationSearch = inspirationViewModel::search,
                onInspirationCategorySelected = inspirationViewModel::onCategorySelected,
                onInspirationSurprise = inspirationViewModel::surpriseMe,
                onInspirationRetry = inspirationViewModel::retry,
                // CAMBIO REALIZADO (Parte 4): navegación al detalle de receta API.
                onInspirationRecipeClick = { mealId ->
                    navController.navigate(
                        "${AppRoute.InspirationDetail.route}/$mealId"
                    )
                },
                onClearNetworkLog = NetworkLogStore::clear
            )
        }

        composable(AppRoute.Catalog.route) {
            val catalogViewModel: CatalogViewModel = viewModel(factory = factory)
            val state by catalogViewModel.uiState.collectAsState()

            CatalogScreen(
                state = state,
                onCategorySelected = catalogViewModel::onCategorySelected,
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
                onRecipesClick = {
                    navigateToMain(AppRoute.Recipes.route)
                },
                onProfileClick = {
                    navigateToMain(AppRoute.Profile.route)
                }
            )
        }

        composable(AppRoute.Recipes.route) {
            val recipesViewModel: RecipesViewModel = viewModel(factory = factory)

            RecipesScreen(
                recipes = recipesViewModel.getRecipes(),
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
                },
                onProfileClick = {
                    navigateToMain(AppRoute.Profile.route)
                },
                onRecipeClick = { recipeId ->
                    navController.navigate(
                        "${AppRoute.RecipeDetail.route}/$recipeId"
                    )
                }
            )
        }

        composable("${AppRoute.RecipeDetail.route}/{recipeId}") { backStackEntry ->
            val recipesViewModel: RecipesViewModel = viewModel(factory = factory)
            val recipe = recipesViewModel.getRecipe(
                backStackEntry.arguments?.getString("recipeId").orEmpty()
            )

            RecipeDetailScreen(
                recipe = recipe,
                onBack = {
                    navController.popBackStack()
                },
                onContactSales = {
                    navController.navigate(AppRoute.ContactSales.route)
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

        // CAMBIO REALIZADO (integración de API externa - TheMealDB):
        // Detalle de una receta de la API usando lookup.php?i={id}.
        composable("${AppRoute.InspirationDetail.route}/{mealId}") { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("mealId").orEmpty()
            val inspirationDetailViewModel: InspirationDetailViewModel =
                viewModel(factory = factory)

            LaunchedEffect(mealId) {
                inspirationDetailViewModel.load(mealId)
            }

            val detailState by inspirationDetailViewModel.uiState.collectAsState()

            InspirationDetailScreen(
                state = detailState,
                onBack = {
                    navController.popBackStack()
                },
                onRetry = {
                    inspirationDetailViewModel.load(mealId)
                }
            )
        }

        composable(AppRoute.Events.route) {
            val eventsViewModel: EventsViewModel = viewModel(factory = factory)

            EventsScreen(
                events = eventsViewModel.getEvents(),
                onBack = {
                    navController.popBackStack()
                },
                onHomeClick = {
                    navigateToMain(AppRoute.Home.route)
                },
                onCatalogClick = {
                    navigateToMain(AppRoute.Catalog.route)
                },
                onEventsClick = {},
                onProfileClick = {
                    navigateToMain(AppRoute.Profile.route)
                },
                onEventClick = { eventId ->
                    navController.navigate(
                        "${AppRoute.EventDetail.route}/$eventId"
                    )
                }
            )
        }

        composable("${AppRoute.EventDetail.route}/{eventId}") { backStackEntry ->
            val eventsViewModel: EventsViewModel = viewModel(factory = factory)
            val event = eventsViewModel.getEvent(
                backStackEntry.arguments?.getString("eventId").orEmpty()
            )

            EventDetailScreen(
                event = event,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppRoute.Profile.route) {
            val profileViewModel: ProfileViewModel = viewModel(factory = factory)
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
                },
                onNameChange = profileViewModel::onNameChange,
                onEmailChange = profileViewModel::onEmailChange,
                onPhoneChange = profileViewModel::onPhoneChange,
                onAddressChange = profileViewModel::onAddressChange,
                onRutChange = profileViewModel::onRutChange,
                onBusinessTypeChange = profileViewModel::onBusinessTypeChange,
                onBusinessSizeChange = profileViewModel::onBusinessSizeChange,
                onCatalogOptionToggle = profileViewModel::onCatalogOptionToggle,
                onSaveAccount = {
                    profileViewModel.saveAccount {}
                },
                onSaveInterests = profileViewModel::saveInterests,
                onLargeTextChange = profileViewModel::onLargeTextChange,
                onLogout = {
                    profileViewModel.logout {
                        navController.navigate(AppRoute.Login.route) {
                            popUpTo(AppRoute.Home.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            )
        }
    }
}

/** Traduce el destino resuelto por los ViewModel a una ruta de navegación. */
private fun routeFor(destination: StartDestination): String = when (destination) {
    StartDestination.Home -> AppRoute.Home.route
    StartDestination.Interests -> AppRoute.Interest.route
    StartDestination.Login -> AppRoute.Login.route
}
