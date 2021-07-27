package br.com.rangeldev.servicecontroller.feature.listaOrdensServico.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.rangeldev.servicecontroller.R
import br.com.rangeldev.servicecontroller.feature.listaOrdensServico.model.OSModel
import kotlinx.android.synthetic.main.item_os.view.*

class OSAdapter (
    private val context: Context,
    private val listaOsM: List<OSModel>,
    private val onClick: ((Int) -> Unit)
) :RecyclerView.Adapter<OSModelViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OSModelViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_os,parent,false)
        return OSModelViewHolder(view)
    }

    override fun getItemCount(): Int  = listaOsM.size

    override fun onBindViewHolder(holder: OSModelViewHolder, position: Int) {
        val osModel = listaOsM[position]
        with(holder.itemView){
            txtClient.text =  osModel.cliente
            txtDescripition.text = osModel.descricao
            txtResolotion.text = osModel.resulocao
            txtTecnico.text = osModel.tecnico
            txtDateStart.text = osModel.dataInical
            txtDateEnd.text = osModel.dataFinal
            txtValue.text = osModel.valor.toString()

            llItem.setOnClickListener { onClick(osModel.id) }
        }
    }

}

class OSModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)