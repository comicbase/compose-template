package com.example.composetemplate.navigation

/** 顶层目的地仅在 app 中存在，Feature 不知道路由格式和回退栈结构。 */
sealed interface AppDestination {
    data object Home : AppDestination
    data object Profile : AppDestination
    data class Article(val id: String) : AppDestination
}

