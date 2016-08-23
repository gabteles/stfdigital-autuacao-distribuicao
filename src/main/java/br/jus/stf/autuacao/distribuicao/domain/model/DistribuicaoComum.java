package br.jus.stf.autuacao.distribuicao.domain.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.Validate;

import br.jus.stf.autuacao.distribuicao.domain.model.identidade.Ministro;
import br.jus.stf.autuacao.distribuicao.domain.model.identidade.MinistroRepository;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 19.05.2016
 */
@Entity
@DiscriminatorValue("COMUM")
public class DistribuicaoComum extends Distribuicao {
    
	@OneToMany(cascade = ALL, fetch = EAGER)
	@JoinTable(name = "MINISTRO_CANDIDATO", schema = "DISTRIBUICAO", joinColumns = @JoinColumn(name = "SEQ_DISTRIBUICAO", nullable = false), inverseJoinColumns = @JoinColumn(name = "COD_MINISTRO", nullable = false))
	private Set<Ministro> ministrosCandidatos = new HashSet<>(0);
	
	@OneToMany(cascade = ALL, fetch = EAGER)
	@JoinTable(name = "MINISTRO_IMPEDIDO", schema = "DISTRIBUICAO", joinColumns = @JoinColumn(name = "SEQ_DISTRIBUICAO", nullable = false), inverseJoinColumns = @JoinColumn(name = "COD_MINISTRO", nullable = false))
	private Set<Ministro> ministrosImpedidos = new HashSet<>(0);
    
    DistribuicaoComum() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
    }
    
    /**
	 * @param filaDistribuicao
	 * @param ministroRepository
	 * @param ministrosCandidatos
	 * @param ministrosImpedidos
	 */
	public DistribuicaoComum(FilaDistribuicao filaDistribuicao, MinistroRepository ministroRepository,
			Set<Ministro> ministrosCandidatos, Set<Ministro> ministrosImpedidos) {
        super(filaDistribuicao);
        
        Validate.notNull(ministroRepository, "Repositorório de ministros requerido.");
		Validate.notEmpty(ministrosCandidatos, "Ministros candidatos requeridos.");
		Validate.notNull(ministrosImpedidos, "Ministros impedidos requeridos.");
		Validate.isTrue(isListasValidas(ministroRepository, ministrosCandidatos, ministrosImpedidos),
				"As listas de candidatos e impedidos estão sobrepostas ou não contêm todos os ministros.");
        
        this.ministrosCandidatos = ministrosCandidatos;
        this.ministrosImpedidos = ministrosImpedidos;
    }
	
	@Override
	public Ministro sorteio() {
		int indice = new Random().nextInt(ministrosCandidatos.size());
		
		return (Ministro) ministrosCandidatos.toArray()[indice];
	}
	
	@Override
	public TipoDistribuicao tipo() {
		return TipoDistribuicao.COMUM;
	}
	
	private boolean isListasValidas(MinistroRepository ministroRepository, Set<Ministro> candidatos, Set<Ministro> impedidos) {
		if (ministroRepository.count() != candidatos.size() + impedidos.size()) {
			return false;
		}
    	
		Set<Ministro> intersecaoCandidatoImpedido = new HashSet<>(candidatos);
		Set<Ministro> intersecaoImpedidoCandidato = new HashSet<>(impedidos);
		
		intersecaoCandidatoImpedido.retainAll(impedidos);
		intersecaoImpedidoCandidato.retainAll(candidatos);
		
		return intersecaoCandidatoImpedido.isEmpty() &&	intersecaoImpedidoCandidato.isEmpty();
	}
    
}