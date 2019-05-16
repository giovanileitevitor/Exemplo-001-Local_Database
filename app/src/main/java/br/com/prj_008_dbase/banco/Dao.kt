package br.com.prj_008_dbase.banco

import android.content.Context
import android.database.sqlite.SQLiteDatabase

open class Dao(
    private val context: Context
) {

    protected var db: SQLiteDatabase? = null

    fun abrirBanco() {
        var dbHelper = SQLiteHelper(
            context,
            Constantes.BANCO,
            null,
            Constantes.VERSAO
        )

        this.db = dbHelper.writableDatabase
    }

    fun fecharBanco() {
        db?.let { banco ->
            if (banco.isOpen) {
                banco.close()
            }
        }
    }
}