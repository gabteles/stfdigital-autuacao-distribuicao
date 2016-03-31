package br.jus.stf.autuacao.distribuicao.infra;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.distribuicao.domain.model.Distribuicao;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoId;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoRepository;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 13.02.2016
 */
@Repository
public class DistribuicaoRepositoryImpl extends SimpleJpaRepository<Distribuicao, DistribuicaoId> implements DistribuicaoRepository {

    private EntityManager entityManager;

    @Autowired
    public DistribuicaoRepositoryImpl(EntityManager entityManager) {
        super(Distribuicao.class, entityManager);
        
        this.entityManager = entityManager;
    }

    @Override
    public DistribuicaoId nextDistribuicaoId() {
    	Query q = entityManager.createNativeQuery("SELECT distribuicao.seq_distribuicao.NEXTVAL FROM DUAL");
    	
    	return new DistribuicaoId(((BigInteger) q.getSingleResult()).longValue());
    }

}
