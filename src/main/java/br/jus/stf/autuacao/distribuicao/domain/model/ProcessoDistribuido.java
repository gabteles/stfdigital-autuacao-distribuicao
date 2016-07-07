package br.jus.stf.autuacao.distribuicao.domain.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.elasticsearch.common.lang3.Validate;

import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;
import br.jus.stf.core.shared.identidade.MinistroId;
import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 03.05.2016
 */
@Entity
@Table(name = "PROCESSO_DISTRIBUIDO", schema = "DISTRIBUICAO")
public class ProcessoDistribuido extends EntitySupport<ProcessoDistribuido, ProcessoId> {

	@EmbeddedId
	private ProcessoId processoId;
	
	@Embedded
    @Column(nullable = false)
	private MinistroId relator;
	
	public ProcessoDistribuido() {
		// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	/**
	 * @param processoId
	 * @param relator
	 */
	public ProcessoDistribuido(ProcessoId processoId, MinistroId relator) {
		Validate.notNull(processoId, "Id requerido");
		Validate.notNull(relator, "Relator requerido");
		
		this.processoId = processoId;	
		this.relator = relator;
	}
	
	/**
	 * @return
	 */
	public MinistroId relator() {
		return relator;
	}
	
	@Override
	public ProcessoId identity() {
		return processoId;
	}

}
