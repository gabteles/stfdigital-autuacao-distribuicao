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
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;
import org.elasticsearch.common.lang3.StringUtils;

import br.jus.stf.core.framework.domaindrivendesign.AggregateRoot;
import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;
import br.jus.stf.core.shared.identidade.MinistroId;
import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.02.2016
 */
@Entity
@Table(name = "DISTRIBUICAO", schema = "DISTRIBUICAO")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIP_DISTRIBUICAO")
public abstract class Distribuicao extends EntitySupport<Distribuicao, DistribuicaoId> implements AggregateRoot<Distribuicao, DistribuicaoId> {
    
    @EmbeddedId
    private DistribuicaoId distribuicaoId;
    
    @Embedded
    @Column(nullable = false)
    private ProcessoId processoId;
    
    @Column(name = "TIP_STATUS", nullable = false)
   	@Enumerated(EnumType.STRING)
    private Status status;
    
    @Embedded
	private MinistroId relator;
    
    @Column(name = "TXT_JUSTIFICATIVA")
	private String justificativa;
    
    @Embedded
    private Distribuidor distribuidor;
    
    @Column(name = "DAT_DISTRIBUICAO")
    private Date dataDistribuicao;
    
    public Distribuicao() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
    }
    
    public Distribuicao(DistribuicaoId distribuicaoId, ProcessoId processoId, Status status) {
    	Validate.notNull(distribuicaoId, "Distribuição requerida.");
    	Validate.notNull(processoId, "Processo requerido.");
    	Validate.notNull(status, "Status requerido.");
    	
        this.distribuicaoId = distribuicaoId;
        this.processoId = processoId;
        this.status = status;
    }
    
    public ProcessoId processo() {
    	return processoId;
    }
    
    public MinistroId relator() {
    	return relator;
    }
    
    public abstract TipoDistribuicao tipo();
    
    protected abstract MinistroId sorteio();
    
    public void executar(ParametroDistribuicao parametros, Distribuidor distribuidor, Status status) {
		Validate.notNull(parametros, "Parâmetros requeridos.");
		Validate.notNull(distribuidor, "Distribuidor requerido.");
		Validate.notNull(status, "Status requerido.");
		Validate.isTrue(
				!parametros.tipoDistribuicao().exigeJustificativa() || !StringUtils.isEmpty(parametros.justificativa()),
				"Tipo de distribuição exige justificativa.");
    	
    	relator = sorteio();
    	justificativa = parametros.justificativa();
        this.distribuidor = distribuidor;
    	this.status = status;
    	dataDistribuicao = new Date();
    }

    @Override
    public DistribuicaoId identity() {
        return distribuicaoId;
    }
    
}
