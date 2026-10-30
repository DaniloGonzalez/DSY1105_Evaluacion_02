package com.example.ev2_master_martini.navigation

sealed class AppRoute(val route: String){
    data object Splash : AppRoute("splash")
    data object Login : AppRoute("login")
    data object Register : AppRoute("register")
    data object Interest : AppRoute("interests")
    data object Home: AppRoute("home")
    data object Catalog: AppRoute("catalog")
    data object Events: AppRoute("events")
    data object Profile: AppRoute("profile")
    data object ContentDetail : AppRoute("content_detail")
    data object ContactSales: AppRoute("contact_sales")
}