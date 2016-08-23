package br.jus.stf.autuacao.distribuicao.application;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import br.jus.stf.autuacao.distribuicao.application.commands.DistribuirProcessoComumCommand;
import br.jus.stf.autuacao.distribuicao.application.commands.DistribuirProcessoPrevencaoCommand;
import br.jus.stf.autuacao.distribuicao.application.commands.IniciarDistribuicaoCommand;
import br.jus.stf.autuacao.distribuicao.domain.DistribuidorAdapter;
import br.jus.stf.autuacao.distribuicao.domain.StatusAdapter;
import br.jus.stf.autuacao.distribuicao.domain.model.Distribuicao;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoComum;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoId;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoPrevencao;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoRepository;
import br.jus.stf.autuacao.distribuicao.domain.model.Distribuidor;
import br.jus.stf.autuacao.distribuicao.domain.model.FilaDistribuicao;
import br.jus.stf.autuacao.distribuicao.domain.model.OrganizarPecaRepository;
import br.jus.stf.autuacao.distribuicao.domain.model.Processo;
import br.jus.stf.autuacao.distribuicao.domain.model.Status;
import br.jus.stf.autuacao.distribuicao.domain.model.identidade.Ministro;
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
@Transactional
public class DistribuicaoApplicationService {
        
    @Autowired
    private DistribuicaoRepository distribuicaoRepository;
    
    @Autowired
    private MinistroRepository ministroRepository;
    
    @Autowired
    private StatusAdapter statusAdapter;
    
    @Autowired
    private DistribuidorAdapter distribuidorAdapter;
    
    @Autowired
    private OrganizarPecaRepository organizarPecaRepository;
    
    /**
     * @param command
     */
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(IniciarDistribuicaoCommand command) {
		DistribuicaoId distribuicaoId = distribuicaoRepository.nextDistribuicaoId();
		Status status = statusAdapter.nextStatus(distribuicaoId);
		
		ProcessoId processoId = new ProcessoId(command.getProcessoId());
		FilaDistribuicao fila = new FilaDistribuicao(distribuicaoId, processoId, status); 

		distribuicaoRepository.saveFilaDistribuicao(fila);
		distribuicaoId.toLong();
    }

    /**
     * @param command
     */
    @Command(value = "distribuir-processo", description = "Distribuição comum")
    public void handle(DistribuirProcessoComumCommand command) {
		Set<Ministro> ministrosCandidatos = command.getMinistrosCandidatos().stream()
				.map(ministroId -> ministroRepository.findOne(new MinistroId(ministroId))).collect(Collectors.toSet());
		Set<Ministro> ministrosImpedidos = command.getMinistrosImpedidos().stream()
				.map(ministroId -> ministroRepository.findOne(new MinistroId(ministroId))).collect(Collectors.toSet());

    	DistribuicaoId distribuicaoId = new DistribuicaoId(command.getDistribuicaoId());
    	FilaDistribuicao filaDistribuicao = configurarFilaDistribuicao(distribuicaoId);
    	
		Distribuicao distribuicao = new DistribuicaoComum(filaDistribuicao, ministroRepository, ministrosCandidatos, ministrosImpedidos);
		
		if(StringUtils.isNotBlank(command.getJustificativa())) {
			distribuicao.justificar(command.getJustificativa());
		}
		executarDistribuicao(filaDistribuicao, distribuicao);
    }
    
    /**
     * @param command
     */
    @Command(value = "distribuir-processo", description = "Distribuição por prevenção")
    public void handle(DistribuirProcessoPrevencaoCommand command) {
		Set<Processo> processosPreventos = command.getProcessosPreventos().stream()
				.map(processo -> organizarPecaRepository.findOne(new ProcessoId(processo)))
				.collect(Collectors.toSet());
    	
    	DistribuicaoId distribuicaoId = new DistribuicaoId(command.getDistribuicaoId());
    	FilaDistribuicao filaDistribuicao = configurarFilaDistribuicao(distribuicaoId);
    	String justificativa = command.getJustificativa();
    	
    	Distribuicao distribuicao = new DistribuicaoPrevencao(filaDistribuicao, justificativa, processosPreventos);
    	
    	executarDistribuicao(filaDistribuicao, distribuicao);
    }

	/**
	 * @param filaDistribuicao
	 * @param distribuicao
	 */
	private void executarDistribuicao(FilaDistribuicao filaDistribuicao, Distribuicao distribuicao) {
        Distribuidor distribuidor = distribuidorAdapter.distribuidor();
        distribuicao.executar(distribuidor);
        distribuicaoRepository.save(distribuicao);
        organizarPecaRepository.save(new Processo(distribuicao.processo(), distribuicao.relator()));
        distribuicaoRepository.saveFilaDistribuicao(filaDistribuicao);
	}

    /**
     * @param distribuicaoId
     * @return
     */
    private FilaDistribuicao configurarFilaDistribuicao(DistribuicaoId distribuicaoId) {
		FilaDistribuicao fila = distribuicaoRepository.findOneFilaDistribuicao(distribuicaoId);
        Status status = statusAdapter.nextStatus(fila.identity());
        fila.alterarStatus(status);
    	return fila;
    }
	
}
