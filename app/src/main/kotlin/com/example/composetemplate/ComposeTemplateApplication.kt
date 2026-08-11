package com.example.composetemplate

import android.app.Application
import com.example.composetemplate.di.AppContainer

/**
 * 应用级组合根的宿主。Application 只负责持有应用生命周期依赖，
 * 具体对象绑定仍集中在 [AppContainer]，避免业务初始化散落到 Activity。
 */
class ComposeTemplateApplication : Application() {
    val appContainer: AppContainer by lazy { AppContainer() }
}
