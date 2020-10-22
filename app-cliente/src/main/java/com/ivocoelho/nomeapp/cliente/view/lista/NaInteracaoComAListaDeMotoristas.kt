package com.ivocoelho.nomeapp.cliente.view.lista

import com.ivocoelho.nomeapp.domain.models.Motorista

/**
 * @author Ivo Coelho Albuquerque - D001378
 * Criado em 17/07/2019
 * Atualizado em 17/07/2019
 */


interface NaInteracaoComAListaDeMotoristas {
    fun aoSelecionarMotorista(motorista: Motorista)
}