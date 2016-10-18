package br.jus.stf.autuacao.distribuicao.domain;

import java.util.List;

import br.jus.stf.autuacao.distribuicao.interfaces.dto.ProcessoConsultaDto;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.ProcessoDto;

public interface ProcessoAdapter {

	/**
	 * Consultar processo autuado
	 * @param processoId
	 * @return
	 */
	ProcessoDto consultar(Long processoId);
	
	/**
	 * Consulta prevenção por parte
	 * 
	 * @param parte
	 * @return
	 */
	List<ProcessoConsultaDto> consultarPrevencaoPorParte(String parte);
}
