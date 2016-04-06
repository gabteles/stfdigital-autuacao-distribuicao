package br.jus.stf.autuacao.distribuicao.application;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.jus.stf.autuacao.distribuicao.application.commands.DistribuirProcessoCommand;
import br.jus.stf.autuacao.distribuicao.application.commands.IniciarDistribuicaoCommand;
import br.jus.stf.autuacao.distribuicao.domain.DistribuicaoFactory;
import br.jus.stf.autuacao.distribuicao.domain.StatusAdapter;
import br.jus.stf.autuacao.distribuicao.domain.model.Distribuicao;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoId;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoRepository;
import br.jus.stf.autuacao.distribuicao.domain.model.Status;
import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.02.2016
 */
@Component
public class DistribuicaoApplicationService {
    
    @Autowired
    private DistribuicaoFactory distribuicaoFactory;
    
    @Autowired
    private DistribuicaoRepository distribuicaoRepository;
    
    @Autowired
    private StatusAdapter statusAdapter;
    
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(IniciarDistribuicaoCommand command) {
        DistribuicaoId distribuicaoId = distribuicaoRepository.nextDistribuicaoId();
        
        Status status = statusAdapter.nextStatus(distribuicaoId);

        Distribuicao distribuicao = distribuicaoFactory.novaDistribuicao(distribuicaoId, new ProcessoId(command.getProcessoId()), status);
        
        distribuicaoRepository.save(distribuicao);
    }

    @Transactional
    public void handle(DistribuirProcessoCommand command) {
        Distribuicao distribuicao = distribuicaoRepository.findOne(command.getDistribuicaoId());
        
        Status status = statusAdapter.nextStatus(distribuicao.identity());
        
        distribuicao.relator(status);
        
        distribuicaoRepository.save(distribuicao);
    }

}
