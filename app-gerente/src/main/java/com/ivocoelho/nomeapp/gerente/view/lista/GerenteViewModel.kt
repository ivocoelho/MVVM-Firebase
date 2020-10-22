package com.ivocoelho.nomeapp.gerente.view.lista

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivocoelho.nomeapp.domain.models.Motorista
import com.ivocoelho.nomeapp.domain.repositories.MotoristaRepository
import kotlinx.coroutines.launch

/**
 * @author Ivo Coelho Albuquerque
 * Criado em 20/01/2020
 */

class GerenteViewModel(
    private val motoristaRepository: MotoristaRepository
) : ViewModel() {

    private val mMotoristas = MutableLiveData<List<Motorista>>()
    val motoristas: LiveData<List<Motorista>> = mMotoristas

    private val mListando = MutableLiveData<Boolean>().apply { value = false }
    val listando: LiveData<Boolean> = mListando

    private val mErroAoListar = MutableLiveData<Exception>().apply { value = null }
    val erroAoListar: LiveData<Exception> = mErroAoListar

    fun listar(location: Location) {
        viewModelScope.launch {
            try {
                mErroAoListar.value = null
                mListando.value = true
                mMotoristas.value = motoristaRepository.listar(location.latitude, location.longitude)
            } catch (e: Exception) {
                e.printStackTrace()
                mErroAoListar.value = e
            } finally {
                mListando.value = false
            }
        }
    }

}