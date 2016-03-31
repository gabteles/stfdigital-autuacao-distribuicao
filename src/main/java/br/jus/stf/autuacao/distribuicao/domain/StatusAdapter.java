package br.jus.stf.autuacao.distribuicao.domain;

import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoId;
import br.jus.stf.autuacao.distribuicao.domain.model.Status;
import br.jus.stf.core.framework.workflow.StatusAdapterSupport;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.02.2016
 */
@Component("statusDistribuicaoAdapter")
public class StatusAdapter extends StatusAdapterSupport<DistribuicaoId, Status> {

    private static final String DISTRIBUICAO_PROCESS_KEY = "distribuicao";
    
    private static final String DISTRIBUICAO_ID_PATTERN = "DT:%s";

    @Override
    protected Status statusFromDescription(String description) {
        return Status.valueOf(description);
    }

    @Override
    protected String processId(DistribuicaoId informationId) {
        return String.format(DISTRIBUICAO_ID_PATTERN, informationId);
    }
    
    @Override
    protected String processKey() {
        return DISTRIBUICAO_PROCESS_KEY;
    }

}
