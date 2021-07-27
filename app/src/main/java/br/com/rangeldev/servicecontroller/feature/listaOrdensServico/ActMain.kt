package br.com.rangeldev.servicecontroller.feature.listaOrdensServico

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.rangeldev.servicecontroller.R
import br.com.rangeldev.servicecontroller.application.OSApplication

import br.com.rangeldev.servicecontroller.bases.BaseActivity
import br.com.rangeldev.servicecontroller.feature.listaOrdensServico.adapter.OSAdapter
import br.com.rangeldev.servicecontroller.feature.listaOrdensServico.model.OSModel
import br.com.rangeldev.servicecontroller.feature.ordemServico.ActOrdemServico
import kotlinx.android.synthetic.main.act_main.*

import java.lang.Exception

class ActMain : BaseActivity() {
private var adapter : OSAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)
        //setupToolBar(toolBar, "Lista de Serviços",false)
        setupToolBar(false)
        setupListView()
        setupOnClicks()
    }

    private fun setupOnClicks(){
        fab.setOnClickListener { onClickAdd() }
        ivBuscar.setOnClickListener { onClickBuscar() }
    }

    private fun setupListView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        onClickBuscar()
    }

    private fun onClickAdd(){
        val intent = Intent(this,ActOrdemServico::class.java)
        startActivity(intent)
    }

    private fun onClickItemRecyclerView(index: Int){
        val intent = Intent(this,ActOrdemServico::class.java)
        intent.putExtra("index", index)
        startActivity(intent)
    }

    private fun onClickBuscar(){
        val busca = etBuscar.text.toString()
       // progressBar.visibility = View.VISIBLE
        Thread(Runnable {
            Thread.sleep(1000)
            var listaFiltrada: List<OSModel> = mutableListOf()
            try {
                listaFiltrada = OSApplication.instance.helperDB?.searchOS(busca) ?: mutableListOf()
            }catch (ex: Exception){
                println("ERRO DB ----------  @@@ ERRO @@@ ------------  ${ex}")
                ex.printStackTrace()
            }
            runOnUiThread {
                adapter = OSAdapter(this,listaFiltrada) {onClickItemRecyclerView(it)}
                recyclerView.adapter = adapter
              //  progressBar.visibility = View.GONE
                Toast.makeText(this,"Buscando por $busca", Toast.LENGTH_SHORT).show()
            }
        }).start()
    }

}
