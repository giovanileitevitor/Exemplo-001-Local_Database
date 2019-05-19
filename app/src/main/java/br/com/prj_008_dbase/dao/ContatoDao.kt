package br.com.prj_008_dbase.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import br.com.prj_008_dbase.banco.Dao
import br.com.prj_008_dbase.banco.HMAux
import br.com.prj_008_dbase.model.Contato
import java.lang.Exception

class ContatoDao(
    context: Context
) : Dao(context) {

    companion object {
        val TABELA = "contatos"
        val IDCONTATO = "idcontato"
        val NOME = "nome"
        val TELEFONE = "telefone"
        val IDADE = "idade"
    }

    //Função para inserir contatos no banco
    fun inserirContato(contato: Contato) {
        abrirBanco()

        val cv = ContentValues()

        cv.put(IDCONTATO, contato.idcontato)
        cv.put(NOME, contato.nome)
        cv.put(TELEFONE, contato.telefone)
        cv.put(IDADE, contato.idade)

        db?.insert(TABELA, null, cv)

        fecharBanco()
    }

    //Função para editar um contato apartir do contato
    fun alterarContato(contato: Contato) {
        abrirBanco()

        val cv = ContentValues()

        var filtro = "$IDCONTATO = ? "
        var argumentos = arrayOf(contato.idcontato.toString())

        //cv.put(IDCONTATO, contato.idcontato)
        cv.put(NOME, contato.nome)
        cv.put(TELEFONE, contato.telefone)
        cv.put(IDADE, contato.idade)

        db?.update(TABELA, cv, filtro, argumentos)

        fecharBanco()
    }

    //Função para excluir um contato apartir do ID
    fun excluirContato(idcontato: Long) {
        abrirBanco()

        var filtro = "$IDCONTATO = ? "
        var argumentos = arrayOf(idcontato.toString())

        db?.delete(TABELA, filtro, argumentos)

        fecharBanco()
    }

    //função para Obter o contato apartir do ID do ListView
    fun obterContatoByID(idcontato: Long): Contato? {
        var cAux: Contato? = null

        abrirBanco()

        var cursor: Cursor? = null

        try {

            var comando = " select * from $TABELA where $IDCONTATO = ? " // sql
            var argumentos = arrayOf(idcontato.toString())

            cursor = db?.rawQuery(comando, argumentos)

            while (cursor!!.moveToNext()) {
                cAux = Contato()
                cAux.idcontato = cursor.getLong(cursor.getColumnIndex(IDCONTATO))
                cAux.nome = cursor.getString(cursor.getColumnIndex(NOME))
                cAux.telefone = cursor.getString(cursor.getColumnIndex(TELEFONE))
                cAux.idade = cursor.getInt(cursor.getColumnIndex(IDADE))
            }

            cursor.close()

        } catch (ex: Exception) {
        }

        fecharBanco()

        return cAux
    }

    //Função para obter a Lista de contatos
    fun obterListaContatos(): ArrayList<HMAux> {
        var contatos = ArrayList<HMAux>()

        abrirBanco()

        var cursor: Cursor? = null

        try {

            var comando = " select * from $TABELA order by nome " // sql

            cursor = db?.rawQuery(comando, null)

            while (cursor!!.moveToNext()) {
                var hmAux = HMAux()

                hmAux[HMAux.ID] = cursor.getString(cursor.getColumnIndex(IDCONTATO))
                hmAux[HMAux.NOME] = cursor.getString(cursor.getColumnIndex(NOME))

                contatos.add(hmAux)
            }

            cursor.close()

        } catch (ex: Exception) {
        }

        fecharBanco()

        return contatos
    }

    //Função para pegar o proximo ID válido
    fun proximoID(): Long {
        var proID: Long = -1L

        abrirBanco()

        var cursor: Cursor? = null

        try {

            var comando = " select ifnull(max($IDCONTATO) + 1, 1) as id from $TABELA " // sql

            cursor = db?.rawQuery(comando, null)

            while (cursor!!.moveToNext()) {
                proID = cursor.getLong(cursor.getColumnIndex("id"))
            }

            cursor.close()

        } catch (ex: Exception) {
        }

        fecharBanco()

        return proID
    }
}