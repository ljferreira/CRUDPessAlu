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
	private static       Object[] botoesConfirm     = {"Sim", "Não"};
	

	public static Boolean cadastro(){

		Integer  confirmaCadastro = -1;
		Integer  pessoaAluno      = -1;
		
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
			pessoaAluno = JOptionPane.showOptionDialog(null, "A pessoa sendo cadastrada é aluno(a)? [enter]=Sim", 
					                                         "Confirma Aluno(a)", JOptionPane.YES_NO_OPTION, 
					                                         JOptionPane.QUESTION_MESSAGE, null, botoesConfirm, 
					                                         botoesConfirm[0]);
		}

		if(pessoaAluno == aluno){
			if(Util.processaDados(notaFinal)) System.out.println("Nota final: " + notaFinal[3].replace(".", ","));
		}

		while(confirmaCadastro == -1){
			confirmaCadastro = JOptionPane.showOptionDialog(null, "Confirma geração do  cadastro com os dados atuais? [enter]=Sim", 
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

		JOptionPane.showMessageDialog(null, "CADASTRO CONCLUÍDO", 
								                          "Aviso", JOptionPane.INFORMATION_MESSAGE);

		return true;
	}


	public static void listagem(){
		List<Pessoa> lstPess;
		             lstPess      = CtrlDado.listaPessoa();
		DateFormat   dataFmt      = new SimpleDateFormat("dd/MM/yyyy");
		Scanner      entrada      = new Scanner(System.in);
		String       opEscolhida  = null; 
		String       telaListagem = "";
		Boolean      exibeDados   = true;
		String       rodapeMSG    = "<-- | <Enter>: Menu Principal | <B>: Lista em Blocos | <L>: Lista em Linhas | -->";

		Util.limpaTela();

		while(exibeDados){
			if(lstPess.size() != 0){
				for(Pessoa pess : lstPess){
					telaListagem += "ID: " + pess.getIdPess() + "\n";
					telaListagem += "Nome: " + pess.getNome() + "\n";
					telaListagem += "Fone: " + pess.getFone() + "\n";
					telaListagem += "Data de Nascimento: " + dataFmt.format(pess.getDtNasc()) + "\n";
					telaListagem += "Data de Cadastro: " + dataFmt.format(pess.getDtCad()) + "\n";
					telaListagem += "Data da Ultima Alteração: " + dataFmt.format(pess.getDtUltAlt()) + "\n";
					
					if(pess instanceof Aluno){
						Aluno alu = (Aluno) pess;
						telaListagem += "Nota Final: " + alu.getNotaFinalCurso() + "\n\n";
					}
					else{
						telaListagem += "\n";
					}
				}
				telaListagem += rodapeMSG;
			}
			else{
				telaListagem = "Nenhum registro encontrado para ser listado !!!\n\n" +
				               "Tecle <Enter> para retornar ao Menu Principal: ";

			}

			do{
				Util.atualizaTela(telaListagem);

				opEscolhida = entrada.nextLine().trim();
			}while(!opEscolhida.equals(""));

			exibeDados = false;

		}
	}


	public static Boolean atualizacao(){

		Pessoa   pessoa;
		Integer  confirmaAtualizacao = -1;
		Integer  pessoaAluno         = -1;
		String   telaAtualizacao     = "*** Atualização de Pessoa / Aluno ***\n\n";

		String[] iD        = new String[]{"Informe o número de ID:",                              "Atualização Pessoa/Aluno(a)", "id", null};
		String[] nome      = new String[]{"Edite o nome:",                                        "Atualização Pessoa/Aluno(a)", "nm", null};
		String[] fone      = new String[]{"Edite o telefone? (dd)nnnn-nnnn ou (dd)nnnnn-nnnn:",   "Atualização Pessoa/Aluno(a)", "fn", null};
		String[] dtNasc    = new String[]{"Edite a Data de nascimento dd/mm/aaaa ou dd-mm-aaaa:", "Atualização Pessoa/Aluno(a)", "dt", null};
		String[] notaFinal = new String[]{"Edite a Nota Final (0 a 100):",                        "Atualização Pessoa/Aluno(a)", "nt", null};
		String   dtCad     = null;

		Util.atualizaTela(telaAtualizacao);

		while(true){
			if(Util.processaDados(iD)){
				Integer iDPesq = Integer.parseInt(iD[3]);
				        pessoa = CtrlDado.pesqPessoaPorID(iDPesq);
				
				if(pessoa == null){
					Integer tentarID = -1;
					while(tentarID == -1){
						tentarID = JOptionPane.showOptionDialog(null, "O número de ID " + iDPesq + " não existe. \n Tentar outro? [enter]=Sim", 
						                                         "Atualização Pessoa/Aluno(a)", JOptionPane.YES_NO_OPTION, 
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
			substVar.add(notaFinal[3].replace(".", ","));
		}

		Util.atualizaTela(telaAtualizacao, substVar);
		
		if(Util.processaDados(nome))   { substVar.set(0,   nome[3]); Util.atualizaTela(telaAtualizacao, substVar); } else return false;
		if(Util.processaDados(fone))   { substVar.set(1,   fone[3]); Util.atualizaTela(telaAtualizacao, substVar); } else return false;
		if(Util.processaDados(dtNasc)) { substVar.set(2, dtNasc[3]); Util.atualizaTela(telaAtualizacao, substVar); } else return false;
		
		if(notaFinal[3] != null){
			if(Util.processaDados(notaFinal)){ 
				substVar.set(3, notaFinal[3].replace(".", ",")); 
				Util.atualizaTela(telaAtualizacao, substVar); 
			} 
			else {
				return false;
			}
		}

		while(confirmaAtualizacao == -1){
			confirmaAtualizacao = JOptionPane.showOptionDialog(null, "Confirma atualização do  cadastro com os dados atuais? [enter]=Sim", 
						                                             "Confirma Atualização", JOptionPane.YES_NO_OPTION, 
						                                             JOptionPane.QUESTION_MESSAGE, null, botoesConfirm, 
						                                             botoesConfirm[0]);
			if(confirmaAtualizacao == 0){
				break;
			}
			else if(confirmaAtualizacao == 1){
				JOptionPane.showMessageDialog(null, "ATUALIZAÇÃO CANCELADA", 
								                          "Aviso", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
				
		String[] dadosAtualiza = {nome[3], fone[3], dtNasc[3], dtCad, Util.dataParaTxt(new Date()), notaFinal[3]};
		CtrlDado.atualizaPessoa(pessoa, dadosAtualiza);

		JOptionPane.showMessageDialog(null, "ATUALIZAÇÃO CONCLUÍDA", 
								                          "Aviso", JOptionPane.INFORMATION_MESSAGE);

		return true;
	}


	public static Boolean exclusao(){
		
		Integer idPess;
		
		Scanner entrada = new Scanner(System.in);
		System.out.print("Informe o ID da Pessoa/Aluno para excluir: ");
		idPess = entrada.nextInt();
		return CtrlDado.excluiPessoa(idPess);
	}

	private void listaBloco(List<Pessoa> listaPessoa){
		;
	}

	private void listaLinha(List<Pessoa> listaPessoa){
		;
	}
}