
package br.edu.ifsul.cc.lpoo.cv.test;

import br.edu.ifsul.cc.lpoo.cv.model.Cargo;
import br.edu.ifsul.cc.lpoo.cv.model.Especie;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Raca;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJDBC;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static br.edu.ifsul.cc.lpoo.cv.model.Cargo.ADESTRADOR;

/**
 *
 * @author Prof. Telmo (Reavaliação da Primeira Etapa - 17/03/2022 - LPOO 2021/2)
 */
public class TestPersistenciaJDBC {
    
    //@Test
    public void testConexao() throws Exception {
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JDBC");
            
            persistencia.fecharConexao();
            
        }else{
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }
        
    }

    //@Test
    public void testGeraFuncionario() throws Exception { //INSERE DADOS
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JDBC");

            List<Funcionario> lista = persistencia.listFuncionario();

            if(!lista.isEmpty()) {
                for(Funcionario f: lista) {
                    System.out.println("CPF: " + f.getCpf()+ " \tNome: " + f.getNome());
//                    persistencia.remover(f);
                }
            } else {
                System.out.println("Não encontrou o funcionario");

                Funcionario f = new Funcionario();
                f.setNome("Vitoria");
                f.setSenha("12345");
                f.setCpf("00000000000");
                f.setCep("00000000");
                f.setComplemento("avenida");
                f.setEmail("vitoria@gmail.com");
                f.setEndereco("Rua capitao");
                f.setNumero_celular("000000000");
                f.setRg("0000000000");
                f.setNumero_ctps("00000000");
                f.setNumero_pis("0000000");
                f.setCargo(ADESTRADOR);

                Calendar data_nasc = Calendar.getInstance();
                DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                data_nasc.setTime(formato.parse("30/09/2001"));
                f.setData_nascimento(data_nasc);
                persistencia.persist(f);

                System.out.println("Cadastrou novo Funcionario!");

            }

            persistencia.fecharConexao();
        }else{
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }
    }

    //@Test
    public void testGeraEspecie() throws Exception { //INSERE DADOS
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JDBC");

            List<Especie> lista = persistencia.listEspecies();

            if(!lista.isEmpty()) {
                for(Especie e: lista) {
                    System.out.println("ID: "+ e.getId() + " \tNome: " + e.getNome());
                    persistencia.remover(e);
                }
            } else {
                System.out.println("Não encontrou o Especie");

                Especie e = new Especie();
                e.setNome("Cachorro");

                persistencia.persist(e);

                System.out.println("Cadastrou nova Especie!");

            }

            persistencia.fecharConexao();
        }else{
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }
    }

    @Test
    public void testGeraRaca() throws Exception { //INSERE DADOS
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JDBC");

            List<Raca> lista = persistencia.listRacas();

            if(!lista.isEmpty()) {
                for(Raca r: lista) {
                    System.out.println("ID: "+ r.getId() + " \tNome: " + r.getNome() + " \tEspecie nome: " + r.getEspecie());
                    //persistencia.remover(e);
                }
            } else {
                System.out.println("Não encontrou a Raca");

                Raca r = new Raca();
                r.setNome("Persa");
                Especie e = new Especie();
                e.setId(19);
                r.setEspecie(e);

                persistencia.persist(r);

                System.out.println("Cadastrou nova Raca!");

            }

            persistencia.fecharConexao();
        }else{
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }
    }
    
}
