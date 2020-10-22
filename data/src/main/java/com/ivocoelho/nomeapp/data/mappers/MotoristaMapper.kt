package com.ivocoelho.nomeapp.data.mappers

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.GeoPoint
import com.ivocoelho.nomeapp.data.exttension.calcularIdade
import com.ivocoelho.nomeapp.domain.models.Motorista

/**
 * @author Ivo Coelho Albuquerque
 * Criado em 20/01/2020
 */

fun DocumentSnapshot.toMotorista() = Motorista(
    id = id,
    nome = getString("nome")!!,
    dataNascimento = getDate("dataNascimento")!!,
    idade = getDate("dataNascimento")!!.calcularIdade(),
    avatar = getString("avatar"),
    lat = getGeoPoint("localizacao")!!.latitude,
    lng = getGeoPoint("localizacao")!!.longitude,
    ativo = getBoolean("ativo")!!,
    codigoAtivacao = getString("codigoAtivacao")!!,
    ddd = getLong("ddd")!!,
    celular = getString("celular")!!,
    distancia = 0.0
)


fun Motorista.toHashmap() =  hashMapOf(
    "nome" to nome,
    "dataNascimento" to dataNascimento,
    "avatar" to avatar,
    "localizacao" to GeoPoint(lat,lng),
    "lng" to lng,
    "ativo" to ativo,
    "codigoAtivacao" to codigoAtivacao,
    "ddd" to ddd,
    "celular" to celular
)