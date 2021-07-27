package br.com.rangeldev.servicecontroller.feature.ordemServico

import android.os.Bundle
import android.util.Log
import android.view.View
import br.com.rangeldev.servicecontroller.R
import br.com.rangeldev.servicecontroller.application.OSApplication
import br.com.rangeldev.servicecontroller.bases.BaseActivity
import br.com.rangeldev.servicecontroller.feature.listaOrdensServico.model.OSModel
import kotlinx.android.synthetic.main.act_ordem_servico.*


class ActOrdemServico : BaseActivity() {

    private var idOS:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_ordem_servico)

       // setupToolBar(toolBar, "Ordem Serviço",true)
        setupToolBar(true)
        setupOS()
        btnSave.setOnClickListener{onClickSevaOS()}

    }

    private fun setupOS(){
        idOS = intent.getIntExtra("index",-1)
        if (idOS == -1){
            btnExcluir.visibility = View.GONE
            return
        }
       // progressBar.visibility = View.VISIBLE
        Thread(Runnable {
            Thread.sleep(1000)
            var lista = OSApplication.instance.helperDB?.searchOS("$idOS",true)?:return@Runnable

            var os = lista.getOrNull(0)?:return@Runnable
            runOnUiThread{
                edtClient.setText(os.cliente)
                edtDesc.setText(os.descricao)
                edtResolotion.setText(os.resulocao)
                edtTecnico.setText(os.tecnico)
                edtDateStatart.setText(os.dataInical)
                edtDateEnd.setText(os.dataFinal)
                edtValue.setText(os.valor.toString())
              //  progressBar.visibility = View.GONE
            }
        }).start()
    }

    private fun  onClickSevaOS(){

        val client = edtClient.text.toString()
        val desc = edtDesc.text.toString()
        val resolotion =  edtResolotion.text.toString()
        val tec = edtTecnico.text.toString()
        val dateStart = edtDateStatart.text.toString()
        val dateEnd =  edtDateEnd.text.toString()
        val value = edtValue.text.toString()

        val osModel = OSModel(
            idOS,
            client,
            desc,
            resolotion,
            tec,
            dateStart,
            dateEnd,
            value.toDouble()
        )

       // progressBar.visibility = View.VISIBLE
        Thread(Runnable {
            Thread.sleep(1000)
            if(idOS == -1){
                try {
                    OSApplication.instance.helperDB?.saveOS(osModel)
                }catch (err:Exception){
                    Log.e("ERRO SALVAR","$err" )
                    println(("ERRO SALVAR -- $err" ))
                }

            }else{
                OSApplication.instance.helperDB?.updateOS(osModel)
            }
            runOnUiThread {
               // progressBar.visibility = View.GONE
                finish()
            }
        }).start()
    }

    fun onClickExcluir(view:View){
        if(idOS > -1){
            //progressBar.visibility = View.VISIBLE
            Thread(Runnable {
                Thread.sleep(1000)
                OSApplication.instance.helperDB?.deleteOS(idOS)
                runOnUiThread {
                    //progressBar.visibility = View.GONE
                    finish()
                }
            }).start()
        }
    }

}