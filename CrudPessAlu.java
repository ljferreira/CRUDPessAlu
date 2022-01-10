/**
 * aplicação CRUD +praTi
 * 
 * Menu Principal
 * 
 *@author  Luciano J. Ferreira
 *@version 1.00 12/2021 
 */

package br.com.ferreira.crudpessalu;

import java.util.Scanner;
import br.com.ferreira.crudpessalu.view.Util;
import br.com.ferreira.crudpessalu.view.Tela;



public class CrudPessAlu{

    public static void main(String[] args){

        while(true){
        
            Util.limpaTela();
            
            //Título do Menu  e suas opções, o método estático logo retorna uma representação gráfica baseada em caracteres ASCII
            String[] opMenu = new String[]{"\n\n" + Util.logo(7, false) + "\n" +
                                           "    **********************************************\n" + 
                                           "    **   Digite um número de 1 a 5 e <enter>,   **\n" + 
                                           "    **    para selecionar uma opção do menu     **\n" + 
                                           "    **********************************************", 
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
                                           "    +--------------------------------------------+\n", 
                                           "1", "2", "3", "4", "5"}; //chaves de escolha do menu
            
            Integer opcao = Integer.parseInt(Util.menu(opMenu));

            switch(opcao){
            
                case 1:  Tela.cadastro();
                         break;

                case 2:  Tela.listagem();
                         break;

                case 3:  Tela.atualizacao();
                         break;

                case 4:  Tela.exclusao();
                         break;
                         
                case 5:  System.exit(0);

            }
            
        }
 
    }
}