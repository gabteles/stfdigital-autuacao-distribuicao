package br.jus.stf.autuacao.distribuicao.application.commands;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 04.08.2016
 */
public class UnirPecasCommand {
	
	@NotNull
    @ApiModelProperty(value = "Identificador do processo.", required = true)
	private Long processoId;
	
	@NotEmpty
    @ApiModelProperty(value = "Identificadores das peças que serão unidas.", required = true)
	private List<Long> pecas;
	
	public UnirPecasCommand() {
		// Construtor default.
	}
	
	/**
	 * @param processoId
	 * @param pecasUnidas
	 */
	public UnirPecasCommand(Long processoId, List<Long> pecas) {
		this.processoId = processoId;
		this.pecas = pecas;
	}
	
	public Long getProcessoId() {
		return processoId;
	}
	
	public List<Long> getPecas() {
		return pecas;
	}

}