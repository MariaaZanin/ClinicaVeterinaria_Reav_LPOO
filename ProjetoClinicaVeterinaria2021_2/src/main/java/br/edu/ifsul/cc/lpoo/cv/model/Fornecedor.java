package br.edu.ifsul.cc.lpoo.cv.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author telmo
 */

@Entity
@Table(name = "tb_fornecedor")
@DiscriminatorValue("F")
public class Fornecedor extends Pessoa {
    
    @Column(nullable = false, length = 11)
    private String cnpj;
    
    @Column(nullable = false, length = 11)
    private String ie;
    
    
    public Fornecedor(){
        
        this.setTipo("F");
    }
    
}
