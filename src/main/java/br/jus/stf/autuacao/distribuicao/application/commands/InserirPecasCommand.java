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
public class InserirPecasCommand {
	
	@NotNull
    @ApiModelProperty(value = "Identificador do processo.", required=true)
	private Long processoId;
	
	@NotEmpty
    @ApiModelProperty(value = "Lista de peças.", required=true)
	private List<CadastrarPecaCommand> pecas;
	
	public InserirPecasCommand() {
		// Construtor default.
	}

	/**
	 * @param processoId
	 * @param pecas
	 */
	public InserirPecasCommand(Long processoId, List<CadastrarPecaCommand> pecas) {
		this.processoId = processoId;
		this.pecas = pecas;
	}
	
	public Long getProcessoId() {
		return processoId;
	}
	
	public List<CadastrarPecaCommand> getPecas() {
		return pecas;
	}

}
