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
public class OrganizarPecasCommand {
	
	@NotNull
    @ApiModelProperty(value = "Identificador da distribuição.", required=true)
	private Long distribuicaoId;
	
	@NotEmpty
    @ApiModelProperty(value = "Identificadores das peças organizadas.", required=true)
	private List<Long> pecas;
	
	@NotNull
    @ApiModelProperty(value = "Indica se a tarefa será finalizada.", required=true)
	private Boolean finalizarTarefa;
	
	public OrganizarPecasCommand() {
		// Construtor default.
	}
	
	/**
	 * @param distribuicaoId
	 * @param pecas
	 * @param finalizarTarefa
	 */
	public OrganizarPecasCommand(Long distribuicaoId, List<Long> pecas, Boolean finalizarTarefa) {
		this.distribuicaoId = distribuicaoId;
		this.pecas = pecas;
		this.finalizarTarefa = finalizarTarefa;
	}
	
	public Long getDistribuicaoId() {
		return distribuicaoId;
	}
	
	public List<Long> getPecas() {
		return pecas;
	}
	
	public Boolean isFinalizarTarefa() {
		return finalizarTarefa;
	}

}
