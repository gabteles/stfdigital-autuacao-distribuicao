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
public class QuebrarPecaCommand {
	
	@NotNull
    @ApiModelProperty(value = "Identificador do documento temporário.", required = true)
	private String documentoTemporarioId;
	
	@NotNull
    @ApiModelProperty(value = "Identificador do tipo de peça.", required = true)
	private Long tipoPecaId;
	
	@NotBlank
	@ApiModelProperty(value = "Visibilidade da peça", required = true)
	private String visibilidade;
	
	@NotBlank
	@ApiModelProperty(value = "Situação da peça", required = true)
	private String situacao;
	
	@NotBlank
	@ApiModelProperty(value = "Descrição da peça", required = true)
	private String descricao;
	
	@NotNull
	@ApiModelProperty(value = "Página inicial da peça", required = true)
	private Integer paginaInicial;
	
	@NotNull
	@ApiModelProperty(value = "Página inicial da peça", required = true)
	private Integer paginaFinal;
	
	public QuebrarPecaCommand() {
		// Construtor default.
	}
	
	/**
	 * @param documentoTemporarioId
	 * @param tipoPecaId
	 * @param visibilidade
	 * @param situacao
	 * @param descricao
	 * @param paginaInicial
	 * @param paginaFinal
	 */
	public QuebrarPecaCommand(String documentoTemporarioId, Long tipoPecaId, String visibilidade, String situacao,
			String descricao, Integer paginaInicial, Integer paginaFinal) {
		this.documentoTemporarioId = documentoTemporarioId;
		this.tipoPecaId = tipoPecaId;
		this.visibilidade = visibilidade;
		this.situacao = situacao;
		this.descricao = descricao;
		this.paginaInicial = paginaInicial;
		this.paginaFinal = paginaFinal;
	}
	
	public String getDocumentoTemporarioId() {
		return documentoTemporarioId;
	}
	
	public Long getTipoPecaId() {
		return tipoPecaId;
	}
	
	public String getVisibilidade() {
		return visibilidade;
	}
	
	public String getSituacao() {
		return situacao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public Integer getPaginaInicial() {
		return paginaInicial;
	}
	
	public Integer getPaginaFinal() {
		return paginaFinal;
	}

}