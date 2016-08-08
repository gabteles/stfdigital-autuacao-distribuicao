package br.jus.stf.autuacao.distribuicao.domain.model.documento;

import java.util.List;

import br.jus.stf.core.shared.documento.TipoDocumentoId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 03.08.2016
 */
public interface TipoPecaRepository {

	/**
	 * @return
	 */
	List<TipoPeca> findAll();
	
	/**
	 * @param id
	 * @return
	 */
	TipoPeca findOne(TipoDocumentoId id);

}
