package br.jus.stf.autuacao.distribuicao.domain;

import br.jus.stf.autuacao.distribuicao.domain.model.Distribuidor;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 07.07.2016
 */
@FunctionalInterface
public interface DistribuidorAdapter {
	
	/**
	 * @return
	 */
	Distribuidor distribuidor();

}
