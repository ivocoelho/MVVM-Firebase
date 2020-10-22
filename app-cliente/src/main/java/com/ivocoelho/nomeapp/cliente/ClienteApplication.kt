package com.ivocoelho.nomeapp.cliente

import android.app.Application
import com.ivocoelho.nomeapp.cliente.di.InjecaoDeDependencia
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

/**
 * @author Thiago Barbosa de Oliveira
 * Criado em 10/01/2019
 */

class ClienteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin { loadKoinModules(InjecaoDeDependencia.modulos) }
    }

}