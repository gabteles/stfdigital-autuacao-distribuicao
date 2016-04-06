package br.jus.stf.autuacao.distribuicao.domain.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import br.jus.stf.core.framework.domaindrivendesign.AggregateRoot;
import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;
import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.02.2016
 */
@Entity
@Table(name = "DISTRIBUICAO", schema = "DISTRIBUICAO")
public class Distribuicao extends EntitySupport<Distribuicao, DistribuicaoId> implements AggregateRoot<Distribuicao, DistribuicaoId> {
    
    @EmbeddedId
    private DistribuicaoId distribuicaoId;
    
    @Column
    private ProcessoId processoId;
    
    @Column(name = "TIP_STATUS")
   	@Enumerated(EnumType.STRING)
    private Status status;
    
    public Distribuicao() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
    }
    
    public Distribuicao(DistribuicaoId distribuicaoId, ProcessoId processoId, Status status) {
        this.distribuicaoId = distribuicaoId;
        this.processoId = processoId;
        this.status = status;
    }
    
    public void relator(Status status) {
        this.status = status;
    }

    @Override
    public DistribuicaoId identity() {
        return distribuicaoId;
    }
    
}
