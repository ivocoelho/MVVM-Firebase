package com.ivocoelho.nomeapp.domain.models

import java.io.Serializable
import java.util.*

/**
 * @author Ivo Coelho Albuquerque
 * Criado em 20/01/2020
 */
data class Motorista (
    val id: String?,
    val nome: String,
    val dataNascimento: Date,
    val idade : Long?,
    val avatar: String?,
    val lat: Double,
    val lng: Double,
    val ativo: Boolean,
    val codigoAtivacao: String,
    val ddd: Long,
    val celular: String,
    var distancia: Double
): Serializable