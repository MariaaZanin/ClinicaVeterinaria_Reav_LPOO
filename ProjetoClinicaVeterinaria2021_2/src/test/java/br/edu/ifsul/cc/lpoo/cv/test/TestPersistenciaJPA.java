
package br.edu.ifsul.cc.lpoo.cv.test;

import br.edu.ifsul.cc.lpoo.cv.model.Cargo;
import br.edu.ifsul.cc.lpoo.cv.model.Especie;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Raca;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJPA;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author Prof. Telmo (Reavaliação da Primeira Etapa - 17/03/2022 - LPOO 2021/2)
 */

public class TestPersistenciaJPA {
        
    @Test
    public void testConexaoGeracaoTabelas(){
        
        PersistenciaJPA persistencia = new PersistenciaJPA();                
        
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JPA");
            
            persistencia.fecharConexao();
            
        }else{
            System.out.println("Nao abriu a conexao com o BD via JPA");
        }
        
    }
    
    //@Test
    public void testGeracaoFuncionarioLogin() throws Exception{
        
        PersistenciaJPA persistencia = new PersistenciaJPA();        
        if(persistencia.conexaoAberta()){
            
            Funcionario f = (Funcionario) persistencia.find(Funcionario.class, new String("00001347080"));
            
            if(f == null){
                
                f = new Funcionario();
                f.setNome("Fulano de Tal");
                f.setCpf("10001347088");
                f.setRg("789456123");
                f.setData_cadastro(Calendar.getInstance());
                f.setNumero_ctps("123456789");
                f.setNumero_pis("123456789");
                f.setCargo(Cargo.ATENDENTE);
                f.setSenha("1234");
                
                persistencia.persist(f);
                
            }
            
            System.out.println("abriu a conexao com o BD via JPA");
            
            persistencia.fecharConexao();
            
        }else{
            System.out.println("Nao abriu a conexao com o BD via JPA");
        }
        
    }
    
    //@Test
    public void testGeracaoEspecies() throws Exception{
        
        PersistenciaJPA persistencia = new PersistenciaJPA();        
        if(persistencia.conexaoAberta()){
            
            List<Especie> list = persistencia.listEspecies();

            if(list != null && !list.isEmpty() ){

                for(Especie e : list){
                    System.out.println("Especie: "+e.getId()+ "Nome: "+e.getNome());
                }

            }else{

                Especie e = new Especie();
                e.setNome("Canis lupus");
                persistencia.persist(e);

                e = new Especie();
                e.setNome("Felis catus");
                persistencia.persist(e);

                e = new Especie();
                e.setNome("Oryctolagus cuniculus");
                persistencia.persist(e);

                System.out.println("Cadastrou especies ...");

            }

            System.out.println("abriu a conexao com o BD via JPA");
            
            persistencia.fecharConexao();
            
        }else{
            System.out.println("Nao abriu a conexao com o BD via JPA");
        }
        
    }
    //antes de executar esse metodo, tenha certeza que o método testGeracaoEspecies foi executado antes.
   
    //@Test
    public void testGeracaoRaca() throws Exception{
        
        PersistenciaJPA persistencia = new PersistenciaJPA();        
        if(persistencia.conexaoAberta()){
            
            List<Raca> list = persistencia.listRacas();
            
            if(list != null && !list.isEmpty() ){
                
                for(Raca e : list){
                    System.out.println("Raca: "+e.getId()+ "Nome: "+e.getNome());
                }                                
                
            }else{
                
                Raca r = new Raca();
                r.setNome("Dalmatas");
                r.setEspecie( (Especie) persistencia.find(Especie.class, 1));
                persistencia.persist(r);
                
                r = new Raca();
                r.setNome("Perça");
                r.setEspecie( (Especie) persistencia.find(Especie.class, 2));
                persistencia.persist(r);
                
                r = new Raca();
                r.setNome("N.Z.W.");
                r.setEspecie( (Especie) persistencia.find(Especie.class, 3));
                persistencia.persist(r);
                
                System.out.println("Cadastrou raças ...");
                
                                
            }
            
            System.out.println("abriu a conexao com o BD via JPA");
            
            persistencia.fecharConexao();
            
        }else{
            System.out.println("Nao abriu a conexao com o BD via JPA");
        }
        
    }
    
}
