package br.jus.stf.autuacao.distribuicao.application;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import br.jus.stf.autuacao.distribuicao.application.commands.DistribuirProcessoCommand;
import br.jus.stf.autuacao.distribuicao.application.commands.IniciarDistribuicaoCommand;
import br.jus.stf.autuacao.distribuicao.domain.DistribuicaoFactory;
import br.jus.stf.autuacao.distribuicao.domain.DistribuidorAdapter;
import br.jus.stf.autuacao.distribuicao.domain.StatusAdapter;
import br.jus.stf.autuacao.distribuicao.domain.model.Distribuicao;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoId;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoRepository;
import br.jus.stf.autuacao.distribuicao.domain.model.Distribuidor;
import br.jus.stf.autuacao.distribuicao.domain.model.FilaDistribuicao;
import br.jus.stf.autuacao.distribuicao.domain.model.ParametroDistribuicao;
import br.jus.stf.autuacao.distribuicao.domain.model.ProcessoDistribuido;
import br.jus.stf.autuacao.distribuicao.domain.model.Status;
import br.jus.stf.autuacao.distribuicao.domain.model.TipoDistribuicao;
import br.jus.stf.autuacao.distribuicao.domain.model.identidade.MinistroRepository;
import br.jus.stf.core.framework.component.command.Command;
import br.jus.stf.core.framework.domaindrivendesign.ApplicationService;
import br.jus.stf.core.shared.identidade.MinistroId;
import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.02.2016
 */
@ApplicationService
public class DistribuicaoApplicationService {
    
    @Autowired
    private DistribuicaoFactory distribuicaoFactory;
    
    @Autowired
    private DistribuicaoRepository distribuicaoRepository;
    
    @Autowired
    private StatusAdapter statusAdapter;
    
    @Autowired
    private DistribuidorAdapter distribuidorAdapter;
    
    @Autowired
    private MinistroRepository ministroRepository;
    
    /**
     * @param command
     */
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(IniciarDistribuicaoCommand command) {
		DistribuicaoId distribuicaoId = distribuicaoRepository.nextDistribuicaoId();
		Status status = statusAdapter.nextStatus(distribuicaoId);
		FilaDistribuicao fila = distribuicaoFactory.novaFilaDistribuicao(distribuicaoId,
				new ProcessoId(command.getProcessoId()), status);

		distribuicaoRepository.saveFilaDistribuicao(fila);
		distribuicaoId.toLong();
    }

    /**
     * @param command
     */
    @Transactional
    @Command(description = "Distribuição")
    public void handle(DistribuirProcessoCommand command) {
    	FilaDistribuicao fila = distribuicaoRepository.findOneFilaDistribuicao(new DistribuicaoId(command.getDistribuicaoId()));
        Status status = statusAdapter.nextStatus(fila.identity());
        TipoDistribuicao tipo = TipoDistribuicao.valueOf(command.getTipoDistribuicao());
        Distribuidor distribuidor = distribuidorAdapter.distribuidor();
		Set<MinistroId> ministrosCandidatos = Optional.ofNullable(command.getMinistrosCandidatos()).isPresent() ? command
				.getMinistrosCandidatos().stream().map(ministro -> new MinistroId(ministro))
				.collect(Collectors.toSet()) : null;
		Set<MinistroId> ministrosImpedidos = Optional.ofNullable(command.getMinistrosImpedidos()).isPresent() ? command
				.getMinistrosImpedidos().stream().map(ministro -> new MinistroId(ministro))
				.collect(Collectors.toSet()) : null;
		Set<ProcessoDistribuido> processosPreventos = Optional.ofNullable(command.getProcessosPreventos()).isPresent() ? command
				.getProcessosPreventos().stream().map(processo -> distribuicaoRepository.findOneProcesso(new ProcessoId(processo)))
				.collect(Collectors.toSet()) : null;
		ParametroDistribuicao parametros = new ParametroDistribuicao(fila, tipo, command.getJustificativa(),
				ministrosCandidatos, ministrosImpedidos, processosPreventos);
		Distribuicao distribuicao = distribuicaoFactory.novaDistribuicao(parametros, ministroRepository);
        
        distribuicao.executar(parametros, distribuidor, status);
        distribuicaoRepository.save(distribuicao);
        distribuicaoRepository.saveProcesso(new ProcessoDistribuido(distribuicao.processo(),distribuicao.relator()));
        fila.alterarStatus(status);
        distribuicaoRepository.saveFilaDistribuicao(fila);
    }

}
