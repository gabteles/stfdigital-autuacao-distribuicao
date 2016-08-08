package br.jus.stf.autuacao.distribuicao.infra;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.distribuicao.domain.model.documento.TipoPeca;
import br.jus.stf.autuacao.distribuicao.domain.model.documento.TipoPecaRepository;
import br.jus.stf.core.shared.documento.TipoDocumentoId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 05.08.2016
 */
@Repository
public class TipoPecaRepositoryImpl extends SimpleJpaRepository<TipoPeca, TipoDocumentoId> implements TipoPecaRepository {

	/**
	 * @param entityManager
	 */
	@Autowired
    public TipoPecaRepositoryImpl(EntityManager entityManager) {
        super(TipoPeca.class, entityManager);
    }
    
}
