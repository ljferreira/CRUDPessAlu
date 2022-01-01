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

		while(true){

			Integer  confirmaCadastro  = -1;
			Integer  continuarCadastro = -1;
			Integer  pessoaAluno       = -1;
			
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
				if(Util.processaDados(notaFinal)) System.out.println("Nota final: " + Util.trimNum(notaFinal[3]).replace(".", ","));
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

			while(continuarCadastro == -1){
				continuarCadastro = JOptionPane.showOptionDialog(null, "Continuar cadastrando? [enter]=Sim", 
							                                          "Continuar Cadastro", JOptionPane.YES_NO_OPTION, 
							                                          JOptionPane.QUESTION_MESSAGE, null, botoesConfirm, 
							                                          botoesConfirm[0]);
			}

			if(continuarCadastro == 1)break;

		}

		return true;

	}


	public static void listagem(){
		
		Scanner           entrada      = new Scanner(System.in);
		List<Pessoa>      listaPess;
		                  listaPess    = CtrlDado.listaPessoa();
		Boolean           lstPessNull  = (listaPess.size() == 0) ? true : false;

		//Para teste durante desenvolvimento///////////////////////////////////////////////////////
		//List<TestePessoa> listaPess    = new ArrayList<TestePessoa>();
		
		String            rodapeMSG    = "\n\n<-- <Enter>: Menu Principal | <B>: Lista Blocos | <T>: Lista Tabela | <S>: Pág. Anterior | <D>: Próxima Pág. | <P>: Pesquisa -->";
		String            opEscolhida  = "";
		Character         tipoPagina   = 'B'; //B --> Bloco ou T --> Tabela
		Integer           quantObjPag  = 5; // 5 para Bloco ou 36 para Tabela

		//comentar próximas 2 linhas para teste durante desenvolvimento
		Integer           totalPagina  = (listaPess.size() % quantObjPag == 0) 
		                                 ? (listaPess.size() / quantObjPag) : (listaPess.size() / quantObjPag + 1);
		Integer           paginaAtual  = 1;
		Integer           pontPosArray = (paginaAtual - 1) * quantObjPag;
		
		

		//Para teste durante desenvolvimento///////////////////////////////////////////////////////
		/*for(int cont = 0 ; cont < 100 ; cont++){
				
				//TestePessoa testePessoa = new TestePessoa(cont);
				TesteAluno  testeAluno  = new TesteAluno(cont);
				//listaPess.add(testePessoa);
				listaPess.add(testeAluno);


		}

		Integer totalPagina = (listaPess.size() % quantObjPag == 0) 
		                      ? (listaPess.size() / quantObjPag) 
		                      : (listaPess.size() / quantObjPag + 1);*/
		////////////////////////////////////////////////////////////////////////////////////////////

		
		
		while(true){

			Util.limpaTela();

			if(lstPessNull){

				System.out.print("\n\n\n\n\n");
				System.out.println("\t\t\t+=======================================================+\n" + 
					               "\t\t\t|      Nenhum registro existente de Pessoa/Aluno !!!    |\n" + 
					               "\t\t\t|                <Enter> volta ao menu.                 |\n" + 
					               "\t\t\t+=======================================================+\n");
				entrada.nextLine().trim();
				return;

			}
			
			//Util.limpaTela();

			if(tipoPagina == 'B')
				listaBloco(listaPess, pontPosArray, quantObjPag);
			if(tipoPagina == 'T')
				listaTabela(listaPess, pontPosArray, quantObjPag);
			
			System.out.println(rodapeMSG + "Pág:" + paginaAtual + "/" + totalPagina);
			
			opEscolhida = entrada.nextLine().trim();
			
			if(opEscolhida.equals(""))
				break;
			if(opEscolhida.equalsIgnoreCase("B")){
				tipoPagina   = 'B';
				quantObjPag  = 5;
				paginaAtual  = 1;
				pontPosArray = (paginaAtual - 1) * quantObjPag;
				totalPagina  = (listaPess.size() % quantObjPag == 0) 
		                       ? (listaPess.size() / quantObjPag) 
		                       : (listaPess.size() / quantObjPag + 1);
			}
			if(opEscolhida.equalsIgnoreCase("T")){
				tipoPagina   = 'T';
				quantObjPag  = 36;
				paginaAtual  = 1;
				pontPosArray = (paginaAtual - 1) * quantObjPag;
				totalPagina  = (listaPess.size() % quantObjPag == 0) 
		                       ? (listaPess.size() / quantObjPag) 
		                       : (listaPess.size() / quantObjPag + 1);
			}
			if(opEscolhida.equalsIgnoreCase("S")){
				paginaAtual  = (paginaAtual > 1) ? --paginaAtual : paginaAtual;
				pontPosArray = (paginaAtual - 1) * quantObjPag;
			}
			if(opEscolhida.equalsIgnoreCase("D")){
				paginaAtual  = (paginaAtual < totalPagina) ? ++paginaAtual : paginaAtual;
				pontPosArray = (paginaAtual - 1) * quantObjPag;
			}

			if(opEscolhida.equalsIgnoreCase("P")){
				List<Pessoa> tmpLista = geraPesquisa();
				if(tmpLista != null){
					if(tmpLista.size() > 0){
						listaPess = tmpLista;
						paginaAtual  = 1;
						pontPosArray = (paginaAtual - 1) * quantObjPag;
						totalPagina  = (listaPess.size() % quantObjPag == 0) 
				                       ? (listaPess.size() / quantObjPag) 
				                       : (listaPess.size() / quantObjPag + 1);
					}
				}
			}
		}
	}


	public static Boolean atualizacao(){

		while(true){
		
			Pessoa   pessoa;
			Integer  confirmaAtualizacao  = -1;
			Integer  continuarAtualizacao = -1;
			Integer  pessoaAluno          = -1;
			String   telaAtualizacao      = "*** Atualização de Pessoa / Aluno ***\n\n";

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
				substVar.add(Util.trimNum(notaFinal[3]).replace(".", ","));
			}

			Util.atualizaTela(telaAtualizacao, substVar);
			
			if(Util.processaDados(nome))   { substVar.set(0,   nome[3]); Util.atualizaTela(telaAtualizacao, substVar); } else return false;
			if(Util.processaDados(fone))   { substVar.set(1,   fone[3]); Util.atualizaTela(telaAtualizacao, substVar); } else return false;
			if(Util.processaDados(dtNasc)) { substVar.set(2, dtNasc[3]); Util.atualizaTela(telaAtualizacao, substVar); } else return false;
			
			if(notaFinal[3] != null){
				if(Util.processaDados(notaFinal)){ 
					substVar.set(3, Util.trimNum(notaFinal[3]).replace(".", ",")); 
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

			while(continuarAtualizacao == -1){
				continuarAtualizacao = JOptionPane.showOptionDialog(null, "Continuar atualizando? [enter]=Sim", 
							                                              "Continuar Atualização", JOptionPane.YES_NO_OPTION, 
							                                              JOptionPane.QUESTION_MESSAGE, null, botoesConfirm, 
							                                              botoesConfirm[0]);
			}

			if(continuarAtualizacao == 1)break;

		}

		return true;

	}


	public static Boolean exclusao(){
		
		Boolean exclusao;

		while(true){
			
			Integer idPess;
			Integer confirmaExclusao   = -1;
			Integer continuarExcluindo = -1;
			        exclusao           = false;
			Pessoa  pessoa;

			String[] iD = new String[]{"Informe o número de ID:", "Exclusão Pessoa/Aluno(a)", "id", null};
			Util.limpaTela();
			System.out.print("*** Exclusão de Pessoa / Aluno ***\n\n");
			Util.processaDados(iD);
			if(iD[3] != null){
				idPess = Integer.parseInt(iD[3]);
				pessoa = CtrlDado.pesqPessoaPorID(idPess);
				if(pessoa != null){
					exibeRegExcluir(pessoa);

					while(confirmaExclusao == -1){
						confirmaExclusao = JOptionPane.showOptionDialog(null, "Confirma exclusão do dados atuais? [enter]=Sim", 
								                                              "Confirma Exclusão", JOptionPane.YES_NO_OPTION, 
								                                              JOptionPane.QUESTION_MESSAGE, null, botoesConfirm, 
								                                              botoesConfirm[0]);
						if(confirmaExclusao == 0){
							if(CtrlDado.excluiPessoa(idPess)){
								Util.atualizaTela("*** Exclusão de Pessoa / Aluno ***\n\n");
								JOptionPane.showMessageDialog(null, "Pessoa/Aluno(a) com ID: " +  String.format("%05d", idPess) + " excluído(a)!", 
										              "Aviso", JOptionPane.INFORMATION_MESSAGE);
								exclusao = true;
								break;
							}
						}
						else if(confirmaExclusao == 1){
							JOptionPane.showMessageDialog(null, "EXCLUSÃO CANCELADA", 
											                          "Aviso", JOptionPane.INFORMATION_MESSAGE);
							exclusao = false;
							break;
						}
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Pessoa/Aluno(a) com ID: " +  String.format("%05d", idPess) + " não localizado(a)!", 
									              "Aviso", JOptionPane.INFORMATION_MESSAGE);
					exclusao           = false;
					continuarExcluindo = -2;
				}
			
			}
			else{
				break;
			}

			while(continuarExcluindo == -1){
				continuarExcluindo = JOptionPane.showOptionDialog(null, "Deseja excluir mais algum registro? [enter]=Sim", 
								                                        "Continuar Exclusão", JOptionPane.YES_NO_OPTION, 
								                                        JOptionPane.QUESTION_MESSAGE, null, botoesConfirm, 
								                                        botoesConfirm[0]);
			}

			if(continuarExcluindo == 1)break;

	}

	return exclusao;
}

	private static void listaBloco(List<Pessoa> listaPessoa, Integer indInicio, Integer quantObjListar){

		DateFormat dataFmt =  new SimpleDateFormat("dd/MM/yyyy");
		Integer     limite =  ( (indInicio + quantObjListar) > listaPessoa.size() ) ? listaPessoa.size() : (indInicio + quantObjListar);

		for( ; indInicio < limite ; indInicio++ ){
				
			System.out.println("ID: "                       + String.format("%05d", listaPessoa.get(indInicio).getIdPess())); //listaPessoa.get(indInicio).getIdPess());
			System.out.println("Nome: "                     + listaPessoa.get(indInicio).getNome());
			System.out.println("Fone: "                     + listaPessoa.get(indInicio).getFone());
			System.out.println("Data de Nascimento: "       + dataFmt.format(listaPessoa.get(indInicio).getDtNasc()));
			System.out.println("Data de Cadastro: "         + dataFmt.format(listaPessoa.get(indInicio).getDtCad()));
			System.out.println("Data da Última Alteração: " + dataFmt.format(listaPessoa.get(indInicio).getDtUltAlt()));
			
			if(listaPessoa.get(indInicio) instanceof Aluno){
				Aluno testeAluno = (Aluno) listaPessoa.get(indInicio);
				System.out.println("Nota Final: " + Util.trimNum(testeAluno.getNotaFinalCurso().toString()).replace(".", ","));
			}

			System.out.println();
		}
		
	}

	private static void listaTabela(List<Pessoa> listaPessoa, Integer indInicio, Integer quantObjListar){
		
		String cabecalho = "+--------------------------------------------------------------------------------------------------------------------------------------+\n" + 
		                   "| ID Nº |               Nome da Pessoa / Aluno              |     Fone       | Data Nasc. | Data Cadastro | Atualizado em | Nota Final |\n" + 
		                   "+--------------------------------------------------------------------------------------------------------------------------------------+";

		String rodape    = "+--------------------------------------------------------------------------------------------------------------------------------------+\n";
		                  
		DateFormat dataFmt = new SimpleDateFormat("dd/MM/yyyy");
		Integer     limite =  ( (indInicio + quantObjListar) > listaPessoa.size() ) ? listaPessoa.size() : (indInicio + quantObjListar);

		System.out.println(cabecalho);
		
		for( ; indInicio < limite ; indInicio++ ){
				
			System.out.print("| " +  String.format("%05d", listaPessoa.get(indInicio).getIdPess()) + " ");
			System.out.print("| " + listaPessoa.get(indInicio).getNome() + " ".repeat(50 - listaPessoa.get(indInicio).getNome().length()));
			System.out.print("| " + listaPessoa.get(indInicio).getFone() + " ".repeat(15 - listaPessoa.get(indInicio).getFone().length()));
			System.out.print("| " + dataFmt.format(listaPessoa.get(indInicio).getDtNasc()) + " ");
			System.out.print("|  " + dataFmt.format(listaPessoa.get(indInicio).getDtCad()) + "   ");
			System.out.print("|  " + dataFmt.format(listaPessoa.get(indInicio).getDtUltAlt()) + "   ");
			
			if(listaPessoa.get(indInicio) instanceof Aluno){
				Aluno aluno = (Aluno) listaPessoa.get(indInicio);
				int espaco, espacoAnt, espacoPost;
				//espaco = 10 - testeAluno.getNotaFinalCurso().toString().length();
				//espaco = 10 - String.format("%3.2f", aluno.getNotaFinalCurso()).length();
				espaco = 10 - Util.trimNum(aluno.getNotaFinalCurso().toString()).replace(".", ",").length();
				espacoAnt = espaco / 2;
				espacoPost = (espaco % 2 != 0) ? espacoAnt + 1 : espacoAnt;

				//System.out.println("Espaco, espacoAnt, espacoPost: " + espaco + " " + espacoAnt + " " + espacoPost );

				//System.out.print("| " + " ".repeat(espacoAnt) + testeAluno.getNotaFinalCurso().toString().replace(".", ",") + " ".repeat(espacoPost) + " |");
				//System.out.print("| " + " ".repeat(espacoAnt) + String.format("%3.2f", aluno.getNotaFinalCurso()) + " ".repeat(espacoPost) + " |");
				System.out.print("| " + " ".repeat(espacoAnt) + Util.trimNum(aluno.getNotaFinalCurso().toString()).replace(".", ",") + " ".repeat(espacoPost) + " |");
			}
			else{
				System.out.print("| Não Aluno  |");
			}

			System.out.println();
		}

		System.out.print(rodape);

	}

	private static void exibeRegExcluir(Pessoa pessoa){

		DateFormat dataFmt = new SimpleDateFormat("dd/MM/yyyy");

		System.out.println("ID: "                       + String.format("%05d", pessoa.getIdPess()));
		System.out.println("Nome: "                     + pessoa.getNome());
		System.out.println("Fone: "                     + pessoa.getFone());
		System.out.println("Data de Nascimento: "       + dataFmt.format(pessoa.getDtNasc()));
		System.out.println("Data de Cadastro: "         + dataFmt.format(pessoa.getDtCad()));
		System.out.println("Data da Última Alteração: " + dataFmt.format(pessoa.getDtUltAlt()));
		
		if(pessoa instanceof Aluno){
			Aluno aluno = (Aluno) pessoa;
			System.out.println("Nota Final: " + Util.trimNum(aluno.getNotaFinalCurso().toString()).replace(".", ","));
		}

	}

	private static List<Pessoa> geraPesquisa(){

		Scanner      sc            = new Scanner(System.in);
		DateFormat   dataFmt       = new SimpleDateFormat("dd/MM/yyyy");
		List<Pessoa> pesqPessoa    = null;
		String       dadoInicio    = null;
		String       dadoFinal     = null;
		
		Object[]     opcaoPesquisa = {"Tudo", "Somente Alunos(as)", "Somente não Alunos(as)", 
		                                "ID", "Nome", "Telefone", "Data de Nascimento", "Data de Cadastro", 
		                                "Data de Atualização", "Nota Final"};
		
		String       pesqEscolhida = (String) JOptionPane.showInputDialog(null, "Selecione o tipo de pesquisa", 
			                                                              "Pesquisa de Pessoa/Aluno(a)", 
			                                                              JOptionPane.QUESTION_MESSAGE, null, opcaoPesquisa, "Tudo");
		
		pesqEscolhida = (pesqEscolhida == null) ? "" : pesqEscolhida;
		
		switch(pesqEscolhida){
			
			case "Tudo"                   : pesqPessoa = CtrlDado.listaPessoa();
						                    break;

			case "Somente Alunos(as)"     : pesqPessoa = CtrlDado.pesqSoAluno();
											if(pesqPessoa.size() == 0){
												System.out.print("<< Não existem registro(s) de Somente Alunos(as) >> ");
												sc.nextLine();
											}
						                    break;

			case "Somente não Alunos(as)" : pesqPessoa = CtrlDado.pesqNaoAluno();
											if(pesqPessoa.size() == 0){
												System.out.print("<< Não existem registro(s) de Somente não Alunos(as) >> ");
												sc.nextLine();
											}
						                    break;

			case "ID"                     : dadoInicio = Util.entradaDado("Informe o ID inicial: ", "Pesquisa por intervalo de ID", "id", "");
											if(dadoInicio == null) break;
											dadoFinal  = Util.entradaDado("Informe o ID final: ", "Pesquisa por intervalo de ID", "id", "");
											if(dadoFinal == null) break;
											pesqPessoa = CtrlDado.pesqFaixaId(Integer.parseInt(dadoInicio), Integer.parseInt(dadoFinal));
											if(pesqPessoa.size() == 0){
												System.out.print("<< Não existem registro(s) na faixa de ID informada >> ");
												sc.nextLine();
											}
						                    break;

			case "Nome"                   : dadoInicio = Util.entradaDado("Informe o Nome ou parte dele: ", "Pesquisa por nome de Pessoa/Aluno(a)", "nm", "");
											if(dadoInicio == null) break;
											pesqPessoa = CtrlDado.pesqPorNome(dadoInicio);
											if(pesqPessoa.size() == 0){
												System.out.print("<< Nome informado não encontrado >> ");
												sc.nextLine();
											}
						                    break;

			case "Telefone"               : dadoInicio = Util.entradaDado("Informe o telefone ou parte dele (dd)nnnn-nnnn ou (dd)nnnnn-nnnn: ", 
																		  "Pesquisa por telefone de Pessoa/Aluno(a)", "fp", "");
											if(dadoInicio == null) break;
											pesqPessoa = CtrlDado.pesqPorFone(dadoInicio);
											if(pesqPessoa.size() == 0){
												System.out.print("<< Nenhum telefone encontrado que correspondesse com a busca  >> ");
												sc.nextLine();
											}
						                    break;

			case "Data de Nascimento"     : dadoInicio = Util.entradaDado("Informe a Data inicial dd/mm/aaaa ou dd-mm-aaaa: ", 
																		  "Pesquisa por intervalo de Data de Nascimento", "dt", "");
											if(dadoInicio == null) break;
											dadoFinal  = Util.entradaDado("Informe a Data final dd/mm/aaaa ou dd-mm-aaaa: ", 
																		  "Pesquisa por intervalo de Data de Nascimento", "dt", "");
											if(dadoFinal == null) break;
											try{
												pesqPessoa = CtrlDado.pesqFaixaDtNasc(dataFmt.parse(dadoInicio), dataFmt.parse(dadoFinal));
											}catch(Exception e){
												System.out.println(e.getMessage());
											}
											if(pesqPessoa.size() == 0){
												System.out.print("<< Não existem registro(s) na faixa de Data de Nascimento informada >> ");
												sc.nextLine();
											}
						                    break;

			case "Data de Cadastro"       : dadoInicio = Util.entradaDado("Informe a Data inicial dd/mm/aaaa ou dd-mm-aaaa: ", 
																		  "Pesquisa por intervalo de Data de Cadastro", "dt", "");
											if(dadoInicio == null) break;
											dadoFinal  = Util.entradaDado("Informe a Data final dd/mm/aaaa ou dd-mm-aaaa: ", 
																		  "Pesquisa por intervalo de Data de Cadastro", "dt", "");
											if(dadoFinal == null) break;
											try{
												pesqPessoa = CtrlDado.pesqFaixaDtCad(dataFmt.parse(dadoInicio), dataFmt.parse(dadoFinal));
											}catch(Exception e){
												System.out.println(e.getMessage());
											}
											if(pesqPessoa.size() == 0){
												System.out.print("<< Não existem registro(s) na faixa de Data de Cadastro informada >> ");
												sc.nextLine();
											}
						                    break;

			case "Data de Atualização"    : dadoInicio = Util.entradaDado("Informe a Data inicial dd/mm/aaaa ou dd-mm-aaaa: ", 
																		  "Pesquisa por intervalo de Data de Atualização", "dt", "");
											if(dadoInicio == null) break;
											dadoFinal  = Util.entradaDado("Informe a Data final dd/mm/aaaa ou dd-mm-aaaa: ", 
																		  "Pesquisa por intervalo de Data de Atualização", "dt", "");
											if(dadoFinal == null) break;
											try{
												pesqPessoa = CtrlDado.pesqFaixaDtAtu(dataFmt.parse(dadoInicio), dataFmt.parse(dadoFinal));
											}catch(Exception e){
												System.out.println(e.getMessage());
											}
											if(pesqPessoa.size() == 0){
												System.out.print("<< Não existem registro(s) na faixa de Data de Atualização informada >> ");
												sc.nextLine();
											}
						                    break;

			case "Nota Final"             : dadoInicio = Util.entradaDado("Informe a menor nota: ", "Pesquisa por intervalo de Nota Final", "nt", "");
											if(dadoInicio == null) break;
											dadoFinal  = Util.entradaDado("Informe a maior nota: ", "Pesquisa por intervalo de Nota Final", "nt", "");
											if(dadoFinal == null) break;
											pesqPessoa = CtrlDado.pesqFaixaNotaFinal(Float.parseFloat(dadoInicio), Float.parseFloat(dadoFinal));
											if(pesqPessoa.size() == 0){
												System.out.print("<< Não existem registro(s) na faixa de Nota Final informada >> ");
												sc.nextLine();
											}
						                    break;						                    					                	
		}

		
		return pesqPessoa;

	}

}





/*public static void listagem(){
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
	}*/

	/*private static String listaBloco(List<TestePessoa> listaPessoa, Integer indInicio, Integer quantObjListar){

		String lista = "";
		DateFormat dataFmt = new SimpleDateFormat("dd/MM/yyyy");
		Integer limite = ( quantObjListar > listaPessoa.size() ) ? listaPessoa.size() : quantObjListar;

		for(int cont =0 ; cont < limite ; cont++){
				
			lista += "ID: "                       + listaPessoa.get(cont).getIdPess() + "\n";
			lista += "Nome: "                     + listaPessoa.get(cont).getNome() + "\n";
			lista += "Fone: "                     + listaPessoa.get(cont).getFone() + "\n";
			lista += "Data de Nascimento: "       + dataFmt.format(listaPessoa.get(cont).getDtNasc()) + "\n";
			lista += "Data de Cadastro: "         + dataFmt.format(listaPessoa.get(cont).getDtCad()) + "\n";
			lista += "Data da Ultima Alteração: " + dataFmt.format(listaPessoa.get(cont).getDtUltAlt()) + "\n";
			
			if(listaPessoa.get(cont) instanceof TesteAluno){
				TesteAluno testeAluno = (TesteAluno) listaPessoa.get(cont);
				lista += "Nota Final: " + testeAluno.getNotaFinalCurso() + "\n";
			}

			lista += "\n";
		}

		return lista;
		
	}*/