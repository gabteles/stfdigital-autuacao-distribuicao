package br.jus.stf.autuacao.distribuicao.infra;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.distribuicao.domain.model.OrganizarPecaRepository;
import br.jus.stf.autuacao.distribuicao.domain.model.Peca;
import br.jus.stf.autuacao.distribuicao.domain.model.Processo;
import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 08.08.2016
 */
@Repository
public class OrganizarPecaRepositoryImpl extends SimpleJpaRepository<Processo, ProcessoId> implements OrganizarPecaRepository {

    private EntityManager entityManager;

    /**
     * @param entityManager
     */
    @Autowired
    public OrganizarPecaRepositoryImpl(EntityManager entityManager) {
        super(Processo.class, entityManager);
        
        this.entityManager = entityManager;
    }

    // Peça
    @Override
    public Long nextPecaId() {
    	Query q = entityManager.createNativeQuery("SELECT distribuicao.seq_peca.NEXTVAL FROM DUAL");
    	
    	return ((BigInteger)q.getSingleResult()).longValue();
    }
    
    @Override
    public Peca findOnePeca(Long id) {
    	TypedQuery<Peca> q = entityManager.createQuery("FROM Peca peca WHERE peca.id = :id", Peca.class);
    	
    	q.setParameter("id", id);
    	
    	return q.getSingleResult();
    }

}