/**
 * aplica��o CRUD +praTi
 * 
 * Classe Util que possui m�todos utilit�rios est�ticos,
 * permitindo a execu��o de tarefas comuns a execu��o da aplica��o.
 * 
 *@author  Luciano J. Ferreira
 *@version 1.00 12/2021 
 */


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

    //M�todo que exibe e monitora a op��o escolhida do menu definido em 
    //um array de String, onde o primeiro elemento do array define um  
    //cabe�alho para o menu, o segundo elemento define a apar�ncia do menu 
    //e os demais elementos as chaves de escolha do menu.
    //Retorna a op��o escolhida (chave) como uma String.
    
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

        return opEscolhida;
    }

    
    
    //M�todo que permite atualizar o conte�do da tela em modo texto
    //por meio de uma String que fornece o conte�do a ser exibido, 
    //evitando a rolagem da tela.
    
    public static void atualizaTela(String tela){
        limpaTela();
        System.out.print(tela);
    }

    
    //M�todo que permite atualizar o conte�do da tela em modo texto
    //por meio de uma String que fornece o conte�do a ser exibido, 
    //evitando a rolagem da tela. Marcadores presentes na String tela
    //identificados por @ seguidos por um �ndice de um array de 
    //String[] substVar, substituem tais posi��es na String tela 
    //pelo conte�do presente na posi��o do array substVar.
    //(�til para a passagem de valores armazenados em vari�veis 
    //durante a entrada de dados pelo usu�rio)

    public static void atualizaTela(String tela, String[] substVar){
        limpaTela();
        int pos = 0;
        for(String posVar : substVar){
            tela = tela.replace("@" + pos, posVar);
            pos++;
        }
        System.out.print(tela);
    }

    

    //M�todo que possui a mesma funcionalidade do anterior
    //mas utilizando ArrayList para fornecer valores de vari�veis 
    //para substituir posi��es na String tela.

    public static void atualizaTela(String tela, List<String> substVar){
        limpaTela();
        int pos = 0;
        for(String posVar : substVar){
            tela = tela.replace("@" + pos, posVar);
            pos++;
        }
        System.out.print(tela);
    }

    

    //M�todo que permite limpar a tela em modo texto.

    public static void limpaTela(){

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

    
    //O m�todo seguinte permite a entrada e, a valida��o de dados 
    //no momento em que s�o informados pelo usu�rio, em uma caixa de di�logo.
    //Os par�metros do m�todo permitem que seja informado uma mensagem 
    //para solicita��o do dado ao usu�rio, um t�tulo para a caixa de di�logo, 
    //o tipo de valida��o a ser executada e um conte�do pr�-existente para ser 
    //exibido no campo de entrada do dado, respectivamente.
    //Retorna uma String representado o dado fornecido pelo usu�rio.
    //Caso ocorra algum erro, a valida��o n�o aceite o dado ou a entrada 
    //seja cancelada pelo usu�rio, retorna null.

    public static String entradaDado(String dadoMsg, String titulo, String tipoValidacao, String conteudo){

        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
        String   entradaDado;
        Short    validaDado;
        Integer  cancelOp       = -1;
        Object[] botoesConfirm  = {"Sim", "N�o"};
        
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
                            JOptionPane.showMessageDialog(null, "Nome n�o informado " + entradaDado, "Erro de dado", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    if(tipoValidacao.equals("fn")){
                        validaDado = validaFone(entradaDado);
                        if(validaDado == 0){
                            return entradaDado;
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Formato de dado inv�lido " + entradaDado, "Erro de dado", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    if(tipoValidacao.equals("fp")){
                        validaDado = validaFonePesquisa(entradaDado);
                        if(validaDado == 0){
                            return entradaDado;
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Formato de dado inv�lido " + entradaDado, "Erro de dado", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    if(tipoValidacao.equals("dt")){
                        validaDado = validaData(entradaDado);
                        if(validaDado == 0){
                            entradaDado = entradaDado.replace("-", "/");
                            return dataParaTxt(txtParaData(entradaDado));
                        }
                        if(validaDado == 1){
                            JOptionPane.showMessageDialog(null, "Formato de dado inv�lido " + entradaDado, "Erro de dado", JOptionPane.ERROR_MESSAGE);
                        }
                        if(validaDado == 3){
                            JOptionPane.showMessageDialog(null, "Formato de dado inv�lido " + entradaDado + "\nM�s v�lido � de 01 a 12.", "Erro de dado", JOptionPane.ERROR_MESSAGE);
                        }
                        if(validaDado == 4){
                            JOptionPane.showMessageDialog(null, "Formato de dado inv�lido " + entradaDado + "\nDia do m�s n�o � v�lido.", "Erro de dado", JOptionPane.ERROR_MESSAGE);
                        }
                        if(validaDado == 5){
                            JOptionPane.showMessageDialog(null, "O ano informado n�o � bissexto!!! " + entradaDado + "\nM�s de fevereiro para este ano possui somente 28 dias.", "Erro de dado", JOptionPane.ERROR_MESSAGE);
                        }
                        if(validaDado == 6){
                            JOptionPane.showMessageDialog(null, "Data inv�lida " + entradaDado + "\nData informada � superior ao dia de hoje.", "Erro de dado", JOptionPane.ERROR_MESSAGE);
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
                            JOptionPane.showMessageDialog(null, "Formato de dado inv�lido " + entradaDado, "Erro de dado", JOptionPane.ERROR_MESSAGE);
                        }
                        if(validaDado == 2){
                            JOptionPane.showMessageDialog(null, "Nota inv�lida: " + entradaDado + ".\nNota m�nima deve ser 0, e a m�xima 100.", 
                                                                "Erro de dado", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    if(tipoValidacao.equals("id")){
                        validaDado = validaID(entradaDado);
                        if(validaDado == 0){
                            return entradaDado;
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Formato de dado inv�lido " + entradaDado, "Erro de dado", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                }
                else
                    return entradaDado;

            }
            else{
                while(cancelOp == -1){                          //"Cancelar a opera��o de cadastro?
                    cancelOp = JOptionPane.showOptionDialog(null, "Cancelar esta opera��o?[enter]=Sim", "Cancela Opera��o", 
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

    

    //processaDados aciona o m�todo entradaDado passando 4 posi�oes de um array
    //que armazena informa��es a serem utilizadas por entradaDado.
    //Armazena a String retornada por processaDados na posi��o 3 do array 
    //utilizado como argumento na chamada do m�todo, retornando true.
    //Se entradaDado retornar null, retorna false.

    public static Boolean processaDados(String[] dados){

        String resultEntradaDado = entradaDado(dados[0], dados[1], dados[2], trimNum(dados[3]));
        
        if(resultEntradaDado != null){
            dados[3] = resultEntradaDado;
            return true;
        }
        else
            return false;

    }


    
    //Este m�todo recebe um dado como String, 
    //e uma ou mais express�es regulares dentro 
    //de outra String separadas por @.
    //Em seguida testa o dado contra as express�es,
    //se o dado for v�lido para alguma delas retorna true,
    //caso n�o valide em nenhuma delas retorna false.

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

    
    //Verifica se foi fornecido algum nome,
    //se sim retorna 0, caso contr�rio retorna 1.

    public static Short validaNome(String nome){

        return (short) (nome.equals("") ? 1 : 0);

    }

    


    //Valida o formato do dado sobre o telefone.

    public static Short validaFone(String fone){

        String expRegFone = "[(]\\d{2}[)](\\d{4}|\\d{5})[-]\\d{4}";
        
        if(!validaDadoExpReg(fone, expRegFone))
            return 1; //formato inv�lido de telefone
        else
            return 0;

    }

    
    //Valida o formato de dado para pesquisa parcial sobre o telefone.

    public static Short validaFonePesquisa(String fone){

        String expRegFone = "[(]\\d{2}[)](\\d{4}|\\d{5})[-]\\d{4}@\\d{1,11}";
        
        if(!validaDadoExpReg(fone, expRegFone))
            return 1; //formato inv�lido de telefone
        else
            return 0;

    }

    

    //M�todo para validar data fornecida.

    public static Short validaData(String data){

        String expRegData = "(\\d{1}|\\d{2})[/](\\d{1}|\\d{2})[/]\\d{4}@(\\d{1}|\\d{2})[-](\\d{1}|\\d{2})[-]\\d{4}";
        Date hoje = new Date();
        String[] hojeDiaMesAno = dataParaTxt(hoje).split("/");

        if(!validaDadoExpReg(data, expRegData))
            return 1; //formato inv�lido de data

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

    
    //Testa se o ano informado � bissexto
    //retornado true se verdadeiro e false caso cont�rio.

    public static Boolean anoBissexto(Integer ano){
        
        if(ano < 1)
            return false;

        if(ano % 400 == 0)
            return true;

        if(ano % 4 == 0 )
            if(ano % 100 != 0)
                return true;

        return false;

    }

    
    //Transforma data em formato texto para formato de data.

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

    
    //Transforma data em formato data para formato de texto.

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


    //Transforma nota em formato texto para numero Float.

    public static Float txtParaNota(String txtNota){
        
        Float nota = 0F;
        try{
            nota = Float.parseFloat(txtNota);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return nota;
    }

    

    //Valida nota informada, verificando se o 
    //formato e a faixa de valores est�o corretos.

    public static Short validaNota(String nota){

        String expRegNota   = "\\d+([.]?|[,]?)\\d*";

        Float analisaNota = 0F;
        
        if(!validaDadoExpReg(nota, expRegNota))
            return 1; //formato inv�lido de nota
        else{
            analisaNota = Float.parseFloat(nota.replace(",", "."));
            if(analisaNota < 0 || analisaNota > 100)
                return 2; //valor inv�lido de nota
        }
        return 0;
    }

    
    

    //Valida ID, verificando se o formato est� correto.

    public static Short validaID(String iD){

        String expRegID   = "\\d+";

        if(!validaDadoExpReg(iD, expRegID))
            return 1; //formato inv�lido de ID
        
        return 0;
    }

    

    //Apara valor n�merico em formato texto
    //eliminando zeros desnecess�rios � esquerda
    //e � direita do n�mero.

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

    
    //Gera logo presente no menu principal da aplica��o,
    //sendo poss�vel definir o espa�amento a esquerda do logo
    //para exibi��o na tela.
    //O par�metro booleano random, poder� permitir a implementa��o de c�digo 
    //que randomiza os caracteres que formam o logotipo.

    public static String logo(Integer espacoEsq, Boolean random){

        String logo = " ".repeat(espacoEsq) + "######  ######   ##   ##  ######     #\n"   +
                      " ".repeat(espacoEsq) + "##      ##   ##  ##   ##  ##   ##  #####\n" +
                      " ".repeat(espacoEsq) + "##      #####    ##   ##  ##   ##    #\n"   +
                      " ".repeat(espacoEsq) + "######  ##  ##    #####   ######   praTi\n";

        return logo;

    }

}