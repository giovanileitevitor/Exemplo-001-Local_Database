package br.com.prj_008_dbase.model

data class Contato(
    var idcontato: Long = -1L,
    var nome: String = "",
    var telefone: String = "",
    var idade: Int = -1
)