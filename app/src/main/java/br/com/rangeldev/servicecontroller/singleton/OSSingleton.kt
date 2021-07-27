package br.com.rangeldev.servicecontroller.singleton

import br.com.rangeldev.servicecontroller.feature.listaOrdensServico.model.OSModel

object OSSingleton {
    var listOsModel: MutableList<OSModel> = mutableListOf()
}