package br.jus.stf.autuacao.distribuicao.application.commands;

import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.02.2016
 */
public class IniciarDistribuicaoCommand {
	
	private ProcessoId processoId;

	public IniciarDistribuicaoCommand(ProcessoId processoId) {
		this.processoId = processoId;
	}
	
	public ProcessoId getProcessoId() {
		return processoId;
	}

}
