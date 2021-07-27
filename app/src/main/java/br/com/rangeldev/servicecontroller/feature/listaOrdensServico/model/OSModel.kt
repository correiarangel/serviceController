package br.com.rangeldev.servicecontroller.feature.listaOrdensServico.model

data class OSModel(
    var id :Int = -1,
    var cliente: String = "",
    var descricao: String = "",
    var resulocao: String = "",
    var tecnico: String = "",
    var dataInical: String = "",
    var dataFinal: String = "",
    var valor: Double = 0.0
)
