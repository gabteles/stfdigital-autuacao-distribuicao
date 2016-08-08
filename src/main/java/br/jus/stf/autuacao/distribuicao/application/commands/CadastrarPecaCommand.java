package br.jus.stf.autuacao.distribuicao.application.commands;

import javax.validation.constraints.NotNull;

import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 04.08.2016
 */
public class CadastrarPecaCommand {
	
	@NotNull
    @ApiModelProperty(value = "Identificador do documento temporário.", required=true)
	private String documentoTemporarioId;
	
	@NotNull
    @ApiModelProperty(value = "Identificador do tipo de peça.", required=true)
	private Long tipoPecaId;
	
	@NotNull
    @ApiModelProperty(value = "Número de ordem da peça.", required=true)
	private Integer numeroOrdem;
	
	public CadastrarPecaCommand() {
		// Construtor default.
	}
	
	/**
	 * @param documentoTemporarioId
	 * @param tipoPecaId
	 * @param numeroOrdem
	 */
	public CadastrarPecaCommand(String documentoTemporarioId, Long tipoPecaId, Integer numeroOrdem) {
		this.documentoTemporarioId = documentoTemporarioId;
		this.tipoPecaId = tipoPecaId;
		this.numeroOrdem = numeroOrdem;
	}
	
	public String getDocumentoTemporarioId() {
		return documentoTemporarioId;
	}
	
	public Long getTipoPecaId() {
		return tipoPecaId;
	}
	
	public Integer getNumeroOrdem() {
		return numeroOrdem;
	}

}
