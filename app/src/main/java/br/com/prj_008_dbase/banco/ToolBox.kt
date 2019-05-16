package br.com.prj_008_dbase.banco

import java.lang.Exception

object Constantes {

    // Banco de Dados
    val BANCO = "impacta.db3"
    val VERSAO = 1

}

fun converterIdade(idade: String): Int {
    try {
        var _idade = idade.toInt()

        if (_idade >= 5) {
            return _idade

        } else {
            return -1
        }
    } catch (e: Exception) {
        return -1
    }
}