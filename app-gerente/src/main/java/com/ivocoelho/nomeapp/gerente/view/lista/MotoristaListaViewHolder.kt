package com.ivocoelho.nomeapp.gerente.view.lista

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ivocoelho.nomeapp.domain.models.Motorista
import kotlinx.android.synthetic.main.listar_motoristas_adapter_item.view.*

/**
 * @author Thiago Barbosa de Oliveira
 * Criado em 10/01/2019
 */


class MotoristaListaViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(motorista: Motorista) {
        itemView.distancia_textview.text = "%.2f".format(motorista.distancia) +" km"
        itemView.textView_nome_motorista.text = motorista.nome
        if (motorista.ativo)
            itemView.status_motorista.text = "ONLINE"
        else
            itemView.status_motorista.text = "OFFLINE"
    }

}