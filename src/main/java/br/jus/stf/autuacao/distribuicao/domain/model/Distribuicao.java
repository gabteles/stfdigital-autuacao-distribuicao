package br.jus.stf.autuacao.distribuicao.domain.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.autuacao.distribuicao.domain.model.identidade.Ministro;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIP_DISTRIBUICAO")
@Table(name = "DISTRIBUICAO", schema = "DISTRIBUICAO")
public abstract class Distribuicao extends EntitySupport<Distribuicao, DistribuicaoId> implements AggregateRoot<Distribuicao, DistribuicaoId> {
    
    @EmbeddedId
    private DistribuicaoId distribuicaoId;
    
    @Embedded
    @Column(nullable = false)
    private ProcessoId processoId;
    
    @Column(name = "TIP_STATUS", nullable = false)
   	@Enumerated(EnumType.STRING)
    private Status status;
    
    @ManyToOne
	@JoinColumn(name = "COD_MINISTRO")
	private Ministro relator;
    
    @Embedded
    private Distribuidor distribuidor;
    
    @Column(name = "DAT_DISTRIBUICAO")
    private Date dataDistribuicao;
    
    @Column(name = "TXT_JUSTIFICATIVA")
	private String justificativa;
    
    Distribuicao() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
    }
    
    /**
     * @param filaDistribuicao
     */
    public Distribuicao(FilaDistribuicao filaDistribuicao) {
    	Validate.notNull(filaDistribuicao, "Fila de distribuição requerida.");
    	
        this.distribuicaoId = filaDistribuicao.identity();
        this.processoId = filaDistribuicao.processo();
        this.status = filaDistribuicao.status();
    }
    
    /**
     * @return
     */
    public abstract Ministro sorteio();
    
    /**
     * @return
     */
    public abstract TipoDistribuicao tipo();
    
    /**
     * @return
     */
    public ProcessoId processo() {
    	return processoId;
    }
    
    /**
     * @return
     */
    public Ministro relator() {
    	return relator;
    }
    
    /**
     * @param distribuidor
     * @param status
     */
    public void executar(Distribuidor distribuidor) {
		Validate.notNull(distribuidor, "Distribuidor requerido.");
		
        this.distribuidor = distribuidor;
        relator = sorteio();
    	dataDistribuicao = new Date();
    }
    
    /**
     * @param justificativa
     */
    public void justificar(String justificativa) {
    	Validate.notEmpty(justificativa, "Justificativa requerida.");
    	this.justificativa = justificativa;
    }
    
    /**
     * @param status
     */
    public void alterarStatus(Status status) {
    	Validate.notNull(status, "Status requerido.");
    	
    	this.status = status;
    }

    @Override
    public DistribuicaoId identity() {
        return distribuicaoId;
    }
    
}
