package com.ivocoelho.nomeapp.data.exttension

import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Ivo Coelho Albuquerque
 * Criado em 27/01/2020
 */


fun String.toDate() : Date {
    return  SimpleDateFormat("dd/MM/yyyy").parse(this)

}