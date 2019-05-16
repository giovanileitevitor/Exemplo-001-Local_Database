package br.com.prj_008_dbase.extensions

fun String.toDB(): String {
    return this.trim().toLowerCase()
}