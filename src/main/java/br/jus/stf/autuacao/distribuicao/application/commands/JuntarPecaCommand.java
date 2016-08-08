package br.jus.stf.autuacao.distribuicao.application.commands;

import javax.validation.constraints.NotNull;

import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 04.08.2016
 */
public class JuntarPecaCommand {
	
	@NotNull
    @ApiModelProperty(value = "Identificador do processo.", required = true)
	private Long processoId;
	
	@NotNull
    @ApiModelProperty(value = "Identificador da peça editada.", required = true)
	private Long pecaId;
	
	public JuntarPecaCommand() {
		// Construtor default.
	}
	
	/**
	 * @param processoId
	 * @param pecaId
	 */
	public JuntarPecaCommand(Long processoId, Long pecaId) {
		this.processoId = processoId;
		this.pecaId = pecaId;
	}
	
	public Long getProcessoId() {
		return processoId;
	}
	
	public Long getPecaId() {
		return pecaId;
	}

}