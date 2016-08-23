package br.jus.stf.autuacao.distribuicao.domain.model;

import java.io.Serializable;

import javax.persistence.Column;

import br.jus.stf.core.framework.domaindrivendesign.ValueObjectSupport;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 23.08.2016
 */
public class PecaId extends ValueObjectSupport<PecaId> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "SEQ_PECA")
    private Long id;
    
    PecaId() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
    }
    
    /**
     * @param id
     */
    public PecaId(Long id) {
        this.id = id;
    }

    /**
     * @return
     */
    public Long toLong() {
        return id;
    }
    
    @Override
    public String toString(){
        return toLong().toString();
    }
    


}
