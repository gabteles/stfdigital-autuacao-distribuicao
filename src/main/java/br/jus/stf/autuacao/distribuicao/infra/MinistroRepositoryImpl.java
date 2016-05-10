package br.jus.stf.autuacao.distribuicao.infra;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.distribuicao.domain.model.identidade.Ministro;
import br.jus.stf.autuacao.distribuicao.domain.model.identidade.MinistroRepository;
import br.jus.stf.core.shared.identidade.MinistroId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 02.05.2016
 */
@Repository
public class MinistroRepositoryImpl extends SimpleJpaRepository<Ministro, MinistroId> implements MinistroRepository {

    @Autowired
    public MinistroRepositoryImpl(EntityManager entityManager) {
        super(Ministro.class, entityManager);
    }

}
