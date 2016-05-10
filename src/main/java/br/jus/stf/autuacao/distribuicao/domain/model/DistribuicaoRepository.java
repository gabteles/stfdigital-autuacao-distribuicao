package br.jus.stf.autuacao.distribuicao.domain.model;

import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.02.2016
 */
public interface DistribuicaoRepository {

    <S extends Distribuicao> S save(S entity);
    
    Distribuicao findOne(DistribuicaoId id);
    
    DistribuicaoId nextDistribuicaoId();
    
    <S extends Processo> S saveProcesso(Processo entity);
    
    Processo findOneProcesso(ProcessoId id);

}
