package com.ivocoelho.nomeapp.gerente

import android.app.Application
import com.ivocoelho.nomeapp.gerente.di.InjecaoDeDependencia
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

/**
 * @author Thiago Barbosa de Oliveira
 * Criado em 10/01/2019
 */

class GerenteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin { loadKoinModules(InjecaoDeDependencia.modulos) }
    }

}