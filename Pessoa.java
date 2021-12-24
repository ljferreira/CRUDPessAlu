package br.com.ferreira.crudpessalu.model;

import java.util.Date;

public class Pessoa{

	private Integer idPess;
	private String  nome;
	private String  fone;
	private Date    dtNasc;
	private Date    dtCad;
	private Date    dtUltAlt;

	protected Pessoa(Integer idPess, String nome, String fone, Date dtNasc, Date dtCad, Date dtUltAlt){
		this.idPess   = idPess;
		this.nome     = nome;
		this.fone     = fone;
		this.dtNasc   = dtNasc;
		this.dtCad    = dtCad;
		this.dtUltAlt = dtUltAlt;
	}

	public Integer getIdPess(){
		return this.idPess;
	}

	protected void setIdPess(Integer idPess){
		this.idPess = idPess;
	}

	public String getNome(){
		return this.nome;
	}

	protected void setNome(String nome){
		this.nome = nome;
	}

	public String getFone(){
		return this.fone;
	}

	protected void setFone(String fone){
		this.fone = fone;
	}

	public Date getDtNasc(){
		return this.dtNasc;
	}

	protected void setDtNasc(Date dtNasc){
		this.dtNasc = dtNasc;
	}

	public Date getDtCad(){
		return this.dtCad;
	}

	protected void setDtCad(Date dtCad){
		this.dtCad = dtCad;
	}

	public Date getDtUltAlt(){
		return this.dtUltAlt;
	}

	protected void setDtUltAlt(Date dtUltAlt){
		this.dtUltAlt = dtUltAlt;
	}

}