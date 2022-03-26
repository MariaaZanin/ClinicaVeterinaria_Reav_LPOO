/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.cc.lpoo.cv;

import javax.swing.JOptionPane;

/**
 *
 * @author telmo
 */
public class ClinicaVeterinaria {
    
    private Controle controle;
    
    public ClinicaVeterinaria(){
        
                //primeiramente - estabelecer uma conexao com o banco de dados.
        
        
        try {
                controle = new Controle();//cria a instancia e atribui para o atributo controle.

                //"caminho feliz" : passo 3
                if(controle.conectarBD()){

                    //"caminho feliz" : passo 4
                    controle.initComponents();

                }else{

                        JOptionPane.showMessageDialog(null, "Não conectou no Banco de Dados!", "Banco de Dados", JOptionPane.ERROR_MESSAGE);
                }

        } catch (Exception ex) {

                JOptionPane.showMessageDialog(null, "Erro ao tentar conectar no Banco de Dados: "+ex.getLocalizedMessage(), "Banco de Dados", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
        }
        
    }
    
    public static void main(String args[]){
        
        new ClinicaVeterinaria();
    }
    
}
