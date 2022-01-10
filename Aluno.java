/**
 * aplicação CRUD +praTi
 * 
 * Classe Aluno que herda da classe Pessoa. 
 * Possui uma variável de instância, um construtor e um método setter e getter.
 * O construtor e o método setter tem acesso protegido.
 * Permitindo que objetos da classe Aluno somente sejam instanciados 
 * e modificados pela classe AcessoPessAlu, que pertence ao mesmo 
 * pacote da classe Aluno.
 * 
 *@author  Luciano J. Ferreira
 *@version 1.00 12/2021 
 */


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