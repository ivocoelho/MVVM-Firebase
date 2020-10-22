package com.ivocoelho.nomeapp.motorista.view.login

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

class LoginViewModel(
    private val motoristaRepository: MotoristaRepository
) : ViewModel() {

    private val mMotorista = MutableLiveData<Motorista>()
    val motorista: LiveData<Motorista> = mMotorista

    private val mSalvandoMotorista = MutableLiveData<Boolean>().apply { value = false }
    val salvandoMotorista: LiveData<Boolean> = mSalvandoMotorista

    private val mBuscandoMotorista = MutableLiveData<Boolean>().apply { value = false }
    val buscandoMotorista: LiveData<Boolean> = mBuscandoMotorista


    private val mMotoristaSalvo = MutableLiveData<Boolean>().apply { value = false }
    val motoristaSalvo: LiveData<Boolean> = mMotoristaSalvo

    private val mErroAoSalvar = MutableLiveData<Exception>().apply { value = null }
    val erroAoSalvar: LiveData<Exception> = mErroAoSalvar


    fun buscarMotoristaPorCodigoAtivacao(codigoAtivacao: String) {
        viewModelScope.launch {
            try {
                mBuscandoMotorista.value = true
                mMotorista.value =  motoristaRepository.buscarMotoristaPorAtivacao(codigoAtivacao)
            } catch (e: Exception) {
                e.printStackTrace()
                mErroAoSalvar.value = e
            } finally {
                mBuscandoMotorista.value = false
                mSalvandoMotorista.value = false
            }
        }
    }

}