/**
 * aplica��o CRUD +praTi
 * 
 * Classe Tela, respons�vel pela parte visual 
 * de cadastro, listagem, atualiza��o e exclus�o.
 * 
 *@author  Luciano J. Ferreira
 *@version 1.00 12/2021 
 */



package br.com.ferreira.crudpessalu.view;

import java.util.Scanner;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import br.com.ferreira.crudpessalu.model.*;
import br.com.ferreira.crudpessalu.controller.CtrlDado;


public class Tela{

    private static final Integer  pessAluIndefinido = -1;
    private static final Integer  aluno             =  0;
    private static final Integer  naoAluno          =  1;
    private static       Object[] botoesConfirm     = {"Sim", "N�o"};
    

    
    //C�digo respons�vel pela tela de cadastro de Pessoa/Aluno
    //e direcionamento para o cadastro em mem�ria num ArrayList.

    public static Boolean cadastro(){

        while(true){

            Integer  confirmaCadastro  = -1;
            Integer  continuarCadastro = -1;
            Integer  pessoaAluno       = -1;
            
            String[] nome      = new String[]{"Informe o nome:",                                     "Cadastro Pessoa/Aluno(a)", "nm", null};
            String[] fone      = new String[]{"Qual o telefone? (dd)nnnn-nnnn ou (dd)nnnnn-nnnn:",   "Cadastro Pessoa/Aluno(a)", "fn", null};
            String[] dtNasc    = new String[]{"Data de nascimento dd/mm/aaaa ou dd-mm-aaaa:",        "Cadastro Pessoa/Aluno(a)", "dt", null};
            String[] notaFinal = new String[]{"Nota Final (0 a 100):",                               "Cadastro Pessoa/Aluno(a)", "nt", null};                                    
            
            Util.limpaTela();

            System.out.println("*** Cadastro de Pessoa / Aluno ***\n");

            if(Util.processaDados(nome))   System.out.println("Nome: "               + nome[3]);   else return false;
            if(Util.processaDados(fone))   System.out.println("Telefone: "           + fone[3]);   else return false;
            if(Util.processaDados(dtNasc)) System.out.println("Data de nascimento: " + dtNasc[3]); else return false;
                    
            while(pessoaAluno == pessAluIndefinido){
                pessoaAluno = JOptionPane.showOptionDialog(null, "A pessoa sendo cadastrada � aluno(a)? [enter]=Sim", 
                                                                 "Confirma Aluno(a)", JOptionPane.YES_NO_OPTION, 
                                                                 JOptionPane.QUESTION_MESSAGE, null, botoesConfirm, 
                                                                 botoesConfirm[0]);
            }

            if(pessoaAluno == aluno){
                if(Util.processaDados(notaFinal)) System.out.println("Nota final: " + Util.trimNum(notaFinal[3]).replace(".", ","));
            }

            while(confirmaCadastro == -1){
                confirmaCadastro = JOptionPane.showOptionDialog(null, "Confirma gera��o do  cadastro com os dados atuais? [enter]=Sim", 
                                                                      "Confirma Cadastro", JOptionPane.YES_NO_OPTION, 
                                                                      JOptionPane.QUESTION_MESSAGE, null, botoesConfirm, 
                                                                      botoesConfirm[0]);
                if(confirmaCadastro == 0){
                    break;
                }
                else if(confirmaCadastro == 1){
                    JOptionPane.showMessageDialog(null, "CADASTRO CANCELADO", 
                                                              "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
            }
            
            String[] dadosCadastro = {nome[3], fone[3], dtNasc[3], Util.dataParaTxt(new Date()), 
                                      Util.dataParaTxt(new Date()), notaFinal[3]};

            CtrlDado.cadastraPessoa(dadosCadastro);

            JOptionPane.showMessageDialog(null, "CADASTRO CONCLU�DO", 
                                                              "Aviso", JOptionPane.INFORMATION_MESSAGE);

            while(continuarCadastro == -1){
                continuarCadastro = JOptionPane.showOptionDialog(null, "Continuar cadastrando? [enter]=Sim", 
                                                                       "Continuar Cadastro", JOptionPane.YES_NO_OPTION, 
                                                                       JOptionPane.QUESTION_MESSAGE, null, botoesConfirm, 
                                                                       botoesConfirm[0]);
            }

            if(continuarCadastro == 1)break;

        }

        return true;

    }


    

    //C�digo respons�vel pela tela de listagem de Pessoa/Aluno.
    //Controla o tipo de listagem, se em bloco ou em tabela, 
    //pagina��o dos blocos e tabelas, e aciona o m�todo respons�vel 
    //por op��es de pesquisa.

    public static void listagem(){
        
        Scanner           entrada      = new Scanner(System.in);
        List<Pessoa>      listaPess;
                          listaPess    = CtrlDado.listaPessoa();
        Boolean           lstPessNull  = (listaPess.size() == 0) ? true : false;

        String            rodapeMSG    = "\n\n<-- <Enter>: Menu Principal | <B>: Lista Blocos | <T>: Lista Tabela | <S>: P�g. Anterior | <D>: Pr�xima P�g. | <P>: Pesquisa -->";
        String            opEscolhida  = "";
        Character         tipoPagina   = 'B'; //B --> Bloco ou T --> Tabela
        Integer           quantObjPag  = 5;   // 5 para Bloco ou 36 para Tabela

        Integer           totalPagina  = (listaPess.size() % quantObjPag == 0) 
                                         ? (listaPess.size() / quantObjPag) : (listaPess.size() / quantObjPag + 1);
        Integer           paginaAtual  = 1;
        Integer           pontPosArray = (paginaAtual - 1) * quantObjPag;
        
        
        while(true){

            Util.limpaTela();

            if(lstPessNull){

                System.out.print("\n\n\n\n\n");
                System.out.println("\t\t\t+=======================================================+\n" + 
                                   "\t\t\t|      Nenhum registro existente de Pessoa/Aluno !!!    |\n" + 
                                   "\t\t\t|                <Enter> volta ao menu.                 |\n" + 
                                   "\t\t\t+=======================================================+\n");
                entrada.nextLine().trim();
                return;

            }
            
            if(tipoPagina == 'B')
                listaBloco(listaPess, pontPosArray, quantObjPag);
            if(tipoPagina == 'T')
                listaTabela(listaPess, pontPosArray, quantObjPag);
            
            System.out.println(rodapeMSG + "P�g:" + paginaAtual + "/" + totalPagina);
            
            opEscolhida = entrada.nextLine().trim();
            
            if(opEscolhida.equals(""))
                break;
            if(opEscolhida.equalsIgnoreCase("B")){
                tipoPagina   = 'B';
                quantObjPag  = 5;
                paginaAtual  = 1;
                pontPosArray = (paginaAtual - 1) * quantObjPag;
                totalPagina  = (listaPess.size() % quantObjPag == 0) 
                               ? (listaPess.size() / quantObjPag) 
                               : (listaPess.size() / quantObjPag + 1);
            }
            if(opEscolhida.equalsIgnoreCase("T")){
                tipoPagina   = 'T';
                quantObjPag  = 36;
                paginaAtual  = 1;
                pontPosArray = (paginaAtual - 1) * quantObjPag;
                totalPagina  = (listaPess.size() % quantObjPag == 0) 
                               ? (listaPess.size() / quantObjPag) 
                               : (listaPess.size() / quantObjPag + 1);
            }
            if(opEscolhida.equalsIgnoreCase("S")){
                paginaAtual  = (paginaAtual > 1) ? --paginaAtual : paginaAtual;
                pontPosArray = (paginaAtual - 1) * quantObjPag;
            }
            if(opEscolhida.equalsIgnoreCase("D")){
                paginaAtual  = (paginaAtual < totalPagina) ? ++paginaAtual : paginaAtual;
                pontPosArray = (paginaAtual - 1) * quantObjPag;
            }

            if(opEscolhida.equalsIgnoreCase("P")){
                List<Pessoa> tmpLista = geraPesquisa();
                if(tmpLista != null){
                    if(tmpLista.size() > 0){
                        listaPess = tmpLista;
                        paginaAtual  = 1;
                        pontPosArray = (paginaAtual - 1) * quantObjPag;
                        totalPagina  = (listaPess.size() % quantObjPag == 0) 
                                       ? (listaPess.size() / quantObjPag) 
                                       : (listaPess.size() / quantObjPag + 1);
                    }
                }
            }
        }
    }


    

    //C�digo respons�vel pela tela de atualiza��o de Pessoa/Aluno.

    public static Boolean atualizacao(){

        while(true){
        
            Pessoa   pessoa;
            Integer  confirmaAtualizacao  = -1;
            Integer  continuarAtualizacao = -1;
            Integer  pessoaAluno          = -1;
            String   telaAtualizacao      = "*** Atualiza��o de Pessoa / Aluno ***\n\n";

            String[] iD        = new String[]{"Informe o n�mero de ID:",                              "Atualiza��o Pessoa/Aluno(a)", "id", null};
            String[] nome      = new String[]{"Edite o nome:",                                        "Atualiza��o Pessoa/Aluno(a)", "nm", null};
            String[] fone      = new String[]{"Edite o telefone? (dd)nnnn-nnnn ou (dd)nnnnn-nnnn:",   "Atualiza��o Pessoa/Aluno(a)", "fn", null};
            String[] dtNasc    = new String[]{"Edite a Data de nascimento dd/mm/aaaa ou dd-mm-aaaa:", "Atualiza��o Pessoa/Aluno(a)", "dt", null};
            String[] notaFinal = new String[]{"Edite a Nota Final (0 a 100):",                        "Atualiza��o Pessoa/Aluno(a)", "nt", null};
            String   dtCad     = null;

            Util.atualizaTela(telaAtualizacao);

            while(true){
                if(Util.processaDados(iD)){
                    Integer iDPesq = Integer.parseInt(iD[3]);
                            pessoa = CtrlDado.pesqPessoaPorID(iDPesq);
                    
                    if(pessoa == null){
                        Integer tentarID = -1;
                        while(tentarID == -1){
                            tentarID = JOptionPane.showOptionDialog(null, "O n�mero de ID " + iDPesq + " n�o existe. \n Tentar outro? [enter]=Sim", 
                                                                     "Atualiza��o Pessoa/Aluno(a)", JOptionPane.YES_NO_OPTION, 
                                                                     JOptionPane.QUESTION_MESSAGE, null, botoesConfirm, 
                                                                     botoesConfirm[0]);
                            if(tentarID == 1) return false;
                        }
                        continue;
                    }

                    if(pessoa instanceof Pessoa){
                        pessoaAluno = naoAluno;
                        iD    [3]   = Integer.toString(pessoa.getIdPess());
                        nome  [3]   = pessoa.getNome();
                        fone  [3]   = pessoa.getFone();
                        dtNasc[3]   = Util.dataParaTxt(pessoa.getDtNasc());
                        dtCad       = Util.dataParaTxt(pessoa.getDtCad());
                    }
                    if(pessoa instanceof Aluno){
                        pessoaAluno  = aluno;
                        Aluno aluno  = (Aluno) pessoa;
                        notaFinal[3] = aluno.getNotaFinalCurso().toString();
                    }

                }
                else
                    return false;

                break;
            }

            List<String> substVar = new ArrayList<String>();
            substVar.add(nome[3]);
            substVar.add(fone[3]);
            substVar.add(dtNasc[3]);
            
            telaAtualizacao += "Nome: @0\n" +
                               "Telefone: @1\n" + 
                               "Data de nascimento: @2\n";
            
            if(notaFinal[3] != null){
                telaAtualizacao += "Nota Final: @3\n";
                substVar.add(Util.trimNum(notaFinal[3]).replace(".", ","));
            }

            Util.atualizaTela(telaAtualizacao, substVar);
            
            if(Util.processaDados(nome))   { substVar.set(0,   nome[3]); Util.atualizaTela(telaAtualizacao, substVar); } else return false;
            if(Util.processaDados(fone))   { substVar.set(1,   fone[3]); Util.atualizaTela(telaAtualizacao, substVar); } else return false;
            if(Util.processaDados(dtNasc)) { substVar.set(2, dtNasc[3]); Util.atualizaTela(telaAtualizacao, substVar); } else return false;
            
            if(notaFinal[3] != null){
                if(Util.processaDados(notaFinal)){ 
                    substVar.set(3, Util.trimNum(notaFinal[3]).replace(".", ",")); 
                    Util.atualizaTela(telaAtualizacao, substVar); 
                } 
                else {
                    return false;
                }
            }

            while(confirmaAtualizacao == -1){
                confirmaAtualizacao = JOptionPane.showOptionDialog(null, "Confirma atualiza��o do  cadastro com os dados atuais? [enter]=Sim", 
                                                                         "Confirma Atualiza��o", JOptionPane.YES_NO_OPTION, 
                                                                         JOptionPane.QUESTION_MESSAGE, null, botoesConfirm, 
                                                                         botoesConfirm[0]);
                if(confirmaAtualizacao == 0){
                    break;
                }
                else if(confirmaAtualizacao == 1){
                    JOptionPane.showMessageDialog(null, "ATUALIZA��O CANCELADA", 
                                                              "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
            }
                    
            String[] dadosAtualiza = {nome[3], fone[3], dtNasc[3], dtCad, Util.dataParaTxt(new Date()), notaFinal[3]};
            CtrlDado.atualizaPessoa(pessoa, dadosAtualiza);

            JOptionPane.showMessageDialog(null, "ATUALIZA��O CONCLU�DA", 
                                                              "Aviso", JOptionPane.INFORMATION_MESSAGE);

            while(continuarAtualizacao == -1){
                continuarAtualizacao = JOptionPane.showOptionDialog(null, "Continuar atualizando? [enter]=Sim", 
                                                                          "Continuar Atualiza��o", JOptionPane.YES_NO_OPTION, 
                                                                          JOptionPane.QUESTION_MESSAGE, null, botoesConfirm, 
                                                                          botoesConfirm[0]);
            }

            if(continuarAtualizacao == 1)break;

        }

        return true;

    }


    

    //C�digo respons�vel pela tela de exclus�o de Pessoa/Aluno.

    public static Boolean exclusao(){
        
        Boolean exclusao;

        while(true){
            
            Integer idPess;
            Integer confirmaExclusao   = -1;
            Integer continuarExcluindo = -1;
                    exclusao           = false;
            Pessoa  pessoa;

            String[] iD = new String[]{"Informe o n�mero de ID:", "Exclus�o Pessoa/Aluno(a)", "id", null};
            Util.limpaTela();
            System.out.print("*** Exclus�o de Pessoa / Aluno ***\n\n");
            Util.processaDados(iD);
            if(iD[3] != null){
                idPess = Integer.parseInt(iD[3]);
                pessoa = CtrlDado.pesqPessoaPorID(idPess);
                if(pessoa != null){
                    exibeRegExcluir(pessoa);

                    while(confirmaExclusao == -1){
                        confirmaExclusao = JOptionPane.showOptionDialog(null, "Confirma exclus�o do dados atuais? [enter]=Sim", 
                                                                              "Confirma Exclus�o", JOptionPane.YES_NO_OPTION, 
                                                                              JOptionPane.QUESTION_MESSAGE, null, botoesConfirm, 
                                                                              botoesConfirm[0]);
                        if(confirmaExclusao == 0){
                            if(CtrlDado.excluiPessoa(idPess)){
                                Util.atualizaTela("*** Exclus�o de Pessoa / Aluno ***\n\n");
                                JOptionPane.showMessageDialog(null, "Pessoa/Aluno(a) com ID: " +  String.format("%05d", idPess) + " exclu�do(a)!", 
                                                      "Aviso", JOptionPane.INFORMATION_MESSAGE);
                                exclusao = true;
                                break;
                            }
                        }
                        else if(confirmaExclusao == 1){
                            JOptionPane.showMessageDialog(null, "EXCLUS�O CANCELADA", 
                                                                      "Aviso", JOptionPane.INFORMATION_MESSAGE);
                            exclusao = false;
                            break;
                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Pessoa/Aluno(a) com ID: " +  String.format("%05d", idPess) + " n�o localizado(a)!", 
                                                  "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    exclusao           = false;
                    continuarExcluindo = -2;
                }
            
            }
            else{
                break;
            }

            while(continuarExcluindo == -1){
                continuarExcluindo = JOptionPane.showOptionDialog(null, "Deseja excluir mais algum registro? [enter]=Sim", 
                                                                        "Continuar Exclus�o", JOptionPane.YES_NO_OPTION, 
                                                                        JOptionPane.QUESTION_MESSAGE, null, botoesConfirm, 
                                                                        botoesConfirm[0]);
            }

            if(continuarExcluindo == 1)break;

        }

        return exclusao;
    }

    


    //C�digo respons�vel pela op��o de listagem em bloco.

    private static void listaBloco(List<Pessoa> listaPessoa, Integer indInicio, Integer quantObjListar){

        DateFormat dataFmt =  new SimpleDateFormat("dd/MM/yyyy");
        Integer     limite =  ( (indInicio + quantObjListar) > listaPessoa.size() ) ? listaPessoa.size() : (indInicio + quantObjListar);

        for( ; indInicio < limite ; indInicio++ ){
                
            System.out.println("ID: "                       + String.format("%05d", listaPessoa.get(indInicio).getIdPess())); //listaPessoa.get(indInicio).getIdPess());
            System.out.println("Nome: "                     + listaPessoa.get(indInicio).getNome());
            System.out.println("Fone: "                     + listaPessoa.get(indInicio).getFone());
            System.out.println("Data de Nascimento: "       + dataFmt.format(listaPessoa.get(indInicio).getDtNasc()));
            System.out.println("Data de Cadastro: "         + dataFmt.format(listaPessoa.get(indInicio).getDtCad()));
            System.out.println("Data da �ltima Altera��o: " + dataFmt.format(listaPessoa.get(indInicio).getDtUltAlt()));
            
            if(listaPessoa.get(indInicio) instanceof Aluno){
                Aluno testeAluno = (Aluno) listaPessoa.get(indInicio);
                System.out.println("Nota Final: " + Util.trimNum(testeAluno.getNotaFinalCurso().toString()).replace(".", ","));
            }

            System.out.println();
        }
        
    }

    


    //C�digo respons�vel pela op��o de listagem em tabela.

    private static void listaTabela(List<Pessoa> listaPessoa, Integer indInicio, Integer quantObjListar){
        
        String cabecalho = "+--------------------------------------------------------------------------------------------------------------------------------------+\n" + 
                           "| ID N� |               Nome da Pessoa / Aluno              |     Fone       | Data Nasc. | Data Cadastro | Atualizado em | Nota Final |\n" + 
                           "+--------------------------------------------------------------------------------------------------------------------------------------+";

        String rodape    = "+--------------------------------------------------------------------------------------------------------------------------------------+\n";
                          
        DateFormat dataFmt = new SimpleDateFormat("dd/MM/yyyy");
        Integer     limite =  ( (indInicio + quantObjListar) > listaPessoa.size() ) ? listaPessoa.size() : (indInicio + quantObjListar);

        System.out.println(cabecalho);
        
        for( ; indInicio < limite ; indInicio++ ){
                
            System.out.print("| " +  String.format("%05d", listaPessoa.get(indInicio).getIdPess()) + " ");
            System.out.print("| " + listaPessoa.get(indInicio).getNome() + " ".repeat(50 - listaPessoa.get(indInicio).getNome().length()));
            System.out.print("| " + listaPessoa.get(indInicio).getFone() + " ".repeat(15 - listaPessoa.get(indInicio).getFone().length()));
            System.out.print("| " + dataFmt.format(listaPessoa.get(indInicio).getDtNasc()) + " ");
            System.out.print("|  " + dataFmt.format(listaPessoa.get(indInicio).getDtCad()) + "   ");
            System.out.print("|  " + dataFmt.format(listaPessoa.get(indInicio).getDtUltAlt()) + "   ");
            
            if(listaPessoa.get(indInicio) instanceof Aluno){
                Aluno aluno = (Aluno) listaPessoa.get(indInicio);
                int espaco, espacoAnt, espacoPost;
                espaco = 10 - Util.trimNum(aluno.getNotaFinalCurso().toString()).replace(".", ",").length();
                espacoAnt = espaco / 2;
                espacoPost = (espaco % 2 != 0) ? espacoAnt + 1 : espacoAnt;
                System.out.print("| " + " ".repeat(espacoAnt) + Util.trimNum(aluno.getNotaFinalCurso().toString()).replace(".", ",") + " ".repeat(espacoPost) + " |");
            }
            else{
                System.out.print("| N�o Aluno  |");
            }

            System.out.println();
        }

        System.out.print(rodape);

    }

    
    //C�digo respons�vel por exibir os dados da Pessoa/Aluno
    //na tela antes da confirma��o de exclus�o.

    private static void exibeRegExcluir(Pessoa pessoa){

        DateFormat dataFmt = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("ID: "                       + String.format("%05d", pessoa.getIdPess()));
        System.out.println("Nome: "                     + pessoa.getNome());
        System.out.println("Fone: "                     + pessoa.getFone());
        System.out.println("Data de Nascimento: "       + dataFmt.format(pessoa.getDtNasc()));
        System.out.println("Data de Cadastro: "         + dataFmt.format(pessoa.getDtCad()));
        System.out.println("Data da �ltima Altera��o: " + dataFmt.format(pessoa.getDtUltAlt()));
        
        if(pessoa instanceof Aluno){
            Aluno aluno = (Aluno) pessoa;
            System.out.println("Nota Final: " + Util.trimNum(aluno.getNotaFinalCurso().toString()).replace(".", ","));
        }

    }

    

    //C�digo respons�vel pela exibi��o, execu��o e retorno do resultado 
    //das op��es de pesquisa, durante a listagem de dados sobre Pessoa/Aluno.

    private static List<Pessoa> geraPesquisa(){

        Scanner      sc            = new Scanner(System.in);
        DateFormat   dataFmt       = new SimpleDateFormat("dd/MM/yyyy");
        List<Pessoa> pesqPessoa    = null;
        String       dadoInicio    = null;
        String       dadoFinal     = null;
        
        Object[]     opcaoPesquisa = {"Tudo", "Somente Alunos(as)", "Somente n�o Alunos(as)", 
                                        "ID", "Nome", "Telefone", "Data de Nascimento", "Data de Cadastro", 
                                        "Data de Atualiza��o", "Nota Final"};
        
        String       pesqEscolhida = (String) JOptionPane.showInputDialog(null, "Selecione o tipo de pesquisa", 
                                                                          "Pesquisa de Pessoa/Aluno(a)", 
                                                                          JOptionPane.QUESTION_MESSAGE, null, opcaoPesquisa, "Tudo");
        
        pesqEscolhida = (pesqEscolhida == null) ? "" : pesqEscolhida;
        
        switch(pesqEscolhida){
            
            case "Tudo"                   : pesqPessoa = CtrlDado.listaPessoa();
                                            break;

            case "Somente Alunos(as)"     : pesqPessoa = CtrlDado.pesqSoAluno();
                                            if(pesqPessoa.size() == 0){
                                                System.out.print("<< N�o existem registro(s) de Somente Alunos(as) >> ");
                                                sc.nextLine();
                                            }
                                            break;

            case "Somente n�o Alunos(as)" : pesqPessoa = CtrlDado.pesqNaoAluno();
                                            if(pesqPessoa.size() == 0){
                                                System.out.print("<< N�o existem registro(s) de Somente n�o Alunos(as) >> ");
                                                sc.nextLine();
                                            }
                                            break;

            case "ID"                     : dadoInicio = Util.entradaDado("Informe o ID inicial: ", "Pesquisa por intervalo de ID", "id", "");
                                            if(dadoInicio == null) break;
                                            dadoFinal  = Util.entradaDado("Informe o ID final: ", "Pesquisa por intervalo de ID", "id", "");
                                            if(dadoFinal == null) break;
                                            pesqPessoa = CtrlDado.pesqFaixaId(Integer.parseInt(dadoInicio), Integer.parseInt(dadoFinal));
                                            if(pesqPessoa.size() == 0){
                                                System.out.print("<< N�o existem registro(s) na faixa de ID informada >> ");
                                                sc.nextLine();
                                            }
                                            break;

            case "Nome"                   : dadoInicio = Util.entradaDado("Informe o Nome ou parte dele: ", "Pesquisa por nome de Pessoa/Aluno(a)", "nm", "");
                                            if(dadoInicio == null) break;
                                            pesqPessoa = CtrlDado.pesqPorNome(dadoInicio);
                                            if(pesqPessoa.size() == 0){
                                                System.out.print("<< Nome informado n�o encontrado >> ");
                                                sc.nextLine();
                                            }
                                            break;

            case "Telefone"               : dadoInicio = Util.entradaDado("Informe o telefone ou parte dele (dd)nnnn-nnnn ou (dd)nnnnn-nnnn: ", 
                                                                          "Pesquisa por telefone de Pessoa/Aluno(a)", "fp", "");
                                            if(dadoInicio == null) break;
                                            pesqPessoa = CtrlDado.pesqPorFone(dadoInicio);
                                            if(pesqPessoa.size() == 0){
                                                System.out.print("<< Nenhum telefone encontrado que correspondesse com a busca  >> ");
                                                sc.nextLine();
                                            }
                                            break;

            case "Data de Nascimento"     : dadoInicio = Util.entradaDado("Informe a Data inicial dd/mm/aaaa ou dd-mm-aaaa: ", 
                                                                          "Pesquisa por intervalo de Data de Nascimento", "dt", "");
                                            if(dadoInicio == null) break;
                                            dadoFinal  = Util.entradaDado("Informe a Data final dd/mm/aaaa ou dd-mm-aaaa: ", 
                                                                          "Pesquisa por intervalo de Data de Nascimento", "dt", "");
                                            if(dadoFinal == null) break;
                                            try{
                                                pesqPessoa = CtrlDado.pesqFaixaDtNasc(dataFmt.parse(dadoInicio), dataFmt.parse(dadoFinal));
                                            }catch(Exception e){
                                                System.out.println(e.getMessage());
                                            }
                                            if(pesqPessoa.size() == 0){
                                                System.out.print("<< N�o existem registro(s) na faixa de Data de Nascimento informada >> ");
                                                sc.nextLine();
                                            }
                                            break;

            case "Data de Cadastro"       : dadoInicio = Util.entradaDado("Informe a Data inicial dd/mm/aaaa ou dd-mm-aaaa: ", 
                                                                          "Pesquisa por intervalo de Data de Cadastro", "dt", "");
                                            if(dadoInicio == null) break;
                                            dadoFinal  = Util.entradaDado("Informe a Data final dd/mm/aaaa ou dd-mm-aaaa: ", 
                                                                          "Pesquisa por intervalo de Data de Cadastro", "dt", "");
                                            if(dadoFinal == null) break;
                                            try{
                                                pesqPessoa = CtrlDado.pesqFaixaDtCad(dataFmt.parse(dadoInicio), dataFmt.parse(dadoFinal));
                                            }catch(Exception e){
                                                System.out.println(e.getMessage());
                                            }
                                            if(pesqPessoa.size() == 0){
                                                System.out.print("<< N�o existem registro(s) na faixa de Data de Cadastro informada >> ");
                                                sc.nextLine();
                                            }
                                            break;

            case "Data de Atualiza��o"    : dadoInicio = Util.entradaDado("Informe a Data inicial dd/mm/aaaa ou dd-mm-aaaa: ", 
                                                                          "Pesquisa por intervalo de Data de Atualiza��o", "dt", "");
                                            if(dadoInicio == null) break;
                                            dadoFinal  = Util.entradaDado("Informe a Data final dd/mm/aaaa ou dd-mm-aaaa: ", 
                                                                          "Pesquisa por intervalo de Data de Atualiza��o", "dt", "");
                                            if(dadoFinal == null) break;
                                            try{
                                                pesqPessoa = CtrlDado.pesqFaixaDtAtu(dataFmt.parse(dadoInicio), dataFmt.parse(dadoFinal));
                                            }catch(Exception e){
                                                System.out.println(e.getMessage());
                                            }
                                            if(pesqPessoa.size() == 0){
                                                System.out.print("<< N�o existem registro(s) na faixa de Data de Atualiza��o informada >> ");
                                                sc.nextLine();
                                            }
                                            break;

            case "Nota Final"             : dadoInicio = Util.entradaDado("Informe a menor nota: ", "Pesquisa por intervalo de Nota Final", "nt", "");
                                            if(dadoInicio == null) break;
                                            dadoFinal  = Util.entradaDado("Informe a maior nota: ", "Pesquisa por intervalo de Nota Final", "nt", "");
                                            if(dadoFinal == null) break;
                                            pesqPessoa = CtrlDado.pesqFaixaNotaFinal(Float.parseFloat(dadoInicio), Float.parseFloat(dadoFinal));
                                            if(pesqPessoa.size() == 0){
                                                System.out.print("<< N�o existem registro(s) na faixa de Nota Final informada >> ");
                                                sc.nextLine();
                                            }
                                            break;                                                                                  
        }

        
        return pesqPessoa;

    }

}