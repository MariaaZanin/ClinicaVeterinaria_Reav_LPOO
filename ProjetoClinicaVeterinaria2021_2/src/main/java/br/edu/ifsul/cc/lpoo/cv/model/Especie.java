
package br.edu.ifsul.cc.lpoo.cv.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author telmo
 */

@Entity
@Table(name = "tb_especie")
@NamedQueries({@NamedQuery(name="Especie.listbyId",
               query="SELECT e From Especie e order by e.id"), 
@NamedQuery(name="Especie.getbyNome",
               query="SELECT e From Especie e where e.nome = :nome")})        
public class Especie implements Serializable {
        
    @Id
    @SequenceGenerator(name = "seq_especie", sequenceName = "seq_especie_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_especie", strategy = GenerationType.SEQUENCE)   
    private Integer id;
    
    @Column(nullable = false, length = 100)
    private String nome;
    
    public Especie(){
        
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }


    @Override
    public String toString() {
        return nome;
    }
}
