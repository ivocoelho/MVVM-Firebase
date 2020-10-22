package com.ivocoelho.nomeapp.cliente.di

import com.google.firebase.firestore.FirebaseFirestore
import com.ivocoelho.nomeapp.cliente.view.lista.ClienteViewModel
import com.ivocoelho.nomeapp.data.repositories.MotoristaRepositoryImpl
import com.ivocoelho.nomeapp.domain.repositories.MotoristaRepository
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


/**
 * @author Ivo Coelho Albuquerque
 * Criado em 20/01/2020
 */

object InjecaoDeDependencia {


    private val repositorios = module {
        single { MotoristaRepositoryImpl(get()) as MotoristaRepository }
    }

    private val viewModels = module {
        viewModel { ClienteViewModel(get()) }
    }


    private val singles = module {
        single { FirebaseFirestore.getInstance()}
    }


    val modulos = singles + viewModels + repositorios
}