package com.ivocoelho.nomeapp.gerente.view.lista

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ivocoelho.nomeapp.domain.models.Motorista
import com.ivocoelho.nomeapp.gerente.R


/**
 * @author Thiago Barbosa de Oliveira
 * Criado em 10/01/2019
 */

class MotoristaListaAdapter(
    private val motoristas: List<Motorista>,
    private val listenerLista: NaInteracaoComAListaDeMotoristas
) : RecyclerView.Adapter<MotoristaListaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MotoristaListaViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.listar_motoristas_adapter_item,
                parent,
                false
            )
        )

    override fun getItemCount() = motoristas.size

    override fun onBindViewHolder(holder: MotoristaListaViewHolder, position: Int) {
        val motorista = motoristas[position]
        holder.bind(motorista)
        holder.itemView.setOnClickListener { selecionarMototista(motorista) }
    }

    private fun selecionarMototista(motorista: Motorista) {
        listenerLista.aoSelecionarMotorista(motorista)
    }
}