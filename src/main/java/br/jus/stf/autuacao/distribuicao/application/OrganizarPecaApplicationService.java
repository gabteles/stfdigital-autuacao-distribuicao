package br.jus.stf.autuacao.distribuicao.application;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import br.jus.stf.autuacao.distribuicao.application.commands.CadastrarPecaCommand;
import br.jus.stf.autuacao.distribuicao.application.commands.DividirPecaCommand;
import br.jus.stf.autuacao.distribuicao.application.commands.EditarPecaCommand;
import br.jus.stf.autuacao.distribuicao.application.commands.ExcluirPecasCommand;
import br.jus.stf.autuacao.distribuicao.application.commands.InserirPecasCommand;
import br.jus.stf.autuacao.distribuicao.application.commands.JuntarPecaCommand;
import br.jus.stf.autuacao.distribuicao.application.commands.OrganizarPecasCommand;
import br.jus.stf.autuacao.distribuicao.application.commands.UnirPecasCommand;
import br.jus.stf.autuacao.distribuicao.domain.PecaAdapter;
import br.jus.stf.autuacao.distribuicao.domain.Situacao;
import br.jus.stf.autuacao.distribuicao.domain.StatusAdapter;
import br.jus.stf.autuacao.distribuicao.domain.Visibilidade;
import br.jus.stf.autuacao.distribuicao.domain.model.ControladorOrdenacaoPecas;
import br.jus.stf.autuacao.distribuicao.domain.model.Distribuicao;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoId;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoRepository;
import br.jus.stf.autuacao.distribuicao.domain.model.FilaDistribuicao;
import br.jus.stf.autuacao.distribuicao.domain.model.OrganizarPecaRepository;
import br.jus.stf.autuacao.distribuicao.domain.model.Peca;
import br.jus.stf.autuacao.distribuicao.domain.model.PecaId;
import br.jus.stf.autuacao.distribuicao.domain.model.Processo;
import br.jus.stf.autuacao.distribuicao.domain.model.Status;
import br.jus.stf.autuacao.distribuicao.domain.model.documento.TipoPeca;
import br.jus.stf.autuacao.distribuicao.domain.model.documento.TipoPecaRepository;
import br.jus.stf.core.framework.component.command.Command;
import br.jus.stf.core.framework.domaindrivendesign.ApplicationService;
import br.jus.stf.core.shared.documento.DocumentoId;
import br.jus.stf.core.shared.documento.DocumentoTemporarioId;
import br.jus.stf.core.shared.documento.TipoDocumentoId;
import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 05.08.2016
 */
@ApplicationService
@Transactional
public class OrganizarPecaApplicationService {
    
	@Autowired
    private DistribuicaoRepository distribuicaoRepository;
	
    @Autowired
    private OrganizarPecaRepository organizarPecasRepository;
    
    @Autowired
    private StatusAdapter statusAdapter;
    
    @Autowired
    private TipoPecaRepository tipoPecaRepository;
    
    @Autowired
    private PecaAdapter pecaAdapter;
    
    /**
	 * Insere peças em um processo
	 * @param command
	 */
    @Command(description = "Inserir peças.")
	public void handle(InserirPecasCommand command) {		
		Processo processo = organizarPecasRepository.findOne(new ProcessoId(command.getProcessoId()));
		
		command.getPecas().forEach(peca -> processo.adicionarPeca(criarPeca(peca)));
		organizarPecasRepository.save(processo);
	}
	
	/**
	 * Exclui peças de um processo.
	 * 
	 * @param command
	 */
    @Command(description = "Excluir peças.")
	public void handle(ExcluirPecasCommand command) {
		Processo processo = organizarPecasRepository.findOne(new ProcessoId(command.getProcessoId()));
		
		processo.pecas().forEach(peca -> command.getPecas().forEach(pecaExcluida -> {
			if (peca.identity().equals(pecaExcluida)) {
				processo.removerPeca(peca);
			}
		}));
		
		organizarPecasRepository.save(processo);
	}
	
	/**
	 * Atribui a peças uma nova organização no processo.
	 * 
	 * @param command
	 */
    @Command(description = "Organizar peças.", startProcess = true)
	public void handle(OrganizarPecasCommand command) {
		Distribuicao distribuicao = distribuicaoRepository.findOne(new DistribuicaoId(command.getDistribuicaoId()));
		Processo processo = organizarPecasRepository.findOne(distribuicao.processo());
		
		processo.organizarPecas(command.getPecas());
		organizarPecasRepository.save(processo);
		
		if (command.isFinalizarTarefa()) {
			Status status = statusAdapter.nextStatus(distribuicao.identity());
			FilaDistribuicao fila = distribuicaoRepository.findOneFilaDistribuicao(distribuicao.identity());
			
			fila.alterarStatus(status);
			distribuicao.alterarStatus(status);
			distribuicaoRepository.saveFilaDistribuicao(fila);
			distribuicaoRepository.save(distribuicao);
		}
	}
	
	/**
	 * Divide uma peça de um processo.
	 * 
	 * @param command
	 */
    @Command(description = "Dividir peça.")
	public void handle(DividirPecaCommand command) {
		Processo processo = organizarPecasRepository.findOne(new ProcessoId(command.getProcessoId()));
		Peca pecaOriginal = organizarPecasRepository.findOnePeca(new PecaId(command.getPecaOriginalId()));
		List<Range<Integer>> intervalos = new LinkedList<>();
		
		command.getPecas().forEach(peca -> intervalos.add(Range.between(peca.getPaginaInicial(), peca.getPaginaFinal())));
		
		List<DocumentoId> documentos = pecaAdapter.dividir(pecaOriginal.documento(), intervalos);
		List<Peca> novasPecas = new LinkedList<>();
				
		for (int i = 0; i < documentos.size(); i++) {
			TipoPeca tipoPeca = tipoPecaRepository.findOne(new TipoDocumentoId(command.getPecas().get(i).getTipoPecaId()));
			Visibilidade visibilidade = Visibilidade.valueOf(command.getPecas().get(i).getVisibilidade());
			Situacao situacao = Situacao.valueOf(command.getPecas().get(i).getSituacao());
			
			novasPecas.add(new Peca(organizarPecasRepository.nextPecaId(), documentos.get(i), tipoPeca,
					command.getPecas().get(i).getDescricao(), visibilidade, situacao));
		}
		
		processo.dividirPeca(pecaOriginal, novasPecas);
		organizarPecasRepository.save(processo);
	}
	
	/**
	 * Une peças de um processo.
	 *
	 * @param command
	 */
    @Command(description = "Unir peças.")
	public void handle(UnirPecasCommand command) {
		Processo processo = organizarPecasRepository.findOne(new ProcessoId(command.getProcessoId()));
		List<Peca> pecasUnidas = command.getPecas().stream()
				.map(pecaId -> organizarPecasRepository.findOnePeca(new PecaId(pecaId))).collect(Collectors.toList());
		
		//1º passo: recupera-se os ids dos documentos vinculados às peças para a criação de um novo documento com o conteúdo das peças unificado.
		List<DocumentoId> documentos = pecasUnidas.stream().map(p -> p.documento()).collect(Collectors.toList());
		DocumentoId documentoId = pecaAdapter.unir(documentos);
		
		//2º passo: criação da nova peça. Os dados da nova peça são baseados na peça de menor ordem dentro do processo.
		Integer numeroOrdem = primeiroNumeroOrdemPeca(pecasUnidas);
		Peca peca = pecasUnidas.stream().filter(p -> p.numeroOrdem().equals(numeroOrdem)).findFirst().get();
		Peca pecaUnificada = new Peca(organizarPecasRepository.nextPecaId(), documentoId, peca.tipo(), peca.descricao(),
				Visibilidade.PUBLICO, Situacao.JUNTADA);
		
		processo.unirPecas(pecasUnidas, pecaUnificada);
		organizarPecasRepository.save(processo);
	}
	
	/**
	 * Edita uma peça.
	 * 
	 * @param command
	 */
    @Command(description = "Editar peça.")
	public void handle(EditarPecaCommand command) {
		Processo processo = organizarPecasRepository.findOne(new ProcessoId(command.getProcessoId()));
		Peca pecaOriginal = organizarPecasRepository.findOnePeca(new PecaId(command.getPecaId()));
		TipoPeca tipoPeca = tipoPecaRepository.findOne(new TipoDocumentoId(command.getTipoPecaId()));
		Visibilidade visibilidade = Visibilidade.valueOf(command.getVisibilidade());
		
		processo.editarPeca(pecaOriginal, tipoPeca, command.getDescricao(), command.getNumeroOrdem(), visibilidade);
		organizarPecasRepository.save(processo);
	}
	
	/**
	 * Realiza a juntada de uma peça ao processo.
	 * 
	 * @param command
	 */
    @Command(description = "Juntar peça.")
	public void handle(JuntarPecaCommand command) {
		Processo processo = organizarPecasRepository.findOne(new ProcessoId(command.getProcessoId()));
		Peca peca = organizarPecasRepository.findOnePeca(new PecaId(command.getPecaId()));
		
		processo.juntarPeca(peca);
		organizarPecasRepository.save(processo);
	}
	
	/**
	 * Retorna o primeiro número de ordem da lista de peças.
	 * 
	 * @param pecas
	 * @return
	 */
	private Integer primeiroNumeroOrdemPeca(List<Peca> pecas) {
		ControladorOrdenacaoPecas controlador = new ControladorOrdenacaoPecas(
				pecas.stream().map(p -> p).collect(Collectors.toList()));
		
		return controlador.primeiroNumeroOrdemPeca();
	}
	
	/**
     * Cria um objeto peça.
     * 
     * @param pecaCommand Dados da peça oriundos do front-end.
     * @return Objeto peça.
     */
    private Peca criarPeca(CadastrarPecaCommand pecaCommand) {
	    List<DocumentoTemporarioId> documentosTemporarios = new ArrayList<>(0);
	    
	    documentosTemporarios.add(new DocumentoTemporarioId(pecaCommand.getDocumentoTemporarioId()));
	    
	    TipoPeca tipo = tipoPecaRepository.findOne(new TipoDocumentoId(pecaCommand.getTipoPecaId()));
	    DocumentoId documentoId = pecaAdapter.salvar(documentosTemporarios).get(0);
	    
		return new Peca(organizarPecasRepository.nextPecaId(), documentoId, tipo, tipo.nome(), Visibilidade.PUBLICO,
				Situacao.PENDENTE_JUNTADA);
    }

}
