package br.edu.ifsul.cc.lpoo.cv.gui.funcionario.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import br.edu.ifsul.cc.lpoo.cv.model.Cargo;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import java.util.List;
import javax.swing.JOptionPane;

public class JPanelAFuncionarioFormulario extends JPanel implements ActionListener {

    private JPanelAFuncionario pnlAFuncionario;
    private Controle controle;

    private BorderLayout borderLayout;
    private JTabbedPane tbpAbas;

    private JPanel pnlDadosCadastrais;
    private JPanel pnlCentroDadosCadastrais;

    private GridBagLayout gridBagLayoutDadosCadastrais;
    private JLabel lblNickname;
    private JTextField txfNickname;

    private JLabel lblSenha;
    private JPasswordField txfSenha;

    private JLabel lblCargo;
    private JComboBox cbxCargo;

    private JLabel lblCtps;
    private JTextField txfCtps;

    private JLabel lblPis;
    private JTextField txfPis;

    private JLabel lblCpf;
    private JTextField txfCpf;

    private JLabel lblCep;
    private JTextField txfCep;

    private JLabel lblComplemento;
    private JTextField txfComplemento;

    private JLabel lblDataCadastro;
    private JTextField txfDataCadastro;

    private JLabel lblDataNascimento;
    private JTextField txfDataNascimento;

    private JLabel lblEmail;
    private JTextField txfEmail;

    private JLabel lblEndereco;
    private JTextField txfEndereco;

    private JLabel lblNumeroCelular;
    private JTextField txfNumeroCelular;

    private JLabel lblRg;
    private JTextField txfRg;

    private Funcionario funcionario;
    private SimpleDateFormat format;

    private JPanel pnlSul;
    private JButton btnGravar;
    private JButton btnCancelar;

    public JPanelAFuncionarioFormulario(JPanelAFuncionario pnlAFuncionario, Controle controle) {

        this.pnlAFuncionario = pnlAFuncionario;
        this.controle = controle;

        initComponents();
    }

    public void populaCargo() {
        cbxCargo.removeAllItems();
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbxCargo.getModel();

        model.addElement("Selecione");
        try {
            List<Cargo> listCargos = Arrays.asList(Cargo.values());

            listCargos.forEach(c -> {
                model.addElement(c);
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar os Cargos:"+e.getLocalizedMessage(), "Cargos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public Funcionario getFuncionariobyFormulario() {

        //validacao do formulario
        String msg = "";
        if(txfNickname.getText().trim().length() < 4)
            msg += "Nome invalido, informe um nome com ao menos 4 digitos \n";

        else if(new String(txfSenha.getPassword()).trim().length() < 4){
            msg += "Senha invalida, informe uma senha com ao menos 4 digitos \n";

        }else if(txfCpf.getText().trim().length() != 11){
            msg += "Cpf invalido, informe um CPF com 11 digitos \n";

        }else if(txfCep.getText().trim().length() != 8){
            msg += "CEP invalido, informe um CEP com 8 digitos \n";

        }else if(txfComplemento.getText().trim().length() < 5){
            msg += "Complemento invalido, informe um complemento com ao menos 5 digitos \n";

        }else if(txfEmail.getText().trim().length() < 10){
            msg += "Email invalido, informe um email com ao menos 10 digitos \n";

        }else if(txfEndereco.getText().trim().length() < 7){
            msg += "Endereco invalido, informe um endereco com ao menos 7 digitos \n";

        }else if(txfNumeroCelular.getText().trim().length() < 9){
            msg += "Telefone invalido, informe um email com ao menos 9 digitos \n";

        }else if(txfRg.getText().trim().length() != 10){
            msg += "RG invalido, informe um RG com 10 digitos \n";

        }else if(txfCtps.getText().trim().length() != 8){
            msg += "CTPS invalido, informe um CTPS com 8 digitos \n";

        }else if(txfPis.getText().trim().length() < 10){
            msg += "PIS invalido, informe um PIS com ao menos 10 digitos \n";
        } else {
            Calendar dtNascimento = Calendar.getInstance();
            try {
                DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                dtNascimento.setTime(formato.parse(txfDataNascimento.getText().trim()));
            } catch (Exception ex) {
                txfDataNascimento.requestFocus();
                msg = "Data de nascimento invalida, informe a data de nascimento no formato: dd/MM/yyyy \n";
            }
        }
        if(msg != ""){
            JOptionPane.showMessageDialog(this, msg);
        }else {

            Funcionario f = new Funcionario();
            f.setNome(txfNickname.getText().trim());
            f.setSenha(new String(txfSenha.getPassword()).trim());
            f.setCargo((Cargo) cbxCargo.getSelectedItem());
            f.setNumero_ctps(txfCtps.getText().trim());
            f.setNumero_pis(txfPis.getText().trim());
            f.setCpf(txfCpf.getText().trim());
            f.setCep(txfCep.getText().trim());
            f.setComplemento(txfComplemento.getText().trim());
            f.setEmail(txfEmail.getText().trim());
            f.setEndereco(txfEndereco.getText().trim());
            f.setNumero_celular(txfNumeroCelular.getText().trim());
            f.setRg(txfRg.getText().trim());
            Calendar data_nasc = Calendar.getInstance();

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            try {
                data_nasc.setTime(formato.parse(txfDataNascimento.getText().trim()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao gravar data nascimento");
                ex.printStackTrace();
            }
            f.setData_nascimento(data_nasc);

            if (funcionario != null) {
                f.setData_cadastro(funcionario.getData_cadastro());
            }
            return f;
        }

        return null;
    }

    public void setFuncionarioFormulario(Funcionario f) {

        if (f == null) {//se o parametro estiver nullo, limpa o formulario
            txfNickname.setText("");
            txfSenha.setText("");
            cbxCargo.setSelectedIndex(0);
            txfCtps.setText("");
            txfPis.setText("");
            txfCpf.setText("");
            txfCep.setText("");
            txfComplemento.setText("");
            txfDataNascimento.setText("");
            txfEmail.setText("");
            txfEndereco.setText("");
            txfNumeroCelular.setText("");
            txfRg.setText("");
            txfDataCadastro.setText("");

            txfNickname.setEditable(true);
            funcionario = null;
        } else {
            funcionario = f;
            txfNickname.setText(funcionario.getNome());
            txfSenha.setText(funcionario.getSenha());
            cbxCargo.getModel().setSelectedItem(funcionario.getCargo());
            txfCtps.setText(funcionario.getNumero_ctps());
            txfPis.setText(funcionario.getNumero_pis());
            txfCpf.setEditable(false);
            txfCpf.setText(funcionario.getCpf());
            txfCep.setText(funcionario.getCep());
            txfComplemento.setText(funcionario.getComplemento());
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            txfDataNascimento.setText(formato.format(funcionario.getData_nascimento().getTime()));
            txfEmail.setText(funcionario.getEmail());
            txfEndereco.setText(funcionario.getEndereco());
            txfNumeroCelular.setText(funcionario.getNumero_celular());
            txfRg.setText(funcionario.getRg());
            txfDataCadastro.setText(formato.format(funcionario.getData_cadastro().getTime()));
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

        lblNickname = new JLabel("Nome:");
        GridBagConstraints posicionador = new GridBagConstraints();
        posicionador.gridy = 0;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblNickname, posicionador);

        txfNickname = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfNickname, posicionador);

        lblSenha = new JLabel("Senha:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblSenha, posicionador);

        txfSenha = new JPasswordField(10);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfSenha, posicionador);

        lblCpf = new JLabel("CPF:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblCpf, posicionador);

        txfCpf = new JTextField(14);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfCpf, posicionador);

        lblCep = new JLabel("CEP:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblCep, posicionador);

        txfCep = new JTextField(9);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfCep, posicionador);

        lblComplemento = new JLabel("Complemento:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblComplemento, posicionador);

        txfComplemento = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfComplemento, posicionador);

        lblDataNascimento = new JLabel("Data de Nascimento:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblDataNascimento, posicionador);

        txfDataNascimento = new JTextField(10);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfDataNascimento, posicionador);

        lblDataCadastro = new JLabel("Data de Cadastro:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 6;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblDataCadastro, posicionador);

        txfDataCadastro = new JTextField(10);
        txfDataCadastro.setEditable(false);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 6;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfDataCadastro, posicionador);

        lblEmail = new JLabel("Email:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 7;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblEmail, posicionador);

        txfEmail = new JTextField(30);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 7;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfEmail, posicionador);

        lblEndereco = new JLabel("Endereço:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 8;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblEndereco, posicionador);

        txfEndereco = new JTextField(30);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 8;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfEndereco, posicionador);

        lblNumeroCelular = new JLabel("Número de Celular:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 9;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblNumeroCelular, posicionador);

        txfNumeroCelular = new JTextField(30);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 9;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfNumeroCelular, posicionador);

        lblRg = new JLabel("RG:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 10;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblRg, posicionador);

        txfRg = new JTextField(12);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 10;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfRg, posicionador);

        lblCargo = new JLabel("Cargo:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 11;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblCargo, posicionador);

        cbxCargo = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 11;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(cbxCargo, posicionador);

        lblCtps = new JLabel("Numero CTPS:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 12;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblCtps, posicionador);

        txfCtps = new JTextField(18);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 12;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfCtps, posicionador);

        lblPis = new JLabel("Numero Pis:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 13;
        posicionador.gridx = 0;
        pnlDadosCadastrais.add(lblPis, posicionador);

        txfPis = new JTextField(30);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 13;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlDadosCadastrais.add(txfPis, posicionador);

        tbpAbas.addTab("Dados Cadastrais", pnlDadosCadastrais);

        pnlSul = new JPanel();
        pnlSul.setLayout(new FlowLayout());

        btnGravar = new JButton("Gravar");
        btnGravar.addActionListener(this);
        btnGravar.setFocusable(true);    //acessibilidade
        btnGravar.setToolTipText("btnGravarFuncionario"); //acessibilidade
        btnGravar.setMnemonic(KeyEvent.VK_G);
        btnGravar.setActionCommand("botao_gravar_formulario_funcionario");

        pnlSul.add(btnGravar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        btnCancelar.setFocusable(true);    //acessibilidade
        btnCancelar.setToolTipText("btnCancelarFuncionario"); //acessibilidade
        btnCancelar.setActionCommand("botao_cancelar_formulario_funcionario");

        pnlSul.add(btnCancelar);

        this.add(pnlSul, BorderLayout.SOUTH);

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getActionCommand().equals(btnGravar.getActionCommand())) {

            Funcionario f = getFuncionariobyFormulario();//recupera os dados do formulÃ¡rio

            if (f != null) {
                try {
                    pnlAFuncionario.getControle().getConexaoJDBC().persist(f);
                    JOptionPane.showMessageDialog(this, "Funcionario armazenado!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
                    pnlAFuncionario.showTela("tela_funcionario_listagem");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar Funcionario! : " + ex.getMessage(), "Salvar", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }

            } else {

                JOptionPane.showMessageDialog(this, "Preencha o formulário!", "Edição", JOptionPane.INFORMATION_MESSAGE);
            }


        } else if (arg0.getActionCommand().equals(btnCancelar.getActionCommand())) {
            pnlAFuncionario.showTela("tela_funcionario_listagem");
        }
    }
}
