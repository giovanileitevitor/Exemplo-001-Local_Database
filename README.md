# Exemplo-001-Local_Database
Exemplo de Estrutura de armazenamento de dados em BD Local com SQLite

Estrutura no padrão MVC.

Packages: 
- BANCO
- DAO
- EXTENSIONS
- MODEL
- VIEW
	
Detalhes:
- O aplicativo possui duas telas (activities) sendo elas ListaActivity e DetalheActivity.
- O aplicativo faz uso do simpleAdapter.

Trecho de código do SimpleAdapter:
//Inicializa o Simple adapter passando os parametros básicos
        main_lv_contatos.adapter = SimpleAdapter(
            context,
            contatoDao.obterListaContatos(),
            android.R.layout.simple_list_item_1,
            De,
            Para
        )


Qualquer dúvida, fique a vontade para mandar email para 
giovanileitevitor@gmail.com

Desenvolvedor Android Java/Kotlin


	







