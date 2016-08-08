package br.jus.stf.autuacao.distribuicao.domain.model;

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
	<D extends Distribuicao> D save(D entity);
    
    /**
     * @param id
     * @return
     */
    Distribuicao findOne(DistribuicaoId id);
    
    /**
     * @return
     */
    DistribuicaoId nextDistribuicaoId();
    
    // Fila de distribuição
    /**
     * @param entity
     * @return
     */
    <F extends FilaDistribuicao> F saveFilaDistribuicao(F entity);
    
    /**
     * @param id
     * @return
     */
    FilaDistribuicao findOneFilaDistribuicao(DistribuicaoId id);

}
