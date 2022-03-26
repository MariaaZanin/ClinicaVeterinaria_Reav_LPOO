package br.edu.ifsul.cc.lpoo.cv.gui.raca.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import br.edu.ifsul.cc.lpoo.cv.gui.raca.acessibilidade.JPanelARacaFormulario;
import br.edu.ifsul.cc.lpoo.cv.gui.raca.acessibilidade.JPanelARacaListagem;

import javax.swing.*;
import java.awt.*;

public class JPanelARaca extends JPanel {

    private CardLayout cardLayout;
    private Controle controle;

    private JPanelARacaFormulario formulario;
    private JPanelARacaListagem listagem;

    public JPanelARaca(Controle controle){

        this.controle = controle;
        initComponents();
    }

    private void initComponents(){

        cardLayout = new CardLayout();
        this.setLayout(cardLayout);

        formulario = new JPanelARacaFormulario(this, controle);
        listagem = new JPanelARacaListagem(this, controle);

        this.add(formulario, "tela_raca_formulario");
        this.add(listagem, "tela_raca_listagem");

        cardLayout.show(this, "tela_raca_listagem");
    }

    public void showTela(String nomeTela){
        if(nomeTela.equals("tela_raca_listagem")){
            listagem.populaTable();
        }else if(nomeTela.equals("tela_raca_formulario")){
            getFormulario().populaEspecie();
        }

        cardLayout.show(this, nomeTela);
    }

    /**
     * @return the controle
     */
    public Controle getControle() {
        return controle;
    }
    public JPanelARacaFormulario getFormulario() {
        return formulario;
    }

}
