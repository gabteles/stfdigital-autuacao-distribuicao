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
public class DividirPecaCommand {
	
	@NotNull
    @ApiModelProperty(value = "Identificador do processo.", required = true)
	private Long processoId;
	
	@NotNull
    @ApiModelProperty(value = "Identificador da peça original.", required = true)
	private Long pecaOriginalId;
	
	@NotEmpty
    @ApiModelProperty(value = "Lista com as peças quebradas.", required = true)
	private List<QuebrarPecaCommand> pecas;
	
	public DividirPecaCommand() {
		// Construtor default.
	}

	/**
	 * @param processoId
	 * @param pecas
	 */
	public DividirPecaCommand(Long processoId, List<QuebrarPecaCommand> pecas) {
		this.processoId = processoId;
		this.pecas = pecas;
	}
	
	public Long getProcessoId() {
		return processoId;
	}
	
	public Long getPecaOriginalId() {
		return pecaOriginalId;
	}
	
	public List<QuebrarPecaCommand> getPecas() {
		return pecas;
	}

}
