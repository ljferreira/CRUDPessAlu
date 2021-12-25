package br.com.ferreira.crudpessalu.model;

public class TesteAluno extends TestePessoa{

	private Float notaFinalCurso;

	public TesteAluno(Integer idPess){
		super(idPess);
		this.notaFinalCurso = 87.67F;
	}

	public Float getNotaFinalCurso(){
		return this.notaFinalCurso;
	}

	public void setNotaFinalCurso(Float notaFinalCurso){
		this.notaFinalCurso = notaFinalCurso;
	}
	
}