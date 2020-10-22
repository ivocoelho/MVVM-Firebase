package com.ivocoelho.nomeapp.motorista

import android.app.Application
import com.ivocoelho.nomeapp.motorista.di.InjecaoDeDependencia
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

/**
 * @author Ivo Coelho Albuquerque
 * Criado em 20/01/2020
 */

class MotoristaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin { loadKoinModules(InjecaoDeDependencia.modulos) }
    }

}