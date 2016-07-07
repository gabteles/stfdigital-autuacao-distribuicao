package br.jus.stf.autuacao.distribuicao.domain.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.Validate;

import br.jus.stf.core.shared.identidade.MinistroId;
import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 19.05.2016
 */
@Entity
@DiscriminatorValue("PREVENCAO")
public class DistribuicaoPrevencao extends Distribuicao {
    
	@OneToMany(cascade = ALL, fetch = EAGER)
	@JoinTable(name = "PROCESSO_PREVENTO", schema = "DISTRIBUICAO", joinColumns = @JoinColumn(name = "SEQ_DISTRIBUICAO", nullable = false), inverseJoinColumns = @JoinColumn(name = "SEQ_PROCESSO", nullable = false))
	private Set<ProcessoDistribuido> processosPreventos = new HashSet<>(0);
    
    public DistribuicaoPrevencao() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
    }
    
	/**
	 * @param distribuicaoId
	 * @param processoId
	 * @param status
	 * @param processosPreventos
	 */
	public DistribuicaoPrevencao(DistribuicaoId distribuicaoId, ProcessoId processoId, Status status,
			Set<ProcessoDistribuido> processosPreventos) {
		super(distribuicaoId, processoId, status);
		
        Validate.notEmpty(processosPreventos, "Processos preventos requeridos.");
		Validate.isTrue(listaPreventosValida(processosPreventos),
				"Todos os processos preventos devem ser do mesmo relator.");
        
        this.processosPreventos.addAll(processosPreventos);
    }
	
	@Override
	public MinistroId sorteio() {
		return processosPreventos.iterator().next().relator();
	}
	
	@Override
	public TipoDistribuicao tipo() {
		return TipoDistribuicao.PREVENCAO;
	}
    
    private boolean listaPreventosValida(Set<ProcessoDistribuido> processosPreventos) {
		MinistroId relator = processosPreventos.iterator().next().relator();
		
		return processosPreventos.stream().allMatch(processo -> relator.equals(processo.relator()));
	}
    
}
