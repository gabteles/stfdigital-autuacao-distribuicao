package br.jus.stf.autuacao.distribuicao.domain;

import br.jus.stf.autuacao.distribuicao.interfaces.dto.ProcessoDto;

@FunctionalInterface
public interface ProcessoAdapter {

	/**
	 * Consultar processo autuado
	 * @param processoId
	 * @return
	 */
	ProcessoDto consultar(Long processoId);
	
}
