package br.com.ferreira.crudpessalu.controller;

import br.com.ferreira.crudpessalu.model.*;
import br.com.ferreira.crudpessalu.view.Util;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class CtrlDado{
	private static List<Pessoa>  lstPess       = new ArrayList<Pessoa>();
	private static List<Integer> idPessLivre   = new ArrayList<Integer>();
	private static Integer       idPess        = 0;
	private static Boolean 		 reciclaIdPess = false;
	
	public static List<Pessoa> listaPessoa(){
		return lstPess;
	}

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
	                                         Util.txtParaData(dadosCadastro[2]),  Util.txtParaData(dadosCadastro[3]), 
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

	public static Boolean atualizaPessoa(Pessoa pessAtulz, String[] dadosAtualiza){
		Scanner sc = new Scanner(System.in);

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
				

		System.out.println("Chegou aqui!!! ID --> ");
		sc.nextLine();
		return false;
	}

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

	public static Pessoa pesqPessoaPorID(Integer idPess){

		for(Pessoa pessoa : lstPess)
			if(pessoa.getIdPess() == idPess)
				return pessoa;;
		return null;

	}
}