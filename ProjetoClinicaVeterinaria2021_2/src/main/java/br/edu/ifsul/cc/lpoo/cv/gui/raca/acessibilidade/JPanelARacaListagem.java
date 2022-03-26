package br.edu.ifsul.cc.lpoo.cv.gui.raca.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import br.edu.ifsul.cc.lpoo.cv.model.Raca;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;
import java.util.regex.PatternSyntaxException;

public class JPanelARacaListagem extends JPanel implements ActionListener{

    private JPanelARaca pnlARaca;
    private Controle controle;

    private BorderLayout borderLayout;
    private JPanel pnlNorte;
    private JLabel lblFiltro;
    private JTextField txfFiltro;
    private JButton btnFiltro;

    private JPanel pnlCentro;
    private JScrollPane scpListagem;
    private JTable tblListagem;
    private DefaultTableModel modeloTabela;

    private JPanel pnlSul;
    private JButton btnNovo;
    private JButton btnAlterar;
    private JButton btnRemover;

    private SimpleDateFormat format;

    public JPanelARacaListagem(JPanelARaca pnlARaca, Controle controle){

        this.pnlARaca = pnlARaca;
        this.controle = controle;

        initComponents();
    }

    public void populaTable(){
        DefaultTableModel model =  (DefaultTableModel) tblListagem.getModel();//recuperacao do modelo da tabela

        model.setRowCount(0);//elimina as linhas existentes (reset na tabela)
        try{
            List<Raca> listRaca = controle.getConexaoJDBC().listRacas();
            for(Raca r: listRaca){
                model.addRow(new Object[]{r.getId(), r.getNome(), r.getEspecie()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar Raca:"+ex.getLocalizedMessage(), "Racas", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void initComponents(){

        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);//seta o gerenciado border para este painel

        pnlNorte = new JPanel();
        pnlNorte.setLayout(new FlowLayout());

        lblFiltro = new JLabel("Filtrar por nome de Raca:");
        pnlNorte.add(lblFiltro);

        txfFiltro = new JTextField(20);
        pnlNorte.add(txfFiltro);

        btnFiltro = new JButton("Filtrar");
        btnFiltro.addActionListener(this);
        btnFiltro.setFocusable(true);    //acessibilidade
        btnFiltro.setToolTipText("btnFiltrar"); //acessibilidade
        btnFiltro.setActionCommand("botao_filtro");
        pnlNorte.add(btnFiltro);

        this.add(pnlNorte, BorderLayout.NORTH);//adiciona o painel na posicao norte.

        pnlCentro = new JPanel();
        pnlCentro.setLayout(new BorderLayout());

        scpListagem = new JScrollPane();
        tblListagem =  new JTable();

        modeloTabela = new DefaultTableModel(
                new String [] { "ID", "Nome", "Nome da Especie",}, 0);

        tblListagem.setModel(modeloTabela);
        scpListagem.setViewportView(tblListagem);

        pnlCentro.add(scpListagem, BorderLayout.CENTER);

        this.add(pnlCentro, BorderLayout.CENTER);//adiciona o painel na posicao norte.

        filtrar();

        pnlSul = new JPanel();
        pnlSul.setLayout(new FlowLayout());

        btnNovo = new JButton("Novo");
        btnNovo.addActionListener(this);
        btnNovo.setFocusable(true);    //acessibilidade
        btnNovo.setToolTipText("btnNovo"); //acessibilidade
        btnNovo.setMnemonic(KeyEvent.VK_N);
        btnNovo.setActionCommand("botao_novo");

        pnlSul.add(btnNovo);

        btnAlterar = new JButton("Editar");
        btnAlterar.addActionListener(this);
        btnAlterar.setFocusable(true);  //acessibilidade
        btnAlterar.setToolTipText("btnAlterar"); //acessibilidade
        btnAlterar.setActionCommand("botao_alterar");

        pnlSul.add(btnAlterar);

        btnRemover = new JButton("Remover");
        btnRemover.addActionListener(this);
        btnRemover.setFocusable(true); //acessibilidade
        btnRemover.setToolTipText("btnRemvoer"); //acessibilidade
        btnRemover.setActionCommand("botao_remover");

        pnlSul.add(btnRemover);//adiciona o botao na fila organizada pelo flowlayout

        this.add(pnlSul, BorderLayout.SOUTH);//adiciona o painel na posicao norte.

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

        if(arg0.getActionCommand().equals(btnNovo.getActionCommand())){

            pnlARaca.showTela("tela_raca_formulario");

            pnlARaca.getFormulario().setRacaFormulario(null);

        }else if(arg0.getActionCommand().equals(btnAlterar.getActionCommand())){
            int indice = tblListagem.getSelectedRow();
            if(indice > -1){
                try {
                    Raca r = new Raca();
                    DefaultTableModel model = (DefaultTableModel) tblListagem.getModel();
                    Vector linha = (Vector) model.getDataVector().get(indice);
                    r = (Raca) controle.getConexaoJDBC().find(r.getClass(), linha.get(0));
                    pnlARaca.showTela("tela_raca_formulario");
                    pnlARaca.getFormulario().setRacaFormulario(r);
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(this, "Erro ao editar Raca -:"+ex.getLocalizedMessage(), "Racas", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }else{
                JOptionPane.showMessageDialog(this, "Selecione uma linha para editar!", "Edição", JOptionPane.INFORMATION_MESSAGE);
            }


        }else if(arg0.getActionCommand().equals(btnRemover.getActionCommand())){
            int indice = tblListagem.getSelectedRow();
            if(indice > -1){
                try {
                    DefaultTableModel model =  (DefaultTableModel) tblListagem.getModel();
                    Vector linha = (Vector) model.getDataVector().get(indice);
                    Integer s = (Integer) linha.get(0);
                    Raca r = new Raca();
                    r = (Raca) controle.getConexaoJDBC().find(r.getClass(), s);
                    pnlARaca.getControle().getConexaoJDBC().remover(r);
                    JOptionPane.showMessageDialog(this, "Raca removido!", "Raca", JOptionPane.INFORMATION_MESSAGE);
                    populaTable(); //refresh na tabela
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao remover Raca -:"+ex.getLocalizedMessage(), "Racas", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }else{
                JOptionPane.showMessageDialog(this, "Selecione uma linha para remover!", "Remoção", JOptionPane.INFORMATION_MESSAGE);
            }

        }

    }

    public void filtrar(){
        final TableRowSorter<TableModel> ordenador = new TableRowSorter<TableModel>(modeloTabela);
        tblListagem.setRowSorter(ordenador);
        btnFiltro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = txfFiltro.getText();
                if(text.length() == 0) {
                    ordenador.setRowFilter(null);
                } else {
                    try {
                        ordenador.setRowFilter(RowFilter.regexFilter(text, 1));
                    } catch(PatternSyntaxException pse) {
                        System.out.println("Erro ao filtrar");
                    }
                }
            }
        });
    }
}
