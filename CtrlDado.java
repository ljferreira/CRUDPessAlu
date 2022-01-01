package br.com.ferreira.crudpessalu.controller;

import br.com.ferreira.crudpessalu.model.*;
import br.com.ferreira.crudpessalu.view.Util;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
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

	public static List<Pessoa> pesqPorNome(String nome){
		
		List<Pessoa> lstPorNome = new ArrayList<Pessoa>();

		for(Pessoa pessoa : lstPess)
			if(pessoa.getNome().toLowerCase().contains(nome.toLowerCase()))
				lstPorNome.add(pessoa);
		
		return lstPorNome;
	}

	public static List<Pessoa> pesqPorFone(String fone){
		
		List<Pessoa> lstPorFone = new ArrayList<Pessoa>();

		for(Pessoa pessoa : lstPess)
			if(pessoa.getFone().replaceAll("[()-]", "").contains(fone.replaceAll("[()-]", "")))
				lstPorFone.add(pessoa);
		
		return lstPorFone;
	}

	public static List<Pessoa> pesqSoAluno(){

		List<Pessoa> lstSoAluno = new ArrayList<Pessoa>();

		for(Pessoa pessoa : lstPess)
			if(pessoa instanceof Aluno)
				lstSoAluno.add(pessoa);
		
		return lstSoAluno;
	}

	public static List<Pessoa> pesqNaoAluno(){

		List<Pessoa> lstNaoAluno = new ArrayList<Pessoa>();

		for(Pessoa pessoa : lstPess)
			if(!(pessoa instanceof Aluno))
				lstNaoAluno.add(pessoa);
		
		return lstNaoAluno;
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
				return pessoa;
		return null;

	}

	public static List<Pessoa> pesqFaixaId(Integer inicioId, Integer fimId){

		List<Pessoa> lstFxId = new ArrayList<Pessoa>();

		for(Pessoa pessoa : lstPess)
			if(pessoa.getIdPess() >= inicioId && pessoa.getIdPess() <= fimId)
				lstFxId.add(pessoa);
		
		return lstFxId;

	}

	public static List<Pessoa> pesqFaixaDtNasc(Date inicioData, Date fimData){

		List<Pessoa> lstFxDtNasc = new ArrayList<Pessoa>();

		for(Pessoa pessoa : lstPess)
			if(pessoa.getDtNasc().compareTo(inicioData) >= 0 && pessoa.getDtNasc().compareTo(fimData) <= 0)
				lstFxDtNasc.add(pessoa);
		
		return lstFxDtNasc;

	}

	public static List<Pessoa> pesqFaixaDtCad(Date inicioData, Date fimData){

		List<Pessoa> lstFxDtCad = new ArrayList<Pessoa>();

		for(Pessoa pessoa : lstPess)
			if(pessoa.getDtCad().compareTo(inicioData) >= 0 && pessoa.getDtCad().compareTo(fimData) <= 0)
				lstFxDtCad.add(pessoa);
		
		return lstFxDtCad;

	}

	public static List<Pessoa> pesqFaixaDtAtu(Date inicioData, Date fimData){

		List<Pessoa> lstFxDtAtu = new ArrayList<Pessoa>();

		for(Pessoa pessoa : lstPess)
			if(pessoa.getDtUltAlt().compareTo(inicioData) >= 0 && pessoa.getDtUltAlt().compareTo(fimData) <= 0)
				lstFxDtAtu.add(pessoa);
		
		return lstFxDtAtu;

	}

	public static List<Pessoa> pesqFaixaNotaFinal(Float inicioNotaFinal, Float fimNotaFinal){

		List<Pessoa> lstFxNtFinal = new ArrayList<Pessoa>();

		for(Pessoa pessoa : lstPess)
			if(pessoa instanceof Aluno)
				if(((Aluno)pessoa).getNotaFinalCurso() >= inicioNotaFinal && ((Aluno)pessoa).getNotaFinalCurso() <= fimNotaFinal)
					lstFxNtFinal.add(pessoa);
		
		return lstFxNtFinal;
	}

}