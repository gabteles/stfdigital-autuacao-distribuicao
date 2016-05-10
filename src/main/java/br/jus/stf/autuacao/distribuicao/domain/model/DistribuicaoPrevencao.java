package br.jus.stf.autuacao.distribuicao.domain.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * @since 03.05.2016
 */
@Entity
@DiscriminatorValue("PREVENCAO")
public class DistribuicaoPrevencao extends Distribuicao {
    
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinTable(name = "PROCESSO_PREVENTO", schema = "DISTRIBUICAO", joinColumns = @JoinColumn(name = "SEQ_DISTRIBUICAO", nullable = false), inverseJoinColumns = @JoinColumn(name = "SEQ_PROCESSO", nullable = false))
	private Set<Processo> processosPreventos = new HashSet<>(0);
    
    public DistribuicaoPrevencao() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
    }
    
	public DistribuicaoPrevencao(DistribuicaoId distribuicaoId, ProcessoId processoId, Set<Processo> processosPreventos, Status status) {
        super(distribuicaoId, processoId, status);
        
        Validate.notEmpty(processosPreventos, "Processos preventos requeridos.");
		Validate.isTrue(listaPreventosValida(processosPreventos),
				"Todos os processos preventos devem ser do mesmo relator.");
        
        this.processosPreventos = processosPreventos;
    }
	
	@Override
	public TipoDistribuicao tipo() {
		return TipoDistribuicao.PREVENCAO;
	}
    
    @Override
    protected MinistroId sorteio() {
    	return processosPreventos.iterator().next().relator();
	}
    
    private boolean listaPreventosValida(Set<Processo> processosPreventos) {
		MinistroId relator = processosPreventos.iterator().next().relator();
		
		return processosPreventos.stream().allMatch(processo -> relator.equals(processo.relator()));
	}
    
}
