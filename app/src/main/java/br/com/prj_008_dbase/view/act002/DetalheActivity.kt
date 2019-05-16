package br.com.prj_008_dbase.view.act002

import android.app.AlertDialog
import android.app.ListActivity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import br.com.prj_008_dbase.R
import br.com.prj_008_dbase.dao.ContatoDao
import br.com.prj_008_dbase.extensions.toDB
import br.com.prj_008_dbase.model.Contato
import br.com.prj_008_dbase.view.act001.ListaActivity
import kotlinx.android.synthetic.main.activity_detalhe.*

class DetalheActivity : AppCompatActivity() {

    private lateinit var context: Context
    private lateinit var contatoDao: ContatoDao

    private var codigoAtual: Long = 0L

    private var mensagem = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe)

        initVars()
        initActions()
    }

    private fun initVars() {
        context = this@DetalheActivity
        contatoDao = ContatoDao(context)

        recuperarParametros()

        if (codigoAtual == -1L) {
            detalhe_btn_excluir.visibility = View.GONE
        } else {
            var cAux = contatoDao.obterContatoByID(codigoAtual)

            detalhe_et_codigo.setText(cAux?.idcontato.toString())
            detalhe_et_nome.setText(cAux?.nome)
            detalhe_et_telefone.setText(cAux?.telefone)
            detalhe_et_idade.setText(cAux?.idade.toString())
        }

        detalhe_et_nome.requestFocus()
    }

    private fun initActions() {
        detalhe_btn_excluir.setOnClickListener {
            contatoDao.excluirContato(codigoAtual)

            chamarLista()
        }

        detalhe_btn_salvar.setOnClickListener {
            if (validacao()) {
                salvar()
            } else {
                exibirMensagem(mensagem)
            }
        }
    }

    private fun validacao(): Boolean {
        var nome = detalhe_et_nome.text.toString().trim()
        var telefone = detalhe_et_telefone.text.toString().trim()
        var idade = detalhe_et_idade.text.toString().trim()

        if (nome.isEmpty()) {
            mensagem = "Erro. Nome é Obrigatorio"

            return false
        }

        if (telefone.isEmpty()) {
            mensagem = "Erro. Telefone é Obrigatorio"

            return false
        }

        if (idade.isEmpty()) {
            mensagem = "Erro. Idade é Obrigatoria"

            return false
        }

        return true
    }

    private fun salvar() {

        var cAux = Contato()
        cAux.nome = detalhe_et_nome.text.toString().toDB()
        cAux.telefone = detalhe_et_telefone.text.toString().toDB()
        cAux.idade = detalhe_et_idade.text.toString().toInt()

        if (codigoAtual != -1L) {
            cAux.idcontato = codigoAtual

            contatoDao.alterarContato(cAux)
        } else {
            codigoAtual = contatoDao.proximoID()
            cAux.idcontato = codigoAtual

            contatoDao.inserirContato(cAux)

            detalhe_et_codigo.setText(cAux.idcontato.toString())
            detalhe_btn_excluir.visibility = View.VISIBLE
        }
    }

    private fun exibirMensagem(mensagem: String) {
        Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show()
    }

    companion object {


        const val PARAMETRO_IDCONTATO = "parametro_idcontato"
    }

    private fun recuperarParametros() {
        codigoAtual = intent.getLongExtra(PARAMETRO_IDCONTATO, 0)
    }

    override fun onBackPressed() {
        var alerta = AlertDialog.Builder(context)

        alerta.setTitle("Sair do Cadastro")
        alerta.setMessage("Deseja realmente Sair?")
        alerta.setPositiveButton("Sim", DialogInterface.OnClickListener { dialog, which ->
            chamarLista()
        })
        alerta.setNegativeButton("Nao", null)
        alerta.setCancelable(false)

        alerta.show()
    }

    private fun chamarLista() {
        var mIntent = Intent(
            this@DetalheActivity,
            ListaActivity::class.java
        )

        startActivity(mIntent)

        finish()
    }

}