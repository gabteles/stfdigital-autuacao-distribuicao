package br.jus.stf.autuacao.distribuicao.domain.model.identidade;

import java.util.List;

import br.jus.stf.core.shared.identidade.MinistroId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 02.05.2016
 */
public interface MinistroRepository {

	/**
	 * @return
	 */
	List<Ministro> findAll();
	
	/**
	 * @param id
	 * @return
	 */
	Ministro findOne(MinistroId id);

}
