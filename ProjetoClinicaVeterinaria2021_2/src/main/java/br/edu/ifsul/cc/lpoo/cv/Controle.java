
package br.edu.ifsul.cc.lpoo.cv;

import br.edu.ifsul.cc.lpoo.cv.gui.JFramePrincipal;
import br.edu.ifsul.cc.lpoo.cv.gui.JMenuBarHome;
import br.edu.ifsul.cc.lpoo.cv.gui.funcionario.JPanelFuncionario;
import br.edu.ifsul.cc.lpoo.cv.gui.funcionario.acessibilidade.JPanelAFuncionario;
import br.edu.ifsul.cc.lpoo.cv.gui.home.JPanelHome;
import br.edu.ifsul.cc.lpoo.cv.gui.autenticacao.JPanelAutenticacao;
import br.edu.ifsul.cc.lpoo.cv.gui.raca.JPanelRaca;
import br.edu.ifsul.cc.lpoo.cv.gui.raca.acessibilidade.JPanelARaca;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJDBC;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJPA;

import javax.swing.JOptionPane;

/**
 *
 * @author telmo
 */
public class Controle {
    
    private JFramePrincipal frame;
    
    private JPanelAutenticacao pnlAutenticacao;
    
    private JMenuBarHome menuBar; //menu principal
    
    private JPanelHome pnlHome; // painel de boas vindas (home)

    private PersistenciaJDBC conexaoJDBC;

    private JPanelFuncionario pnlFuncionario; // painel de manutencao para funcionario
    private JPanelAFuncionario pnlAFuncionario; // painel de manutencao para funcionario

    private JPanelRaca pnlRaca; // painel de manutencao para raca
    private JPanelARaca pnlARaca; // painel de manutencao para raca

    //construtor.
    public Controle(){
                        
    }

    public void initComponents(){

        frame = new JFramePrincipal(this);                
        
        pnlAutenticacao = new JPanelAutenticacao(this);
        
        menuBar = new JMenuBarHome(this);
        
        pnlHome = new JPanelHome(this);

        pnlFuncionario = new JPanelFuncionario(this);

        pnlAFuncionario = new JPanelAFuncionario(this);

        pnlRaca = new JPanelRaca(this);

        pnlARaca = new JPanelARaca(this);
        
        frame.addTela(pnlAutenticacao, "tela_autenticacao");//carta 1
        frame.addTela(pnlHome, "tela_home");//carta 2

        frame.addTela(pnlAFuncionario, "tela_funcionario");//carta 3 - poderia adicionar opcionalmente: pnlJogador
        frame.addTela(pnlFuncionario, "tela_funcionario_designer");//carta 3 - poderia adicionar opcionalmente: pnlJogador

        frame.addTela(pnlARaca, "tela_raca");//carta 3 - poderia adicionar opcionalmente: pnlJogador
        frame.addTela(pnlRaca, "tela_raca_designer");//carta 3 - poderia adicionar opcionalmente: pnlJogador

        frame.showTela("tela_autenticacao"); // apreseta a carta cujo nome é "tela_autenticacao"
        
        frame.setVisible(true); // torna visível o jframe
        
//        autenticar("10001347088", "1234");

    }
    
    public void autenticar(String param1, String param2) {
        try{

            Pessoa p =  getConexaoJDBC().doLogin(param1, param2);
            
            if(p != null){

                JOptionPane.showMessageDialog(pnlAutenticacao, "Funcionário "+p.getNome()+" autenticado com sucesso!", "Autenticação", JOptionPane.INFORMATION_MESSAGE);

                pnlHome.setNome(p);
                frame.setJMenuBar(menuBar);//adiciona o menu de barra no frame
                frame.showTela("tela_home");//muda a tela para o painel de boas vindas (home)

            }else{

                JOptionPane.showMessageDialog(pnlAutenticacao, "Dados inválidos!", "Autenticação", JOptionPane.INFORMATION_MESSAGE);
            }

        }catch(Exception e){

            JOptionPane.showMessageDialog(pnlAutenticacao, "Erro ao executar a autenticação no Banco de Dados!", "Autenticação", JOptionPane.ERROR_MESSAGE);
            
            e.printStackTrace();
        }
        
    }
    
    public void showTela(String nomeTela){
            if(nomeTela.equals("tela_autenticacao")){
                //pnlAutenticacao.cleanForm();
                pnlAutenticacao.requestFocus();
            }else if(nomeTela.equals("tela_funcionario")){
                pnlAFuncionario.showTela("tela_funcionario_listagem");
            }else if(nomeTela.equals("tela_raca")){
                pnlARaca.showTela("tela_raca_listagem");
            }
            frame.showTela(nomeTela);            
            pnlAutenticacao.requestFocus();
    }
        
    
    public boolean conectarBD() throws Exception {
            conexaoJDBC = new PersistenciaJDBC();

            if(getConexaoJDBC()!= null){
                        return getConexaoJDBC().conexaoAberta();
            }

            return false;
    }
    
    public void fecharBD(){
        System.out.println("Fechando conexao com o Banco de Dados");
        getConexaoJDBC().fecharConexao();
    }
    
    /**
     * @return the conexaoJDBC
     */
    public PersistenciaJDBC getConexaoJDBC() {
        return conexaoJDBC;
    }
    
}
