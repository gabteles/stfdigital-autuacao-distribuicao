package br.jus.stf.autuacao.distribuicao.domain.model;

import static javax.persistence.FetchType.EAGER;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

import org.apache.commons.lang3.Validate;

import br.jus.stf.autuacao.distribuicao.domain.model.identidade.MinistroRepository;
import br.jus.stf.core.shared.identidade.MinistroId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 19.05.2016
 */
@Entity
@DiscriminatorValue("COMUM")
public class DistribuicaoComum extends Distribuicao {
    
	@ElementCollection(fetch = EAGER)
	@CollectionTable(name = "MINISTRO_CANDIDATO", schema = "DISTRIBUICAO", joinColumns = @JoinColumn(name = "SEQ_DISTRIBUICAO", nullable = false))
	private Set<MinistroId> ministrosCandidatos = new HashSet<>(0);
	
	@ElementCollection(fetch = EAGER)
	@CollectionTable(name = "MINISTRO_IMPEDIDO", schema = "DISTRIBUICAO", joinColumns = @JoinColumn(name = "SEQ_DISTRIBUICAO", nullable = false))
	private Set<MinistroId> ministrosImpedidos = new HashSet<>(0);
    
    DistribuicaoComum() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
    }
    
    /**
	 * @param filaDistribuicao
	 * @param ministroRepository
	 * @param ministrosCandidatos
	 * @param ministrosImpedidos
	 */
	public DistribuicaoComum(FilaDistribuicao filaDistribuicao, MinistroRepository ministroRepository, Set<MinistroId> ministrosCandidatos, Set<MinistroId> ministrosImpedidos) {
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
	public MinistroId sorteio() {
		int indice = new Random().nextInt(ministrosCandidatos.size());
		
		return (MinistroId) ministrosCandidatos.toArray()[indice];
	}
	
	@Override
	public TipoDistribuicao tipo() {
		return TipoDistribuicao.COMUM;
	}
	
	private boolean isListasValidas(MinistroRepository ministroRepository, Set<MinistroId> candidatos, Set<MinistroId> impedidos) {
		if (ministroRepository.count() != candidatos.size() + impedidos.size()) {
			return false;
		}
    	
		Set<MinistroId> intersecaoCandidatoImpedido = new HashSet<>(candidatos);
		Set<MinistroId> intersecaoImpedidoCandidato = new HashSet<>(impedidos);
		
		intersecaoCandidatoImpedido.retainAll(impedidos);
		intersecaoImpedidoCandidato.retainAll(candidatos);
		
		return intersecaoCandidatoImpedido.isEmpty() &&	intersecaoImpedidoCandidato.isEmpty();
	}
    
}