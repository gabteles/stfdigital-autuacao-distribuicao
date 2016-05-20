package br.jus.stf.autuacao.distribuicao.infra;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.distribuicao.domain.model.Distribuicao;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoId;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoRepository;
import br.jus.stf.autuacao.distribuicao.domain.model.FilaDistribuicao;
import br.jus.stf.autuacao.distribuicao.domain.model.Processo;
import br.jus.stf.core.shared.processo.ProcessoId;

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

    /** Distribuição **/
    @Override
    public DistribuicaoId nextDistribuicaoId() {
    	Query q = entityManager.createNativeQuery("SELECT distribuicao.seq_distribuicao.NEXTVAL FROM DUAL");
    	
    	return new DistribuicaoId(((BigInteger) q.getSingleResult()).longValue());
    }
    
    /** Processo **/
    @SuppressWarnings("unchecked")
	@Override
    public <S extends Processo> S saveProcesso(Processo entity) {
    	return (S)entityManager.merge(entity);
    }
    
    @Override
    public Processo findOneProcesso(ProcessoId id) {
    	TypedQuery<Processo> q = entityManager.createQuery("FROM Processo proc WHERE proc.processoId = :id", Processo.class);
    	
    	q.setParameter("id", id);
    	return q.getSingleResult();
    }
    
    /** Fila de distribuição **/
    @SuppressWarnings("unchecked")
    @Override
	public <F extends FilaDistribuicao> F saveFilaDistribuicao(FilaDistribuicao entity) {
    	return (F)entityManager.merge(entity);
    }
    
    public FilaDistribuicao findOneFilaDistribuicao(DistribuicaoId id) {
    	TypedQuery<FilaDistribuicao> q = entityManager.createQuery("FROM FilaDistribuicao fila WHERE fila.distribuicaoId = :id", FilaDistribuicao.class);
    	
    	q.setParameter("id", id);
    	return q.getSingleResult();
    }

}
