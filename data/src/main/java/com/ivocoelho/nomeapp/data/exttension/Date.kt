package com.ivocoelho.nomeapp.data.exttension

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author Ivo Coelho Albuquerque
 * Criado em 21/01/2020
 */


fun Date.calcularIdade(): Long{
    val dataNascimento = Calendar.getInstance()
    val dataAtual = Calendar.getInstance()
    dataNascimento.time = this
    dataAtual.time = Date()
    val dif = dataAtual.timeInMillis - dataNascimento.timeInMillis
    val dias = TimeUnit.MILLISECONDS.toDays(dif)

return (dias/365)
}


fun Date.formataData(): String{
    val locale = Locale("pt", "BR")
    val dateFormat = SimpleDateFormat("dd/MM/yyyy" , locale)
    return dateFormat.format(this)
}
