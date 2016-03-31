package br.jus.stf.autuacao.distribuicao.domain;

import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.distribuicao.domain.model.Distribuicao;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoId;
import br.jus.stf.autuacao.distribuicao.domain.model.Status;
import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 04.02.2016
 */
@Component
public class DistribuicaoFactory {
    
    public Distribuicao novaDistribuicao(DistribuicaoId distribuicaoId, ProcessoId processoId, Status status) {
        return new Distribuicao(distribuicaoId, processoId, status);
    }

}
