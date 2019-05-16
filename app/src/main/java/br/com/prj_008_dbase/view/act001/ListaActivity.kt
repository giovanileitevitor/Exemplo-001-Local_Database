package br.com.prj_008_dbase.view.act001

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.SimpleAdapter
import br.com.prj_008_dbase.R
import br.com.prj_008_dbase.banco.Dao
import br.com.prj_008_dbase.banco.HMAux
import br.com.prj_008_dbase.dao.ContatoDao
import br.com.prj_008_dbase.model.Contato
import br.com.prj_008_dbase.view.act002.DetalheActivity

import kotlinx.android.synthetic.main.activity_lista.*
import kotlinx.android.synthetic.main.content_lista.*
import java.lang.StringBuilder

class ListaActivity : AppCompatActivity() {

    private lateinit var context: Context
    private lateinit var contatoDao: ContatoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)

        setSupportActionBar(toolbar)

        initVars()
        initActions()
    }

    private fun initVars() {
        context = this@ListaActivity
        contatoDao = ContatoDao(context)

        val De = arrayOf(HMAux.NOME) // onde esta a informacao
        val Para = intArrayOf(android.R.id.text1) // onde deve ser colocada a informacao

        main_lv_contatos.adapter = SimpleAdapter(
            context,
            contatoDao.obterListaContatos(),
            android.R.layout.simple_list_item_1,
            De,
            Para
        )
    }

    private fun initActions() {
        main_lv_contatos.setOnItemClickListener { parent, view, position, id ->

            var item = parent.getItemAtPosition(position) as HMAux

            chamarTelaDetalhes(item[HMAux.ID]!!.toLong())
        }
    }

    fun chamarTelaDetalhes(idcontato: Long){
        var mIntent = Intent(
            context,
            DetalheActivity::class.java
        )

        mIntent.putExtra(
            DetalheActivity.PARAMETRO_IDCONTATO,
            idcontato
        )

        startActivity(mIntent)

        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_new_contact -> {

                chamarTelaDetalhes(-1L)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
