package com.ivocoelho.nomeapp.gerente.view.cadastro

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

class CadastroMotoristaViewModel(
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


    fun buscarMotorista(motoristaId: String) {
        viewModelScope.launch {
            try {
                mBuscandoMotorista.value = true
              mMotorista.value =  motoristaRepository.buscarMotorista(motoristaId)
            } catch (e: Exception) {
                e.printStackTrace()
                mErroAoSalvar.value = e
            } finally {
                mBuscandoMotorista.value = false
                mSalvandoMotorista.value = false
            }
        }
    }



    fun atualizar(motorista: Motorista) {
        viewModelScope.launch {
            try {
                mErroAoSalvar.value = null
                mSalvandoMotorista.value = true
                mMotoristaSalvo.value = false
                motoristaRepository.atualizar(motorista)
                mMotoristaSalvo.value = true
            } catch (e: Exception) {
                e.printStackTrace()
                mErroAoSalvar.value = e
            } finally {
                mSalvandoMotorista.value = false
            }
        }
    }

    fun salvar(motorista: Motorista) {
        viewModelScope.launch {
            try {
                mErroAoSalvar.value = null
                mSalvandoMotorista.value = true
                mMotoristaSalvo.value = false
                motoristaRepository.inserir(motorista)
                mMotoristaSalvo.value = true
            } catch (e: Exception) {
                e.printStackTrace()
                mErroAoSalvar.value = e
            } finally {
                mSalvandoMotorista.value = false
            }
        }
    }

    fun excluir() {
        viewModelScope.launch {
            try {
                motorista.value?.let {
                    mErroAoSalvar.value = null
                    mSalvandoMotorista.value = true
                    mMotoristaSalvo.value = false
                    motoristaRepository.excluir(it)
                    mMotoristaSalvo.value = true
                }

            } catch (e: Exception) {
                e.printStackTrace()
                mErroAoSalvar.value = e
            } finally {
                mSalvandoMotorista.value = false
            }
        }
    }

}