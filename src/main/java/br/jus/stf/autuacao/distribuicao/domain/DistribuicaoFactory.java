package br.jus.stf.autuacao.distribuicao.domain;

import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.distribuicao.domain.model.Distribuicao;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoComum;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoId;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoPrevencao;
import br.jus.stf.autuacao.distribuicao.domain.model.FilaDistribuicao;
import br.jus.stf.autuacao.distribuicao.domain.model.ParametroDistribuicao;
import br.jus.stf.autuacao.distribuicao.domain.model.Status;
import br.jus.stf.autuacao.distribuicao.domain.model.TipoDistribuicao;
import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 04.02.2016
 */
@Component
public class DistribuicaoFactory {
    
    public FilaDistribuicao novaFilaDistribuicao(DistribuicaoId distribuicaoId, ProcessoId processoId, Status status) {
    	return new FilaDistribuicao(distribuicaoId, processoId, status);
    }
    
    public Distribuicao novaDistribuicao(ParametroDistribuicao parametros) {
    	Distribuicao distribuicao;
    	FilaDistribuicao fila = parametros.fila();
		TipoDistribuicao tipoDistribuicao = parametros.tipoDistribuicao();
		
		switch(tipoDistribuicao) {
			case COMUM:
			distribuicao = new DistribuicaoComum(fila.identity(), fila.processo(), fila.status(),
					parametros.ministrosCandidatos(), parametros.ministrosImpedidos());
				break;
			case PREVENCAO:
			distribuicao = new DistribuicaoPrevencao(fila.identity(), fila.processo(), fila.status(),
					parametros.processosPreventos());
				break;
			default:
				throw new IllegalArgumentException("Tipo de distribuição inexistente: " + tipoDistribuicao);
		}
		return distribuicao;
    }

}
