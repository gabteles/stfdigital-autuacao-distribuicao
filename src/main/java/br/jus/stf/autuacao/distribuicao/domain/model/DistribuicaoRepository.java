package br.jus.stf.autuacao.distribuicao.domain.model;

import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.02.2016
 */
public interface DistribuicaoRepository {

    /** Distribuição **/
	<S extends Distribuicao> S save(S entity);
    
    Distribuicao findOne(DistribuicaoId id);
    
    DistribuicaoId nextDistribuicaoId();
    
    /** Processo **/
    <P extends ProcessoDistribuido> P saveProcesso(ProcessoDistribuido entity);
    
    ProcessoDistribuido findOneProcesso(ProcessoId id);
    
    /** Fila de distribuição **/
    <F extends FilaDistribuicao> F saveFilaDistribuicao(FilaDistribuicao entity);
    
    FilaDistribuicao findOneFilaDistribuicao(DistribuicaoId id);

}
