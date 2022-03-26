package br.edu.ifsul.cc.lpoo.cv.gui.raca;

import br.edu.ifsul.cc.lpoo.cv.Controle;

import javax.swing.*;
import java.awt.*;

public class JPanelRaca extends JPanel {

    private CardLayout cardLayout;
    private Controle controle;

    public JPanelRaca(Controle controle){
        this.controle = controle;
        initComponents();
    }

    private void initComponents(){
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);
    }

    public void showTela(String nomeTela){
        cardLayout.show(this, nomeTela);
    }

    /**
     * @return the controle
     */
    public Controle getControle() {
        return controle;
    }

}
