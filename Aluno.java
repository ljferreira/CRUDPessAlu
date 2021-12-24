package br.com.ferreira.crudpessalu.model;

import java.util.Date;

public class Aluno extends Pessoa{

	private Float notaFinalCurso;

	protected Aluno(Integer idPess, String nome, String fone, Date dtNasc, Date dtCad, Date dtUltAlt, Float notaFinalCurso){
		super(idPess, nome, fone, dtNasc, dtCad, dtUltAlt);
		this.notaFinalCurso = notaFinalCurso;
	}

	public Float getNotaFinalCurso(){
		return this.notaFinalCurso;
	}

	protected void setNotaFinalCurso(Float notaFinalCurso){
		this.notaFinalCurso = notaFinalCurso;
	}
}