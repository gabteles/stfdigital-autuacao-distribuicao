package br.jus.stf.autuacao.distribuicao.interfaces;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.distribuicao.application.DistribuicaoApplicationService;
import br.jus.stf.autuacao.distribuicao.application.commands.IniciarDistribuicaoCommand;
import br.jus.stf.autuacao.distribuicao.infra.RabbitConfiguration;
import br.jus.stf.core.shared.eventos.AutuacaoFinalizada;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 02.02.2016
 */
@Component
public class AutuacaoFinalizadaEventHandler {
    
    @Autowired
    private DistribuicaoApplicationService distribuicaoApplicationService;
    
    @RabbitListener(queues = RabbitConfiguration.AUTUACAO_FINALIZADA_QUEUE)
    public void handle(AutuacaoFinalizada event) {
        distribuicaoApplicationService.handle(new IniciarDistribuicaoCommand(event.getProcessoId()));
    }

}
