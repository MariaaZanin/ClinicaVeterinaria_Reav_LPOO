package br.edu.ifsul.cc.lpoo.cv.gui.raca.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import br.edu.ifsul.cc.lpoo.cv.model.Especie;
import br.edu.ifsul.cc.lpoo.cv.model.Raca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class JPanelARacaFormulario extends JPanel implements ActionListener {

    private JPanelARaca pnlARaca;
    private Controle controle;

    private BorderLayout borderLayout;
    private JTabbedPane tbpAbas;

    private JPanel pnlDadosCadastrais;
    private JPanel pnlCentroDadosCadastrais;

    private GridBagLayout gridBagLayoutDadosCadastrais;
    private JLabel lblId;
    private JTextField txfId;

    private JLabel lblNome;
    private JTextField txfNome;

    private JLabel lblEspecieId;
    private JComboBox cbxEspecieId;

    private Raca raca;
    private SimpleDateFormat format;

    private JPanel pnlSul;
    private JButton btnGravar;
    private JButton btnCancelar;

    String id = "";

    public JPanelARacaFormulario(JPanelARaca pnlARaca, Controle controle) {

        this.pnlARaca = pnlARaca;
        this.controle = controle;

        initComponents();
    }

    public void populaEspecie(){
        cbxEspecieId.removeAllItems();

        DefaultComboBoxModel model =  (DefaultComboBoxModel) cbxEspecieId.getModel();

        model.addElement("Selecione");
        try {

            List<Especie> listEspecie = controle.getConexaoJDBC().listEspecies();
            for(Especie especie : listEspecie){
                model.addElement(especie);
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Erro ao listar Especies -:"+ex.getLocalizedMessage(), "Especies", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public Raca getRacabyFormulario() {
        String msg = "", aux = "";

        if(txfNome.getText().trim().length() < 3){
            msg += "Nome invalido, informe um nome com ao menos 3 digitos \n";
        }else if(cbxEspecieId.getSelectedIndex() == 0){
            msg += "Selecione opção no combo box \n";
        }
        if(msg != ""){
            JOptionPane.showMessageDialog(this, msg);
        }else {

            Raca newR = new Raca();
            newR.setNome(txfNome.getText().trim());
            newR.setEspecie((Especie) cbxEspecieId.getSelectedItem());

            if(raca != null) {
                newR.setId(raca.getId());
            }

            return newR;
        }

        return null;
    }

    public void setRacaFormulario(Raca r) {
        if (r == null) {//se o parametro estiver nullo, limpa o formulario
            txfNome.setText("");
            txfId.setText("");
            cbxEspecieId.setSelectedIndex(0);

            raca = null;
        } else {
            raca = r;
            txfNome.setText(r.getNome());
            txfId.setText(r.getId().toString());
            cbxEspecieId.getModel().setSelectedItem(raca.getEspecie());
            System.out.println("RACAA: " + raca.getEspecie());
        }
    }

    private void initComponents() {

        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);

        tbpAbas = new JTabbedPane();
        this.add(tbpAbas, BorderLayout.CENTER);

        pnlDadosCadastrais = new JPanel();
        gridBagLayoutDadosCadastrais = new GridBagLayout();
        pnlDadosCadastrais.setLayout(gridBagLayoutDadosCadastrais);

        lblNome = new JLabel("Nome:");
        GridBagConstraints posicionador = new GridBagConstraints();
        posicionador.gridy = 0;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblNome, posicionador);

        txfNome = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfNome, posicionador);

        lblId = new JLabel("ID:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblId, posicionador);

        txfId = new JTextField(10);
        txfId.setEditable(false);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfId, posicionador);

        lblEspecieId = new JLabel("Especie:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblEspecieId, posicionador);

        cbxEspecieId = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(cbxEspecieId, posicionador);

        tbpAbas.addTab("Dados Cadastrais", pnlDadosCadastrais);

        pnlSul = new JPanel();
        pnlSul.setLayout(new FlowLayout());

        btnGravar = new JButton("Gravar");
        btnGravar.addActionListener(this);
        btnGravar.setFocusable(true);    
        btnGravar.setToolTipText("btnGravarRaca");
        btnGravar.setMnemonic(KeyEvent.VK_G);
        btnGravar.setActionCommand("botao_gravar_formulario_raca");

        pnlSul.add(btnGravar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        btnCancelar.setFocusable(true);
        btnCancelar.setToolTipText("btnCancelarraca");
        btnCancelar.setActionCommand("botao_cancelar_formulario_raca");

        pnlSul.add(btnCancelar);

        this.add(pnlSul, BorderLayout.SOUTH);

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getActionCommand().equals(btnGravar.getActionCommand())) {

            Raca r = getRacabyFormulario();

            if (r != null) {
                try {
                    pnlARaca.getControle().getConexaoJDBC().persist(r);
                    JOptionPane.showMessageDialog(this, "Raca armazenado!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
                    pnlARaca.showTela("tela_raca_listagem");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar Raca! : " + ex.getMessage(), "Salvar", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }

            } else {

                JOptionPane.showMessageDialog(this, "Preencha o formulário!", "Edição", JOptionPane.INFORMATION_MESSAGE);
            }


        } else if (arg0.getActionCommand().equals(btnCancelar.getActionCommand())) {
            pnlARaca.showTela("tela_raca_listagem");
        }
    }
}
