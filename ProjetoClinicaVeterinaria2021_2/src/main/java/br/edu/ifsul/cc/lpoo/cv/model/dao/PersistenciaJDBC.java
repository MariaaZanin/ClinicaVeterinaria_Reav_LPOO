
package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Cargo;
import br.edu.ifsul.cc.lpoo.cv.model.Especie;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import br.edu.ifsul.cc.lpoo.cv.model.Raca;
import br.edu.ifsul.cc.lpoo.cv.model.TipoProduto;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 *
 * @author Prof. Telmo (Reavaliação da Primeira Etapa - 17/03/2022 - LPOO 2021/2)
 */
public class PersistenciaJDBC implements InterfacePersistencia{
    
    private final String DRIVER = "org.postgresql.Driver";
    private final String USER = "postgres";
    private final String SENHA = "123456";
    public static final String URL = "jdbc:postgresql://localhost:5432/db_cv_reavaliacao_2_lpoo_2021_2";
    private Connection con = null;


    public PersistenciaJDBC() throws Exception{
        Class.forName(DRIVER); //carregamento do driver postgresql em tempo de execução
        System.out.println("Tentando estabelecer conexao JDBC com : "+URL+" ...");
            
        this.con = (Connection) DriverManager.getConnection(URL, USER, SENHA);

//        PreparedStatement ps = this.con.prepareStatement("select * from pg_catalog.pg_tables where schemaname='public'");
//
//        ResultSet rs = ps.executeQuery();
//        System.out.println(rs.toString());
    }

    @Override
    public Boolean conexaoAberta() {
        try {
            if(con != null)
                return !con.isClosed();//verifica se a conexao está aberta
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        return false;
        
    }

    @Override
    public void fecharConexao() {
        try{                               
            this.con.close();//fecha a conexao.
            System.out.println("Fechou conexao JDBC");
        }catch(SQLException e){            
            e.printStackTrace();//gera uma pilha de erro na saida.
        } 
        
    }

    @Override
    public Object find(Class c, Object id) throws Exception {
        if(c == Funcionario.class){

            PreparedStatement ps = this.con.prepareStatement("select f.cargo, f.numero_ctps, f.numero_pis, f.cpf, p.cep, p.complemento, p.data_cadastro, " +
                    "p.data_nascimento, p.email, p.endereco, p.nome, p.numero_celular, p.rg, p.senha from tb_funcionario f, tb_pessoa p " +
                    "where p.cpf = f.cpf and f.cpf = ?");
            ps.setString(1, id.toString());

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Funcionario f = new Funcionario();
                f.setCargo(Cargo.valueOf(rs.getString("cargo")));
                f.setNumero_ctps(rs.getString("Numero_ctps"));
                f.setNumero_pis(rs.getString("Numero_pis"));
                f.setCpf(rs.getString("cpf"));
                f.setCep(rs.getString("CEP"));
                f.setComplemento(rs.getString("Complemento"));
                Calendar dtCad = Calendar.getInstance();
                dtCad.setTimeInMillis(rs.getDate("Data_Cadastro").getTime());
                f.setData_cadastro(dtCad);
                Calendar dtNas = Calendar.getInstance();
                dtNas.setTimeInMillis(rs.getDate("Data_Nascimento").getTime());
                f.setData_nascimento(dtNas);
                f.setEmail(rs.getString("Email"));
                f.setEndereco(rs.getString("Endereco"));
                f.setNome(rs.getString("Nome"));
                f.setNumero_celular(rs.getString("Numero_Celular"));
                f.setRg(rs.getString("RG"));
                f.setSenha(rs.getString("Senha"));
                ps.close();

                return f;
            }
        }else if(c == Raca.class){

            PreparedStatement ps = this.con.prepareStatement("select r.id, r.nome, r.especie_id, e.nome nome_esp from tb_raca r inner join tb_especie e on"
                    + " r.especie_id = e.id where r.id = ?");
            ps.setInt(1, Integer.parseInt(id.toString()));

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                Raca r = new Raca();
                r.setId(rs.getInt("id"));
                r.setNome(rs.getString("nome"));
                Especie e = new Especie();
                e.setId(rs.getInt("especie_id"));
                e.setNome(rs.getString("nome_esp"));
                r.setEspecie(e);

                ps.close();
                return r;
            }

        }else if(c == Especie.class){

            PreparedStatement ps = this.con.prepareStatement("select id, nome from tb_especie where id = ? ");
            ps.setInt(1, Integer.parseInt(id.toString()));

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                Especie e = new Especie();
                e.setId(rs.getInt("id"));
                e.setNome(rs.getString("nome"));

                ps.close();

                return e;
            }

        }

        return null;
    }

    @Override
    public void persist(Object o) throws Exception {
        if (o instanceof Funcionario) {
            Funcionario f = (Funcionario) o;
            if (f.getData_cadastro() == null) {
                PreparedStatement ps_pessoa = this.con.prepareStatement("insert into tb_pessoa"
                        + " (cpf, rg, nome, senha, numero_celular, email, cep, endereco, complemento, data_cadastro, data_nascimento, tipo)"
                        + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, 'F')");
                ps_pessoa.setString(1, f.getCpf());
                ps_pessoa.setString(2, f.getRg());
                ps_pessoa.setString(3, f.getNome());
                ps_pessoa.setString(4, f.getSenha());
                ps_pessoa.setString(5, f.getNumero_celular());
                ps_pessoa.setString(6, f.getEmail());
                ps_pessoa.setString(7, f.getCep());
                ps_pessoa.setString(8, f.getEndereco());
                ps_pessoa.setString(9, f.getComplemento());
                ps_pessoa.setTimestamp(10, new Timestamp(f.getData_nascimento().getTimeInMillis()));

                ps_pessoa.execute();

                PreparedStatement psf = this.con.prepareStatement("insert into tb_funcionario "
                        + "(cargo, numero_ctps, numero_pis, cpf) values (?, ?, ?, ?) ");
                psf.setString(1, f.getCargo().toString());
                psf.setString(2, f.getNumero_ctps());
                psf.setString(3, f.getNumero_pis());
                psf.setString(4, f.getCpf());

                psf.execute();
                //System.out.println("O Funcionario com CPF = " + f.getCpf() + " foi cadastrado com sucesso!\n");
            } else {
                PreparedStatement ps = this.con.prepareStatement("update tb_pessoa set rg = ?, nome = ?, senha = ?, numero_celular = ?, "
                        + "email = ?, cep= ?, endereco = ?, complemento = ?, data_nascimento = ?, tipo = 'F'"
                        + "where cpf = ?");
                ps.setString(1, f.getRg());
                ps.setString(2, f.getNome());
                ps.setString(3, f.getSenha());
                ps.setString(4, f.getNumero_celular());
                ps.setString(5, f.getEmail());
                ps.setString(6, f.getCep());
                ps.setString(7, f.getEndereco());
                ps.setString(8, f.getComplemento());
                ps.setTimestamp(9, new Timestamp(f.getData_nascimento().getTimeInMillis()));
                ps.setString(10, f.getCpf());

                PreparedStatement psf = this.con.prepareStatement("update tb_funcionario set cargo = ?, numero_ctps = ?, numero_pis = ? where cpf = ?");
                psf.setString(1, f.getCargo().toString());
                psf.setString(2, f.getNumero_ctps());
                psf.setString(3, f.getNumero_pis());
                psf.setString(4, f.getCpf());

                ps.execute();
                psf.execute();
            }
        }else if(o instanceof Raca) {
            Raca r = (Raca) o;

            if(r.getId() == null) {
                PreparedStatement ps = this.con.prepareStatement("insert into tb_raca (id, nome, especie_id) values (nextval('seq_raca_id'), ?, ?);",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, r.getNome());
                ps.setInt(2, r.getEspecie().getId());

                ps.execute();

                ResultSet rs = ps.getGeneratedKeys();

                if(rs.next()) {
                    r.setId(rs.getInt(1));
                }
            } else {
                PreparedStatement ps = this.con.prepareStatement("update tb_raca set nome = ?, especie_id = ? where id = ?;");
                ps.setString(1, r.getNome());
                ps.setInt(2, r.getEspecie().getId());
                ps.setInt(3, r.getId());

                ps.execute();
            }

        }else if(o instanceof Especie) {
            Especie e = (Especie) o; // Converter o "o" para Produto
            // Descobrir qual operacao deve realizar (insert ou update)
            if(e.getId() == null) {
                PreparedStatement ps = this.con.prepareStatement("insert into tb_especie (id, nome) values (nextval('seq_raca_id'), ?);",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, e.getNome());

                ps.execute();

                ResultSet rs = ps.getGeneratedKeys();

                if(rs.next()) {
                    e.setId(rs.getInt(1));
                }
            } else {
                PreparedStatement ps = this.con.prepareStatement("update tb_especie set nome = ? where id = ?;");
                ps.setString(1, e.getNome());
                ps.setInt(2, e.getId());

                ps.execute();
            }

        }
    }

    @Override
    public void remover(Object o) throws Exception {
        if(o instanceof Funcionario) {
            Funcionario f = (Funcionario) o;
            PreparedStatement ps1 = this.con.prepareStatement("delete from tb_funcionario where cpf = ?");// deleta a informação da tabela receita_fornecedor
            ps1.setString(1, f.getCpf());
            ps1.execute();

            PreparedStatement ps2 = this.con.prepareStatement("delete from tb_pessoa where cpf = ?");// deleta a informação da tabela receita_pessoa
            ps2.setString(1, f.getCpf());
            ps2.execute();
        }else if(o instanceof Raca){
            Raca r = (Raca) o;

            PreparedStatement ps = this.con.prepareStatement("delete from tb_raca where id = ? ");
            ps.setInt(1, r.getId());
            ps.execute();

        }else if(o instanceof Especie){
            Especie e = (Especie) o;

            PreparedStatement ps = this.con.prepareStatement("delete from tb_especie where id = ? ");
            ps.setInt(1, e.getId());
            ps.execute();

        }

    }


    @Override
    public Pessoa doLogin(String cpf, String senha) throws Exception {

        Pessoa f = null;

        PreparedStatement ps =
                this.con.prepareStatement("select p.cpf, p.nome, p.senha from tb_pessoa p " +
                        " inner join tb_funcionario f ON p.cpf = f.cpf where p.cpf = ? and p.senha = ? ");

        ps.setString(1, cpf);
        ps.setString(2, senha);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            f = new Pessoa();
            f.setCpf(rs.getString("cpf"));
            f.setNome(rs.getString("nome"));
            f.setSenha(rs.getString("senha"));
        }

        ps.close();
        return f;

    }

    @Override
    public List<Especie> listEspecies() throws Exception {
        List<Especie> lista = null;

        PreparedStatement ps = this.con.prepareStatement("select id, nome from tb_especie");

        ResultSet rs = ps.executeQuery();

        lista = new ArrayList();
        while(rs.next()) {
            Especie e = new Especie();
            e.setId(rs.getInt("id"));
            e.setNome(rs.getString("nome"));

            lista.add(e);
        }

        return lista;
    }

    @Override
    public List<Raca> listRacas() throws Exception {
        List<Raca> lista = null;

        PreparedStatement ps = this.con.prepareStatement("select r.id, r.nome, r.especie_id, e.nome nome_esp from tb_raca r inner join tb_especie e on"
                + " r.especie_id = e.id");

        ResultSet rs = ps.executeQuery();

        lista = new ArrayList();
        while(rs.next()) {
            Raca r = new Raca();
            r.setId(rs.getInt("id"));
            r.setNome(rs.getString("nome"));
            Especie e = new Especie();
            e.setId(rs.getInt("especie_id"));
            e.setNome(rs.getString("nome_esp"));
            r.setEspecie(e);

            lista.add(r);
        }

        return lista;

    }

    @Override
    public List<Funcionario> listFuncionario() throws Exception {
        List<Funcionario> lista = null;

        PreparedStatement ps = this.con.prepareStatement("select pes.cpf, pes.cep, pes.complemento, "
                + "pes.data_nascimento, pes.data_cadastro, pes.email, pes.endereco, pes.nome, pes.numero_celular,"
                + " pes.rg, pes.senha, func.cargo, func.numero_ctps, func.numero_pis from tb_pessoa as pes INNER JOIN tb_funcionario as func "
                + "on pes.cpf = func.cpf;\n");

        ResultSet rs = ps.executeQuery();

        lista = new ArrayList();
        while(rs.next()) {
            Funcionario func = new Funcionario();
            func.setCpf(rs.getString("cpf"));
            func.setComplemento(rs.getString("complemento"));
            func.setCep(rs.getString("cep"));

            Calendar dtCadastro = Calendar.getInstance();
            dtCadastro.setTimeInMillis(rs.getDate("data_cadastro").getTime());
            func.setData_cadastro(dtCadastro);
            if(rs.getDate("data_nascimento") != null) {
                Calendar dtNascimento = Calendar.getInstance();
                dtNascimento.setTimeInMillis(rs.getDate("data_nascimento").getTime());
                func.setData_nascimento(dtNascimento);
            }

            func.setEmail(rs.getString("email"));
            func.setEndereco(rs.getString("endereco"));
            func.setNome(rs.getString("nome"));
            func.setNumero_celular(rs.getString("numero_celular"));
            func.setRg(rs.getString("rg"));
            func.setSenha(rs.getString("senha"));
            func.setCargo(Cargo.valueOf(rs.getString("cargo")));
            func.setNumero_ctps(rs.getString("numero_ctps"));
            func.setNumero_pis(rs.getString("numero_pis"));

            lista.add(func);
        }

        return lista;
     }
    
}
