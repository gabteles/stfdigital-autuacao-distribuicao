package br.jus.stf.autuacao.distribuicao.application.commands;

import javax.validation.constraints.NotNull;

import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.02.2016
 */
public class IniciarDistribuicaoCommand {
	
	@NotNull
    @ApiModelProperty(value = "Identificador do processo", required=true)
	private Long processoId;

	/**
	 * @param processoId
	 */
	public IniciarDistribuicaoCommand(Long processoId) {
		this.processoId = processoId;
	}
	
	public Long getProcessoId() {
		return processoId;
	}

}
