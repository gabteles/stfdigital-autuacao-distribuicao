package br.jus.stf.autuacao.distribuicao.domain;

import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.distribuicao.domain.model.Distribuicao;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoComum;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoId;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoPrevencao;
import br.jus.stf.autuacao.distribuicao.domain.model.ParametroDistribuicao;
import br.jus.stf.autuacao.distribuicao.domain.model.Status;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 04.02.2016
 */
@Component
public class DistribuicaoFactory {
    
    public Distribuicao novaDistribuicao(DistribuicaoId distribuicaoId, ParametroDistribuicao parametros, Status status) {
    	Distribuicao distribuicao;
    	
		switch (parametros.tipoDistribuicao()) {
		case COMUM:
			distribuicao = new DistribuicaoComum(distribuicaoId, parametros.processoId(),
					parametros.ministrosCandidatos(), parametros.ministrosImpedidos(), status);
			break;
		case PREVENCAO:
			distribuicao = new DistribuicaoPrevencao(distribuicaoId, parametros.processoId(), parametros.processosPreventos(), status);
			break;
		default:
			throw new IllegalArgumentException("Tipo de distribuição inexistente: " + parametros.tipoDistribuicao());
		}
		
    	return distribuicao;
    }

}
