package com.ivocoelho.nomeapp.cliente.view.lista

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.ivocoelho.nomeapp.data.mappers.toMotorista
import com.ivocoelho.nomeapp.domain.models.Motorista
import kotlinx.coroutines.launch
import kotlin.math.*

/**
 * @author Ivo Coelho Albuquerque
 * Criado em 20/01/2020
 */

class ClienteViewModel(
    private val db: FirebaseFirestore
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
                db.collection(MOTORISTA_TB)
                    .addSnapshotListener { result, e ->

                        val motoristas = result?.map { it.toMotorista() }
                        motoristas?.forEach {
                            it.apply {
                                distancia = calculaDistancia(
                                    location.latitude,
                                    location.longitude,
                                    it.lat,
                                    it.lng
                                )
                            }
                        }
                        mMotoristas.value = motoristas
                    }

            } catch (e: Exception) {
                e.printStackTrace()
                mErroAoListar.value = e
            } finally {
                mListando.value = false
            }
        }
    }

    private fun calculaDistancia(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {
        //double earthRadius = 3958.75;//miles
        val earthRadius = 6371.0//kilometers
        val dLat = Math.toRadians(lat2 - lat1)
        val dLng = Math.toRadians(lng2 - lng1)
        val sindLat = sin(dLat / 2)
        val sindLng = sin(dLng / 2)
        val a = sindLat.pow(2.0) + (sindLng.pow(2.0)
                * cos(Math.toRadians(lat1))
                * cos(Math.toRadians(lat2)))
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return earthRadius * c


    }

    companion object {
        private const val MOTORISTA_TB = "Motoristas"
    }

}