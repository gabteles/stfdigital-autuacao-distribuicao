package br.jus.stf.autuacao.distribuicao.application.commands;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 04.08.2016
 */
public class EditarPecaCommand {
	
	@NotNull
    @ApiModelProperty(value = "Identificador do processo.", required = true)
	private Long processoId;
	
	@NotNull
    @ApiModelProperty(value = "Identificador da peça editada.", required = true)
	private Long pecaId;
	
	@NotNull
    @ApiModelProperty(value = "Identificador do tipo de peça.", required = true)
	private Long tipoPecaId;
	
	@NotBlank
    @ApiModelProperty(value = "Descrição da peça.", required = true)
	private String descricao;
	
	@NotNull
    @ApiModelProperty(value = "Número de ordem da peça.", required = true)
	private Integer numeroOrdem;
	
	@NotBlank
    @ApiModelProperty(value = "Visibilidade da peça.", required = true)
	private String visibilidade;
	
	public EditarPecaCommand() {
		// Construtor default.
	}
	
	/**
	 * @param processoId
	 * @param pecaId
	 * @param tipoPecaId
	 * @param descricao
	 * @param numeroOrdem
	 * @param visibilidade
	 */
	public EditarPecaCommand(Long processoId, Long pecaId, Long tipoPecaId, String descricao, Integer numeroOrdem,
			String visibilidade) {
		this.processoId = processoId;
		this.pecaId = pecaId;
		this.tipoPecaId = tipoPecaId;
		this.descricao = descricao;
		this.numeroOrdem = numeroOrdem;
		this.visibilidade = visibilidade;
	}
	
	public Long getProcessoId() {
		return processoId;
	}
	
	public Long getPecaId() {
		return pecaId;
	}
	
	public Long getTipoPecaId() {
		return tipoPecaId;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public Integer getNumeroOrdem() {
		return numeroOrdem;
	}
	
	public String getVisibilidade() {
		return visibilidade;
	}

}
