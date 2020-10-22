package com.ivocoelho.nomeapp.motorista.view.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ivocoelho.nomeapp.motorista.R
import com.ivocoelho.nomeapp.motorista.view.cadastro.CadastroMotoristaActivity
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val loginViewModel by viewModel<LoginViewModel> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setListener()
    }

    private fun setListener() {
//        validar_button.setOnClickListener {
//            validarTokenMotorista()
//            observarMotorista()
//        }
    }

    private fun observarMotorista() {
        loginViewModel.motorista.observe(this, Observer {
            if(it != null)
                goDadosMotorista()
            else
                Toast.makeText(this, "Token inválido", Toast.LENGTH_SHORT).show()
        })
    }

    private fun validarTokenMotorista() {
//        val codigo = codigo_ativacao_editText.text.toString()
//        loginViewModel.buscarMotoristaPorCodigoAtivacao(codigo)
    }

    private fun goDadosMotorista() {
        startActivity(Intent(this, CadastroMotoristaActivity::class.java)
            .apply { putExtra("MOTORISTA_ID", loginViewModel.motorista.value?.id) })
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
