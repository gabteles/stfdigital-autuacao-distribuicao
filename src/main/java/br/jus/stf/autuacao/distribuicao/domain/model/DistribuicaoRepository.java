package br.jus.stf.autuacao.distribuicao.domain.model;

import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.02.2016
 */
public interface DistribuicaoRepository {

    // Distribuição
	/**
	 * @param entity
	 * @return
	 */
	<S extends Distribuicao> S save(S entity);
    
    /**
     * @param id
     * @return
     */
    Distribuicao findOne(DistribuicaoId id);
    
    /**
     * @return
     */
    DistribuicaoId nextDistribuicaoId();
    
    // Processo
    /**
     * @param entity
     * @return
     */
    <P extends ProcessoDistribuido> P saveProcesso(ProcessoDistribuido entity);
    
    /**
     * @param id
     * @return
     */
    ProcessoDistribuido findOneProcesso(ProcessoId id);
    
    // Fila de distribuição
    /**
     * @param entity
     * @return
     */
    <F extends FilaDistribuicao> F saveFilaDistribuicao(FilaDistribuicao entity);
    
    /**
     * @param id
     * @return
     */
    FilaDistribuicao findOneFilaDistribuicao(DistribuicaoId id);

}
