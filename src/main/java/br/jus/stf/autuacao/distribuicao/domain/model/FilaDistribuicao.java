package br.jus.stf.autuacao.distribuicao.domain.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;
import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.05.2016
 */
@Entity
@Table(name = "FILA_DISTRIBUICAO", schema = "DISTRIBUICAO")
public class FilaDistribuicao extends EntitySupport<FilaDistribuicao, DistribuicaoId> {
    
    @EmbeddedId
    private DistribuicaoId distribuicaoId;
    
    @Embedded
    @Column(nullable = false)
    private ProcessoId processoId;
    
    @Column(name = "TIP_STATUS", nullable = false)
   	@Enumerated(EnumType.STRING)
    private Status status;
    
    public FilaDistribuicao() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
    }
    
    public FilaDistribuicao(DistribuicaoId distribuicaoId, ProcessoId processoId, Status status) {
    	Validate.notNull(distribuicaoId, "Distribuição requerida.");
    	Validate.notNull(processoId, "Processo requerido.");
    	Validate.notNull(status, "Status requerido.");
    	
        this.distribuicaoId = distribuicaoId;
        this.processoId = processoId;
        this.status = status;
    }
    
    @Override
    public DistribuicaoId identity() {
    	return distribuicaoId;
    }
    
    public ProcessoId processo() {
    	return processoId;
    }
    
    public Status status() {
    	return status;
    }
    
    public void alterarStatus(Status status) {
    	Validate.notNull(status, "Status requerido.");
    	
    	this.status = status;
    }
    
}
