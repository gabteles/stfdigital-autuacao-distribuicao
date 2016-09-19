package br.jus.stf.autuacao.distribuicao.domain;

import br.jus.stf.autuacao.distribuicao.interfaces.dto.DocumentoDto;

/**
 * 
 * @author viniciusk
 *
 */
@FunctionalInterface
public interface DocumentoAdapter {
	
	DocumentoDto consultar(Long documentoId);

}
