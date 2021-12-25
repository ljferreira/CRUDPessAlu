package br.com.ferreira.crudpessalu.model;

import java.util.Date;

public class TestePessoa{

	private Integer idPess;
	private String  nome;
	private String  fone;
	private Date    dtNasc;
	private Date    dtCad;
	private Date    dtUltAlt;

	public TestePessoa(Integer idPess){
		Date data     = new Date();
		this.idPess   = idPess;
		this.nome     = "TesteNome";
		this.fone     = "(99)9999-9999";
		this.dtNasc   = data;
		this.dtCad    = data;
		this.dtUltAlt = data;
	}

	public Integer getIdPess(){
		return this.idPess;
	}

	public void setIdPess(Integer idPess){
		this.idPess = idPess;
	}

	public String getNome(){
		return this.nome;
	}

	public void setNome(String nome){
		this.nome = nome;
	}

	public String getFone(){
		return this.fone;
	}

	public void setFone(String fone){
		this.fone = fone;
	}

	public Date getDtNasc(){
		return this.dtNasc;
	}

	public void setDtNasc(Date dtNasc){
		this.dtNasc = dtNasc;
	}

	public Date getDtCad(){
		return this.dtCad;
	}

	public void setDtCad(Date dtCad){
		this.dtCad = dtCad;
	}

	public Date getDtUltAlt(){
		return this.dtUltAlt;
	}

	public void setDtUltAlt(Date dtUltAlt){
		this.dtUltAlt = dtUltAlt;
	}

}