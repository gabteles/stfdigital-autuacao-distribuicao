package br.jus.stf.autuacao.distribuicao.application.commands;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.02.2016
 */
public class IniciarDistribuicaoCommand {
	
	private Long processoId;

	public IniciarDistribuicaoCommand(Long processoId) {
		this.processoId = processoId;
	}
	
	public Long getProcessoId() {
		return processoId;
	}

}
