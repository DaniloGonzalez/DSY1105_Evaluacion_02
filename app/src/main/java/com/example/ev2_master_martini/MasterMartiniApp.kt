package com.example.ev2_master_martini

import android.app.Application
import com.example.ev2_master_martini.data.AppContainer

/**
 * CAMBIO REALIZADO (separación de responsabilidades):
 * Application propia de la app. Expone un [AppContainer] con los repositorios,
 * de modo que los ViewModel obtengan sus dependencias desde un único punto y
 * no las construyan manualmente con el contexto.
 */
class MasterMartiniApp : Application() {

    val container: AppContainer by lazy {
        AppContainer(this)
    }
}
