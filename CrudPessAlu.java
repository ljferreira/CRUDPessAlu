package br.com.ferreira.crudpessalu;

import java.util.Scanner;
import br.com.ferreira.crudpessalu.view.Util;
import br.com.ferreira.crudpessalu.view.Tela;



public class CrudPessAlu{

	public static void main(String[] args){

		while(true){
		
			Util.limpaTela();
			String[] opMenu = new String[]{"*** Selecione uma op��o do menu ***\n", //T�tulo do Menu
			                               "\t1. Cadastrar\n\t2. Listar\n\t3. Atualizar\n\t4. Excluir\n\t5. Sair\n\n", //Op��es do Menu
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
						 
				case 5:  System.exit(1);

			}
		}
 
	}
}