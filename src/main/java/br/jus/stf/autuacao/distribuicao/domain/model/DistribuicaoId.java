package br.jus.stf.autuacao.distribuicao.domain.model;

import java.io.Serializable;

import javax.persistence.Column;

import br.jus.stf.core.framework.domaindrivendesign.ValueObjectSupport;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 12.02.2016
 */
public class DistribuicaoId extends ValueObjectSupport<DistribuicaoId> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "SEQ_DISTRIBUICAO")
    private Long id;
    
    public DistribuicaoId(Long id) {
        this.id = id;
    }
    
    public DistribuicaoId() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
    }

    public Long toLong() {
        return id;
    }
    
    @Override
    public String toString(){
        return toLong().toString();
    }
    


}
