package br.jus.stf.autuacao.distribuicao.interfaces.dto;

/**
 * 
 * @author viniciusk
 *
 */
public class PecaDto {
	
	private Long pecaId;
	private Long documentoId;
	private String tipoPeca;
	private String descricao;
	private Integer numeroOrdem;
	private String visibilidade;
	private String situacao;
	
	public PecaDto(Long pecaId, Long documentoId, String tipoPeca, String descricao, Integer numeroOrdem, String visibilidade, String situacao) {
		this.pecaId = pecaId;
		this.documentoId = documentoId;
		this.tipoPeca = tipoPeca;
		this.descricao = descricao;
		this.numeroOrdem = numeroOrdem;
		this.visibilidade = visibilidade;
		this.situacao = situacao;
	}

	public Long getPecaId() {
		return pecaId;
	}

	public Long getDocumentoId() {
		return documentoId;
	}

	public String getTipoPeca() {
		return tipoPeca;
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

	public String getSituacao() {
		return situacao;
	}
	
	

}
