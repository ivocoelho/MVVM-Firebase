package com.ivocoelho.nomeapp.gerente.view.lista

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.ivocoelho.nomeapp.domain.models.Motorista
import com.ivocoelho.nomeapp.gerente.R
import com.ivocoelho.nomeapp.gerente.view.cadastro.CadastroMotoristaActivity
import kotlinx.android.synthetic.main.activity_gerente.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * @author Ivo Coelho Albuquerque
 * Criado em 20/01/2020
 */

class GerenteActivity : AppCompatActivity(), NaInteracaoComAListaDeMotoristas {

    private val gerenteViewModel by viewModel<GerenteViewModel>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gerente)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }


    override fun onResume() {
        obterLocalizacao()
        observarMotoristas()
        observarListando()
        observarErroAoListar()
        configurarFab()
        super.onResume()
    }

    private fun configurarFab() {
        fab_adicionar_motorista.setOnClickListener {
            startActivity(Intent(this, CadastroMotoristaActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        }
    }

    private fun obterLocalizacao() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Sem permissão para GPS", Toast.LENGTH_SHORT).show()
            obterPermissoGps()
        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let { listarMotoristas(it) }
                }
        }
    }

    private fun obterPermissoGps() {
        // Permission is not granted
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(this, "Libera aí", Toast.LENGTH_LONG).show()
            requisitaPermissao()
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
        } else {
            // No explanation needed, we can request the permission.
            requisitaPermissao()
        }
    }

    private fun requisitaPermissao() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            MY_PERMISSIONS_REQUEST_FINE_LOCATION
        )
    }

    private fun listarMotoristas(location: Location) {
        gerenteViewModel.listar(location)
    }

    private fun observarMotoristas() {
        gerenteViewModel.motoristas.observe(this, Observer {
            recyclerView_listar_motoristas.adapter = if (it.isNullOrEmpty()) {
                null
            } else {
                MotoristaListaAdapter(motoristas = it, listenerLista = this)
            }
        })
    }

    private fun observarListando() {
        gerenteViewModel.listando.observe(this, Observer { listando ->
            progressBar_issues_carregando.visibility =
                if (listando == true) View.VISIBLE else View.GONE
            recyclerView_listar_motoristas?.visibility =
                if (listando == true || gerenteViewModel.motoristas.value.isNullOrEmpty()
                ) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
        })
    }

    private fun observarErroAoListar() {
        gerenteViewModel.erroAoListar.observe(this, Observer { erro ->
            if (erro != null) {
                progressBar_issues_carregando.visibility = View.GONE
                recyclerView_listar_motoristas.visibility = View.GONE
                text_view_nenhuma_issue_encontrada.visibility = View.VISIBLE
                Toast.makeText(this, R.string.erro_ao_listar_motoristas, Toast.LENGTH_SHORT).show()
            } else {
                recyclerView_listar_motoristas.visibility = View.VISIBLE
            }
        })
    }

    override fun aoSelecionarMotorista(motorista: Motorista) {
        startActivity(Intent(this, CadastroMotoristaActivity::class.java)
            .apply { putExtra("MOTORISTA_ID", motorista.id) })
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    companion object const {
        val MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1
    }

}
