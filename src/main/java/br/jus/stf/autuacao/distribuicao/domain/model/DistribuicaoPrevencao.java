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

import br.jus.stf.autuacao.distribuicao.domain.model.identidade.Ministro;

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
	private Set<Processo> processosPreventos = new HashSet<>(0);
    
    DistribuicaoPrevencao() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
    }
    
	/**
	 * @param filaDistribuicao
	 * @param justificativa
	 * @param processosPreventos
	 */
	public DistribuicaoPrevencao(FilaDistribuicao filaDistribuicao, String justificativa, Set<Processo> processosPreventos) {
		super(filaDistribuicao);
		super.justificar(justificativa);
		
        Validate.notEmpty(processosPreventos, "Processos preventos requeridos.");
		Validate.isTrue(isListaValida(processosPreventos), "Todos os processos preventos devem ser do mesmo relator.");
        
        this.processosPreventos.addAll(processosPreventos);
    }
	
	@Override
	public Ministro sorteio() {
		return processosPreventos.iterator().next().relator();
	}
	
	@Override
	public TipoDistribuicao tipo() {
		return TipoDistribuicao.PREVENCAO;
	}
    
    private boolean isListaValida(Set<Processo> processosPreventos) {
		Ministro relator = processosPreventos.iterator().next().relator();
		
		return processosPreventos.stream().allMatch(processo -> relator.equals(processo.relator()));
	}
    
}
