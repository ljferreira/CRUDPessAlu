package br.com.ferreira.crudpessalu.view;

import java.util.Scanner;
import javax.swing.JOptionPane;
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
							JOptionPane.showMessageDialog(null, "Formato de dado inválido " + entradaDado, "Erro de dado", JOptionPane.ERROR_MESSAGE);
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

					if(tipoValidacao.equals("dt")){
						validaDado = validaData(entradaDado);
						if(validaDado == 0){
							entradaDado = entradaDado.replace("-", "/");
							return entradaDado;
						}
						else{
							JOptionPane.showMessageDialog(null, "Formato de dado inválido " + entradaDado, "Erro de dado", JOptionPane.ERROR_MESSAGE);
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

		String resultEntradaDado = entradaDado(dados[0], dados[1], dados[2], dados[3]);
		
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

		return 0;

	}

	public static Short validaFone(String fone){

		String expRegFone = "[(]\\d{2}[)](\\d{4}|\\d{5})[-]\\d{4}";
		
		if(!validaDadoExpReg(fone, expRegFone))
			return 1; //formato inválido de telefone
		else
			return 0;

	}

	public static Short validaData(String data){

		String expRegData = "(\\d{1}|\\d{2})[/](\\d{1}|\\d{2})[/]\\d{4}@(\\d{1}|\\d{2})[-](\\d{1}|\\d{2})[-]\\d{4}";
		
		if(!validaDadoExpReg(data, expRegData))
			return 1; //formato inválido de data
		else
			return 0;

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

}