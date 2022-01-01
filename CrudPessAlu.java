package br.com.ferreira.crudpessalu;

import java.util.Scanner;
import br.com.ferreira.crudpessalu.view.Util;
import br.com.ferreira.crudpessalu.view.Tela;



public class CrudPessAlu{

	public static void main(String[] args){

		while(true){
		
			Util.limpaTela();
			/*String[] opMenu = new String[]{"*** Selecione uma opção do menu ***\n", //Título do Menu
			                               "\t1. Cadastrar\n\t2. Listar\n\t3. Atualizar\n\t4. Excluir\n\t5. Sair\n\n", //Opções do Menu
			                               "1", "2", "3", "4", "5"}; //chaves de escolha do menu*/

			String[] opMenu = new String[]{"\n\n" + Util.logo(7, false) + "\n" +
				                           "    **********************************************\n" + 
			                               "    **   Digite um número de 1 a 5 e <enter>,   **\n" + 
			                               "    **    para selecionar uma opção do menu     **\n" + 
			                               "    **********************************************", //Título do Menu
			                               "    +--------------------------------------------+\n"+
			                               "    |                1. Cadastrar                |\n" + 
			                               "    |                                            |\n" + 
			                               "    |                2. Listar                   |\n" + 
			                               "    |                                            |\n" + 
			                               "    |                3. Atualizar                |\n" + 
			                               "    |                                            |\n" + 
			                               "    |                4. Excluir                  |\n" + 
			                               "    |                                            |\n" + 
			                               "    |                5. Sair                     |\n" + 
			                               "    +--------------------------------------------+\n", //Opções do Menu
			                               "1", "2", "3", "4", "5"}; //chaves de escolha do menu
			
			Integer opcao = Integer.parseInt(Util.menu(opMenu));

			switch(opcao){
			
				case 1:  Tela.cadastro();
						 break;

				case 2:  Tela.listagem();
						 break;

				case 3:  Tela.atualizacao();
						 break;

				case 4:	 Tela.exclusao();
						 break;
						 
				case 5:  System.exit(0);

			}
			
		}
 
	}
}