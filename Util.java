package br.com.ferreira.crudpessalu.view;

import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import br.com.ferreira.crudpessalu.model.*;
import br.com.ferreira.crudpessalu.controller.CtrlDado;

public class Util{

	public static String menu(String[] opMenu){

		if(opMenu.length < 3) return null;
		
		Scanner escolhaMenu = new Scanner(System.in);
		String  opEscolhida = "";
		String  telaMenu    = new String(opMenu[0] + "\n" + opMenu[1]);
		Boolean chkEscolha  = true;

		System.out.print(telaMenu);

		while(chkEscolha){
			
			opEscolhida = escolhaMenu.nextLine().trim();

			for(int cont = 2 ; cont < opMenu.length; cont++){
				if(opEscolhida.equals(opMenu[cont])){
					chkEscolha = false;
					break;
				}
			}

			atualizaTela(telaMenu);

		}

		//escolhaMenu.close();
		return opEscolhida;
	}

	
	public static void atualizaTela(String tela){
		limpaTela();
		System.out.print(tela);
	}

	public static void atualizaTela(String tela, String[] substVar){
		limpaTela();
		int pos = 0;
        for(String posVar : substVar){
	        tela = tela.replace("@" + pos, posVar);
	        pos++;
		}
		System.out.print(tela);
	}

	public static void atualizaTela(String tela, List<String> substVar){
		limpaTela();
		int pos = 0;
        for(String posVar : substVar){
	        tela = tela.replace("@" + pos, posVar);
	        pos++;
		}
		System.out.print(tela);
	}

	
	public static void limpaTela(){

		//Limpa a tela no windows, no linux e no MacOS
		try{
        
        if (System.getProperty("os.name").contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            Runtime.getRuntime().exec("clear");
    	}
    	
    	catch(Exception e){
    		System.out.println(e.getMessage());
    	}
	}

	
	public static String entradaDado(String dadoMsg, String titulo, String tipoValidacao, String conteudo){

		UIManager.put("OptionPane.cancelButtonText", "Cancelar");
		String   entradaDado;
		Short    validaDado;
		Integer  cancelOp       = -1;
		Object[] botoesConfirm  = {"Sim", "Não"};
		
		while(true){
			entradaDado = (String) JOptionPane.showInputDialog(null, dadoMsg, titulo, JOptionPane.QUESTION_MESSAGE, null, null, conteudo);
			if(entradaDado != null){

				entradaDado = entradaDado.trim();

				if(tipoValidacao != null){

					if(tipoValidacao.equals("nm")){
						validaDado = validaNome(entradaDado);
						if(validaDado == 0){
							return entradaDado;
						}
						else{
							JOptionPane.showMessageDialog(null, "Nome não informado " + entradaDado, "Erro de dado", JOptionPane.ERROR_MESSAGE);
						}
					}

					if(tipoValidacao.equals("fn")){
						validaDado = validaFone(entradaDado);
						if(validaDado == 0){
							return entradaDado;
						}
						else{
							JOptionPane.showMessageDialog(null, "Formato de dado inválido " + entradaDado, "Erro de dado", JOptionPane.ERROR_MESSAGE);
						}
					}

					if(tipoValidacao.equals("fp")){
						validaDado = validaFonePesquisa(entradaDado);
						if(validaDado == 0){
							return entradaDado;
						}
						else{
							JOptionPane.showMessageDialog(null, "Formato de dado inválido " + entradaDado, "Erro de dado", JOptionPane.ERROR_MESSAGE);
						}
					}

					if(tipoValidacao.equals("dt")){
						validaDado = validaData(entradaDado);
						if(validaDado == 0){
							entradaDado = entradaDado.replace("-", "/");
							return dataParaTxt(txtParaData(entradaDado));
						}
						if(validaDado == 1){
							JOptionPane.showMessageDialog(null, "Formato de dado inválido " + entradaDado, "Erro de dado", JOptionPane.ERROR_MESSAGE);
						}
						if(validaDado == 3){
							JOptionPane.showMessageDialog(null, "Formato de dado inválido " + entradaDado + "\nMês válido é de 01 a 12.", "Erro de dado", JOptionPane.ERROR_MESSAGE);
						}
						if(validaDado == 4){
							JOptionPane.showMessageDialog(null, "Formato de dado inválido " + entradaDado + "\nDia do mês não é válido.", "Erro de dado", JOptionPane.ERROR_MESSAGE);
						}
						if(validaDado == 5){
							JOptionPane.showMessageDialog(null, "O ano informado não é bissexto!!! " + entradaDado + "\nMês de fevereiro para este ano possui somente 28 dias.", "Erro de dado", JOptionPane.ERROR_MESSAGE);
						}
						if(validaDado == 6){
							JOptionPane.showMessageDialog(null, "Data inválida " + entradaDado + "\nData informada é superior ao dia de hoje.", "Erro de dado", JOptionPane.ERROR_MESSAGE);
						}
					}

					if(tipoValidacao.equals("nt")){
						validaDado = validaNota(entradaDado);
						if(validaDado == 0){
							Float entradaDadoTemp = Float.parseFloat(entradaDado.replace(",", "."));
							entradaDado = String.format("%.2f", entradaDadoTemp).replace(",", ".");
							return entradaDado;
						}
						if(validaDado == 1){
							JOptionPane.showMessageDialog(null, "Formato de dado inválido " + entradaDado, "Erro de dado", JOptionPane.ERROR_MESSAGE);
						}
						if(validaDado == 2){
							JOptionPane.showMessageDialog(null, "Nota inválida: " + entradaDado + ".\nNota mínima deve ser 0, e a máxima 100.", 
							                                    "Erro de dado", JOptionPane.ERROR_MESSAGE);
						}
					}

					if(tipoValidacao.equals("id")){
						validaDado = validaID(entradaDado);
						if(validaDado == 0){
							return entradaDado;
						}
						else{
							JOptionPane.showMessageDialog(null, "Formato de dado inválido " + entradaDado, "Erro de dado", JOptionPane.ERROR_MESSAGE);
						}
					}

				}
				else
					return entradaDado;

			}
			else{
				while(cancelOp == -1){                          //"Cancelar a operação de cadastro?
					cancelOp = JOptionPane.showOptionDialog(null, "Cancelar esta operação?[enter]=Sim", "Cancela Operação", 
							                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesConfirm, botoesConfirm[0]);
					if(cancelOp == 0)
						return null;
					if(cancelOp == 1){
						cancelOp = -1;
						break;
					}
				}
			}
		}
	}

	
	public static Boolean processaDados(String[] dados){

		String resultEntradaDado = entradaDado(dados[0], dados[1], dados[2], trimNum(dados[3]));
		
		if(resultEntradaDado != null){
			dados[3] = resultEntradaDado;
			return true;
		}
		else
			return false;

	}


	public static Boolean validaDadoExpReg(String dado, String ExpReg){

		if(ExpReg.contains("@")){
			String[] ExpRegs = ExpReg.split("@");
			for(String valDado : ExpRegs)
				if(dado.matches(valDado))
					return true;
			return false;
		}
		else
			return dado.matches(ExpReg);
	}

	public static Short validaNome(String nome){

		return (short) (nome.equals("") ? 1 : 0);

	}

	public static Short validaFone(String fone){

		String expRegFone = "[(]\\d{2}[)](\\d{4}|\\d{5})[-]\\d{4}";
		
		if(!validaDadoExpReg(fone, expRegFone))
			return 1; //formato inválido de telefone
		else
			return 0;

	}

	public static Short validaFonePesquisa(String fone){

		String expRegFone = "[(]\\d{2}[)](\\d{4}|\\d{5})[-]\\d{4}@\\d{1,11}";
		
		if(!validaDadoExpReg(fone, expRegFone))
			return 1; //formato inválido de telefone
		else
			return 0;

	}

	public static Short validaData(String data){

		String expRegData = "(\\d{1}|\\d{2})[/](\\d{1}|\\d{2})[/]\\d{4}@(\\d{1}|\\d{2})[-](\\d{1}|\\d{2})[-]\\d{4}";
		Date hoje = new Date();
		String[] hojeDiaMesAno = dataParaTxt(hoje).split("/");

		if(!validaDadoExpReg(data, expRegData))
			return 1; //formato inválido de data

		String[] diaMesAno = data.replace("-", "/").split("/");
		
		if(Integer.parseInt(diaMesAno[0]) == 0 || Integer.parseInt(diaMesAno[1]) == 0 || Integer.parseInt(diaMesAno[2]) == 0)
			return 1;

		if(Integer.parseInt(diaMesAno[1]) > 12)
			return 3;
		
		if((Integer.parseInt(diaMesAno[1]) == 2 || Integer.parseInt(diaMesAno[1]) == 4 || Integer.parseInt(diaMesAno[1]) == 6 || 
		    Integer.parseInt(diaMesAno[1]) == 9 || Integer.parseInt(diaMesAno[1]) == 11) && Integer.parseInt(diaMesAno[0]) > 30){
			return 4;
		}
		else if(Integer.parseInt(diaMesAno[0]) > 31){
			return 4;
		}

		if(!anoBissexto(Integer.parseInt(diaMesAno[2])) && Integer.parseInt(diaMesAno[1]) == 2 && Integer.parseInt(diaMesAno[0]) > 28)
			return 5;

		if(Integer.parseInt(diaMesAno[1]) == 2 && Integer.parseInt(diaMesAno[0]) > 29)
			return 4;
		
		if(Integer.parseInt(diaMesAno[2]) > Integer.parseInt(hojeDiaMesAno[2]))
			return 6;

		if(Integer.parseInt(diaMesAno[2]) == Integer.parseInt(hojeDiaMesAno[2]) && Integer.parseInt(diaMesAno[1]) > Integer.parseInt(hojeDiaMesAno[1]))
			return 6;

		if(Integer.parseInt(diaMesAno[2]) == Integer.parseInt(hojeDiaMesAno[2]) 
		   && Integer.parseInt(diaMesAno[1]) == Integer.parseInt(hojeDiaMesAno[1]) 
		   && Integer.parseInt(diaMesAno[0]) > Integer.parseInt(hojeDiaMesAno[0]))
			return 6;




		return 0;

	}

	public static Boolean anoBissexto(Integer ano){
		
		if(ano < 1)
			return false;

		if(ano % 4 == 0 )
			if(ano % 100 != 0)
				return true;

		if(ano % 4 != 0)
			if(ano % 400 == 0)
				return true;

		if(ano % 400 == 0)
        	return true;
		
		return false;

	}

	public static Date txtParaData(String txtData){
		
		
		DateFormat fmt  = new SimpleDateFormat("dd/MM/yyyy");
        Date       data = null;
        
        try{
            data = fmt.parse(txtData);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return data;
	}

	public static String dataParaTxt(Date data){
		
		DateFormat fmt     = new SimpleDateFormat("dd/MM/yyyy");
        String     dataTxt = null;
        
        try{
            dataTxt = fmt.format(data);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return dataTxt;
	}


	public static Float txtParaNota(String txtNota){
		
		Float nota = 0F;
		try{
			nota = Float.parseFloat(txtNota);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return nota;
	}

	public static Short validaNota(String nota){

		//String expRegNota   = "\\d{0,3}([.]?|[,]?)\\d{0,2}";
		String expRegNota   = "\\d+([.]?|[,]?)\\d*";

		Float analisaNota = 0F;
		
		if(!validaDadoExpReg(nota, expRegNota))
			return 1; //formato inválido de nota
		else{
			analisaNota = Float.parseFloat(nota.replace(",", "."));
			if(analisaNota < 0 || analisaNota > 100)
				return 2; //valor inválido de nota
		}
		return 0;
	}

	public static Short validaID(String iD){

		String expRegID   = "\\d+";

		if(!validaDadoExpReg(iD, expRegID))
			return 1; //formato inválido de ID
		
		return 0;
	}

	public static String trimNum(String sNum){

		String sNumAux = sNum;
		Double dNum = 0.0;
		
		if(sNum == null) return null;
		
		try{
			dNum = Double.parseDouble(sNum.replace(",", "."));
		}
		catch(Exception e)
		{
			return sNumAux;
		}
        
        sNum = dNum.toString();
        sNum = (sNum.endsWith(".0")) ? sNum.substring(0, sNum.length()-2).replace(".", ",") :sNum.replace(".", ",");
        return sNum;

	}

	public static String logo(Integer espacoEsq, Boolean random){

		String logo = " ".repeat(espacoEsq) + "######  ######   ##   ##  ######     #\n"   +
		              " ".repeat(espacoEsq) + "##      ##   ##  ##   ##  ##   ##  #####\n" +
		              " ".repeat(espacoEsq) + "##      #####    ##   ##  ##   ##    #\n"   +
		              " ".repeat(espacoEsq) + "######  ##  ##    #####   ######   praTi\n";

		return logo;

	}

}