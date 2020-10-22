package com.ivocoelho.nomeapp.domain.repositories

import com.ivocoelho.nomeapp.domain.models.Motorista


interface MotoristaRepository {

    suspend fun listar(latitude: Double, longitude: Double): List<Motorista>
    suspend fun atualizar(motorista: Motorista)
    suspend fun excluir(motorista: Motorista)
    suspend fun inserir(motorista: Motorista)
    suspend fun buscarMotorista(motoristaId: String): Motorista
    suspend fun buscarMotoristaPorAtivacao(codigoAtivacao: String): Motorista?
}