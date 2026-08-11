package com.example.composetemplate.feature.home.navigation

/** Feature 只声明导航意图，app 模块决定具体路由和回退栈实现。 */
interface HomeNavigator {
    fun openArticle(id: String)
    fun openProfile()
}

