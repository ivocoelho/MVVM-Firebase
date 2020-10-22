package com.ivocoelho.nomeapp.gerente.di


import com.google.firebase.firestore.FirebaseFirestore
import com.ivocoelho.nomeapp.data.repositories.MotoristaRepositoryImpl
import com.ivocoelho.nomeapp.domain.repositories.MotoristaRepository
import com.ivocoelho.nomeapp.gerente.view.cadastro.CadastroMotoristaViewModel
import com.ivocoelho.nomeapp.gerente.view.lista.GerenteViewModel
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
        viewModel { GerenteViewModel(get()) }
        viewModel { CadastroMotoristaViewModel(get()) }
    }


    private val singles = module {
        single { FirebaseFirestore.getInstance()}
    }


    val modulos = singles + repositorios + viewModels
}