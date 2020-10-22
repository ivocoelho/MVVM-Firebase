package com.ivocoelho.nomeapp.data.repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.ivocoelho.nomeapp.data.mappers.toHashmap
import com.ivocoelho.nomeapp.data.mappers.toMotorista
import com.ivocoelho.nomeapp.domain.models.Motorista
import com.ivocoelho.nomeapp.domain.repositories.MotoristaRepository
import kotlinx.coroutines.tasks.await
import kotlin.math.*

/**
 * @author Ivo Coelho Albuquerque
 * Criado em 10/01/2019
 */

class MotoristaRepositoryImpl(
    private val db: FirebaseFirestore
) : MotoristaRepository {


    override suspend fun listar(latitude: Double, longitude: Double): List<Motorista> {
        val motoristas = db.collection(MOTORISTA_TB)
            .get().await().map { it.toMotorista() }


        motoristas.forEach {
            it.apply {
                distancia = calculaDistancia(
                    latitude,
                    longitude,
                    it.lat,
                    it.lng
                )
            }
        }

        return motoristas

    }

    override suspend fun atualizar(motorista: Motorista) {
        db.collection(MOTORISTA_TB)
            .document(motorista.id!!)
            .set(motorista.toHashmap())
            .await()
    }

    override suspend fun excluir(motorista: Motorista) {
        db.collection(MOTORISTA_TB)
            .document(motorista.id!!)
            .delete()
            .await()
    }

    override suspend fun inserir(motorista: Motorista) {

        db.collection(MOTORISTA_TB)
            .add(motorista.toHashmap())
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }


    override suspend fun buscarMotorista(motoristaId: String): Motorista {
        val retorno = db.collection(MOTORISTA_TB).document(motoristaId).get().await()
        return retorno.toMotorista()
    }


   override suspend fun buscarMotoristaPorAtivacao(codigoAtivacao: String): Motorista? {
        val retorno = db.collection(MOTORISTA_TB).whereEqualTo("codigoAtivacao", codigoAtivacao)
            .get().await()
        for (document in retorno) {
           return document.toMotorista()
        }
        return null
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