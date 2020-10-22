package com.ivocoelho.nomeapp.gerente.view.cadastro

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ivocoelho.nomeapp.data.exttension.formataData
import com.ivocoelho.nomeapp.data.exttension.toDate
import com.ivocoelho.nomeapp.data.util.IdadeAutomatica
import com.ivocoelho.nomeapp.data.util.Mask
import com.ivocoelho.nomeapp.domain.models.Motorista
import com.ivocoelho.nomeapp.gerente.R
import kotlinx.android.synthetic.main.activity_cadastro_motorista.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


class CadastroMotoristaActivity : AppCompatActivity() {

    private val motoristaViewModel by viewModel<CadastroMotoristaViewModel>()
    private var atualizarCadastro = false
    private var motoristaId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_motorista)
        val codigoAtivacao = gerarCodigoAtivacao()
        copiaCodigoDeAtivacao(codigoAtivacao)
        iniciarListeners()

        supportActionBar?.let{
            it.setDisplayHomeAsUpEnabled(true)
            it.setDefaultDisplayHomeAsUpEnabled(true)
        }


        intent.getStringExtra("MOTORISTA_ID")?.let {
            atualizarCadastro = true
            motoristaId = it
            observarBuscandoMotorista()
            observarMotorista()
            motoristaViewModel.buscarMotorista(it)
            Log.i("MOTORISTA ID", it)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_item_cadastro_motorista, menu)
        return true
    }

    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_excluir -> {
            motoristaViewModel.excluir()
            true
        }
        R.id.home -> {
            this.onBackPressed()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }


    private fun observarMotorista() {
        motoristaViewModel.motorista.observe(this, Observer {
            atualizaViewComMotorista(it)
        })
    }

    private fun atualizaViewComMotorista(motorista: Motorista) {
        nome_motorista_edittex.setText(motorista.nome)
        data_nascimento_ediitext.setText(motorista.dataNascimento.formataData())
        idade_edittex.setText(motorista.idade.toString()+" anos")
        ddd_edittext.setText(motorista.ddd.toString())
        celular_edittext.setText(motorista.celular)
        ativo_em_campo_switch.isChecked  = motorista.ativo
    }

    private fun observarSalvando() {
        motoristaViewModel.salvandoMotorista.observe(this, Observer { listando ->
            progressBar_motorista_carregando.visibility =
                if (listando == true)
                    View.VISIBLE
                else
                    View.GONE
        })
    }

    private fun observarBuscandoMotorista() {
        motoristaViewModel.buscandoMotorista.observe(this, Observer { listando ->
            progressBar_motorista_carregando.visibility =
                if (listando == true)
                    View.VISIBLE
                else
                    View.GONE
        })
    }

    private fun observarMotoristaSalvoComSucesso() {
        motoristaViewModel.motoristaSalvo.observe(this, Observer { motoristaSavoComSucesso ->
                if (motoristaSavoComSucesso == true)
                    this.onBackPressed()

        })
    }


    private fun observarErroAoSalvar() {
        motoristaViewModel.erroAoSalvar.observe(this, Observer { erro ->
            if (erro != null) {
                progressBar_motorista_carregando.visibility = View.GONE
                Toast.makeText(this, R.string.erro_ao_salvar_motoristas, Toast.LENGTH_SHORT).show()
            }
        })
    }




    private fun iniciarListeners() {
        salvar_button.setOnClickListener { salvarMotorista() }
        data_nascimento_ediitext.addTextChangedListener(
            Mask().insert(
                "##/##/####",
                data_nascimento_ediitext
            )
        )

        data_nascimento_ediitext.addTextChangedListener(IdadeAutomatica().calcula(idade_edittex))
        codigo_ativacao_edittext.setOnClickListener {
            Toast.makeText(
                this,
                "Código de Ativação copiado",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun getMotorista(motoristaId : String?): Motorista {
        val nome = nome_motorista_edittex.text.toString()
        val dataNascimento = data_nascimento_ediitext.text.toString().toDate()
        val idade = idade_edittex.text.toString().replace(" anos","").toLong()
        val avatar = ""
        val lat = 0.0
        val lng = 0.0
        val ativo = ativo_em_campo_switch.isChecked
        val codigoAtivacao = codigo_ativacao_edittext.text.toString()
        val ddd = ddd_edittext.text.toString().toLong()
        val celular = celular_edittext.text.toString()
        return Motorista(motoristaId, nome, dataNascimento, idade, avatar, lat, lng, ativo, codigoAtivacao, ddd, celular, 0.0)
    }

    private fun copiaCodigoDeAtivacao(codigoAtivacao: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("Código de Ativação", codigoAtivacao)
        clipboard.primaryClip = clip
        codigo_ativacao_edittext.setText(codigoAtivacao)
    }

    private fun salvarMotorista() {
        val camposPreenchidos = validarCamposPreenchidos()
        if (camposPreenchidos) {
            observarSalvando()
            observarErroAoSalvar()
            observarMotoristaSalvoComSucesso()
            val motorista = getMotorista(motoristaId)



            if(atualizarCadastro) {
                val motorista = getMotorista(motoristaId)
                motoristaViewModel.atualizar(motorista)
            }
            else {
                motoristaViewModel.salvar(motorista)
            }
        }
    }

    private fun validarCamposPreenchidos(): Boolean {
        if (nome_motorista_edittex.text.toString() == "") {
            nome_motorista_edittex.error = "Campo obrigatório"
            return false
        }
        if (data_nascimento_ediitext.text.toString() == "") {
            data_nascimento_ediitext.error = "Campo obrigatório"
            return false
        }


        return true
    }

    private fun gerarCodigoAtivacao(): String {

        val dicionario = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLÇZXCVBNM"

        var armazenaChaves = ""
        var index: Int
        for (i in 0..8) {
            index = Random().nextInt(dicionario.length)
            armazenaChaves += dicionario.substring(index, index + 1)
        }
        return armazenaChaves
    }
}
