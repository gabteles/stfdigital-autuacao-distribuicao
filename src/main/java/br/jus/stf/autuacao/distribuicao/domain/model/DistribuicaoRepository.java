package br.jus.stf.autuacao.distribuicao.domain.model;


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

}
