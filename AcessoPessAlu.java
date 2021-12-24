package br.com.ferreira.crudpessalu.model;

import java.util.Date;

public class AcessoPessAlu{

	private Pessoa pessoa;
	private Aluno  aluno;

	public Pessoa criaPessoa(Integer idPess, String nome, String fone, Date dtNasc, Date dtCad, Date dtUltAlt){
		
		this.pessoa = new Pessoa(idPess, nome, fone, dtNasc, dtCad, dtUltAlt);
		return this.pessoa;

	}

	public Aluno criaAluno(Integer idPess, String nome, String fone, Date dtNasc, Date dtCad, Date dtUltAlt, Float notaFinalCurso){
		
		this.aluno = new Aluno(idPess, nome, fone, dtNasc, dtCad, dtUltAlt, notaFinalCurso);
		return this.aluno;

	}

	public Pessoa atualizaPessoa(Pessoa pessoa, String nome, String fone, Date dtNasc, Date dtCad, Date dtUltAlt){
		
		this.pessoa = pessoa;
		this.pessoa.setNome(nome);
		this.pessoa.setFone(fone);
		this.pessoa.setDtNasc(dtNasc);
		this.pessoa.setDtCad(dtCad);
		this.pessoa.setDtUltAlt(dtUltAlt);
		return this.pessoa;

	}

	public Aluno atualizaAluno(Aluno aluno, String nome, String fone, Date dtNasc, Date dtCad, Date dtUltAlt, Float notaFinalCurso){
		
		this.aluno = aluno;
		this.aluno.setNome(nome);
		this.aluno.setFone(fone);
		this.aluno.setDtNasc(dtNasc);
		this.aluno.setDtCad(dtCad);
		this.aluno.setDtUltAlt(dtUltAlt);
		this.aluno.setNotaFinalCurso(notaFinalCurso);
		return this.aluno;
		
	}

}
