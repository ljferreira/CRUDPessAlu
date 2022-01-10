/**
 * aplicação CRUD +praTi
 * 
 * Classe que controla o acesso, manipulação e pesquisa 
 * de pessoas e alunos armazenados em array.
 * 
 *@author  Luciano J. Ferreira
 *@version 1.00 12/2021 
 */


package br.com.ferreira.crudpessalu.controller;

import br.com.ferreira.crudpessalu.model.*;
import br.com.ferreira.crudpessalu.view.Util;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Collections;


public class CtrlDado{
    private static List<Pessoa>  lstPess       = new ArrayList<Pessoa>();  //repositório de pessoas/alunos cadastrados
    private static List<Integer> idPessLivre   = new ArrayList<Integer>(); //lista os IDs livres para reutilização se reciclaIdPess for verdadeiro
    private static Integer       idPess        = 0;                        //contador de ID, mantém o último ID gerado
    private static Boolean       reciclaIdPess = false;                    //indica se IDs livres (ex. quando objetos Pessoa são excluídos) podem ser
                                                                           //reutilizados em novos cadastros - padrão é false
    
    //permite o acesso aos objetos Pessoa/Aluno cadastrados
    public static List<Pessoa> listaPessoa(){
        return lstPess;
    }

    //retorna uma lista com todos os objetos Pessoa/Aluno 
    //cadastrados, que coincide com o nome informado ou parte dele 
    public static List<Pessoa> pesqPorNome(String nome){
        
        List<Pessoa> lstPorNome = new ArrayList<Pessoa>();

        for(Pessoa pessoa : lstPess)
            if(pessoa.getNome().toLowerCase().contains(nome.toLowerCase()))
                lstPorNome.add(pessoa);
        
        return lstPorNome;
    }

    
    //retorna uma lista com todos os objetos Pessoa/Aluno 
    //cadastrados, que coincide com o telefone informado ou parte dele 
    public static List<Pessoa> pesqPorFone(String fone){
        
        List<Pessoa> lstPorFone = new ArrayList<Pessoa>();

        for(Pessoa pessoa : lstPess)
            if(pessoa.getFone().replaceAll("[()-]", "").contains(fone.replaceAll("[()-]", "")))
                lstPorFone.add(pessoa);
        
        return lstPorFone;
    }

    
    //retorna uma lista com todos os objetos Aluno 
    public static List<Pessoa> pesqSoAluno(){

        List<Pessoa> lstSoAluno = new ArrayList<Pessoa>();

        for(Pessoa pessoa : lstPess)
            if(pessoa instanceof Aluno)
                lstSoAluno.add(pessoa);
        
        return lstSoAluno;
    }

    
    //retorna uma lista com todos as pessoas cadastradas que não são aluno(a)
    public static List<Pessoa> pesqNaoAluno(){

        List<Pessoa> lstNaoAluno = new ArrayList<Pessoa>();

        for(Pessoa pessoa : lstPess)
            if(!(pessoa instanceof Aluno))
                lstNaoAluno.add(pessoa);
        
        return lstNaoAluno;
    }

    
    //cadastra a Pessoa/Aluno no array lstPess, 
    //solicitando a instanciação do objeto através da classe AcessoPessAlu
    public static Boolean cadastraPessoa(String[] dadosCadastro){
        
        Pessoa        pessoa        = null;
        Aluno         aluno         = null;
        AcessoPessAlu acessoPessAlu = new AcessoPessAlu();

        if(dadosCadastro[5] == null)
            pessoa = acessoPessAlu.criaPessoa(getIdPess(), dadosCadastro[0], dadosCadastro[1], 
                                              Util.txtParaData(dadosCadastro[2]),  Util.txtParaData(dadosCadastro[3]), 
                                              Util.txtParaData(dadosCadastro[4]));
        else
            aluno  = acessoPessAlu.criaAluno(getIdPess(), dadosCadastro[0], dadosCadastro[1], 
                                             Util.txtParaData(dadosCadastro[2]), Util.txtParaData(dadosCadastro[3]), 
                                             Util.txtParaData(dadosCadastro[4]), Util.txtParaNota(dadosCadastro[5]));

        if(pessoa != null){
            lstPess.add(pessoa);
            return true;
        }

        if(aluno != null){
            lstPess.add(aluno);
            return true;
        }
        
        return false;

    }

    
    //gera um ID para cada Pessoa/Aluno cadastrado
    private static Integer getIdPess(){

        
        if(!reciclaIdPess)
            return ++idPess;

        Integer idPessTemp = 0;
        if(idPessLivre.size() == 0)
            return ++idPess;
        idPessTemp = idPessLivre.get(0);
        idPessLivre.remove(0);
        Collections.sort(idPessLivre);
        return idPessTemp;
    }

    
    //atualiza a Pessoa/Aluno no array lstPess, 
    //solicitando a classe AcessoPessAlu que atualize 
    //as variáveis de instância dos objetos Pessoa/Aluno
    public static Boolean atualizaPessoa(Pessoa pessAtulz, String[] dadosAtualiza){
        
        AcessoPessAlu acessoPessAlu = new AcessoPessAlu();

        if(dadosAtualiza[5] == null)
            pessAtulz = acessoPessAlu.atualizaPessoa(pessAtulz, dadosAtualiza[0], dadosAtualiza[1], Util.txtParaData(dadosAtualiza[2]),  
                                                     Util.txtParaData(dadosAtualiza[3]), Util.txtParaData(dadosAtualiza[4]));
        else
            pessAtulz  = acessoPessAlu.atualizaAluno((Aluno) pessAtulz, dadosAtualiza[0], dadosAtualiza[1], Util.txtParaData(dadosAtualiza[2]),  
                                                     Util.txtParaData(dadosAtualiza[3]), Util.txtParaData(dadosAtualiza[4]), 
                                                     Util.txtParaNota(dadosAtualiza[5]));


        for(int pos = 0 ; pos < lstPess.size() ; pos++)
            if(lstPess.get(pos).getIdPess() == pessAtulz.getIdPess()){
                lstPess.set(pos, pessAtulz);
                
                return true;
            }
                
        return false;
    }

    
    //exclui o cadastro da Pessoa/Aluno com o ID informado
    //remove o objeto respectivo da lista lstPess 
    public static Boolean excluiPessoa(Integer idPess){

        Boolean exclusao = false;
        for(int cont = 0 ; cont < lstPess.size() ; cont++){
            if(lstPess.get(cont).getIdPess() == idPess){
                lstPess.remove(cont);
                exclusao = true;
                if(reciclaIdPess){
                    idPessLivre.add(idPess);
                    Collections.sort(idPessLivre);
                }
                break;
            }
        }
        return exclusao;
    }

    
    //pesquisa uma Pessoa/Aluno pelo ID específico
    public static Pessoa pesqPessoaPorID(Integer idPess){

        for(Pessoa pessoa : lstPess)
            if(pessoa.getIdPess() == idPess)
                return pessoa;
        return null;

    }

    
    //pesquisa uma lista de Pessoa(s)/Aluno(S) por uma faixa de ID
    public static List<Pessoa> pesqFaixaId(Integer inicioId, Integer fimId){

        List<Pessoa> lstFxId = new ArrayList<Pessoa>();

        for(Pessoa pessoa : lstPess)
            if(pessoa.getIdPess() >= inicioId && pessoa.getIdPess() <= fimId)
                lstFxId.add(pessoa);
        
        return lstFxId;

    }

    
    //pesquisa uma lista de Pessoa(s)/Aluno(S) por um intervalo de data de nascimento
    public static List<Pessoa> pesqFaixaDtNasc(Date inicioData, Date fimData){

        List<Pessoa> lstFxDtNasc = new ArrayList<Pessoa>();

        for(Pessoa pessoa : lstPess)
            if(pessoa.getDtNasc().compareTo(inicioData) >= 0 && pessoa.getDtNasc().compareTo(fimData) <= 0)
                lstFxDtNasc.add(pessoa);
        
        return lstFxDtNasc;

    }

    
    //pesquisa uma lista de Pessoa(s)/Aluno(S) por um intervalo de data de cadastro
    public static List<Pessoa> pesqFaixaDtCad(Date inicioData, Date fimData){

        List<Pessoa> lstFxDtCad = new ArrayList<Pessoa>();

        for(Pessoa pessoa : lstPess)
            if(pessoa.getDtCad().compareTo(inicioData) >= 0 && pessoa.getDtCad().compareTo(fimData) <= 0)
                lstFxDtCad.add(pessoa);
        
        return lstFxDtCad;

    }

    
    //pesquisa uma lista de Pessoa(s)/Aluno(S) por um intervalo de data de atualização
    public static List<Pessoa> pesqFaixaDtAtu(Date inicioData, Date fimData){

        List<Pessoa> lstFxDtAtu = new ArrayList<Pessoa>();

        for(Pessoa pessoa : lstPess)
            if(pessoa.getDtUltAlt().compareTo(inicioData) >= 0 && pessoa.getDtUltAlt().compareTo(fimData) <= 0)
                lstFxDtAtu.add(pessoa);
        
        return lstFxDtAtu;

    }

    
    //pesquisa uma lista de Pessoa(s)/Aluno(S) por uma faixa de nota final
    public static List<Pessoa> pesqFaixaNotaFinal(Float inicioNotaFinal, Float fimNotaFinal){

        List<Pessoa> lstFxNtFinal = new ArrayList<Pessoa>();

        for(Pessoa pessoa : lstPess)
            if(pessoa instanceof Aluno)
                if(((Aluno)pessoa).getNotaFinalCurso() >= inicioNotaFinal && ((Aluno)pessoa).getNotaFinalCurso() <= fimNotaFinal)
                    lstFxNtFinal.add(pessoa);
        
        return lstFxNtFinal;
    }

}