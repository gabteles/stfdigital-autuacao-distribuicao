package br.jus.stf.autuacao.distribuicao.domain.model;

import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 08.08.2016
 */
public interface OrganizarPecaRepository {

	// Peça
	/**
	 * @return
	 */
	Long nextPecaId();
	
    /**
     * @param id
     * @return
     */
    Peca findOnePeca(Long id);
    
    // Processo
    /**
     * @param entity
     * @return
     */
    <P extends Processo> P save(P entity);
    
    /**
     * @param id
     * @return
     */
    Processo findOne(ProcessoId id);

}
