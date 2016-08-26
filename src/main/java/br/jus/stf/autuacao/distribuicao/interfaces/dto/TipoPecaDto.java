package br.jus.stf.autuacao.distribuicao.interfaces.dto;

public class TipoPecaDto {
	
	private Long tipoId;
	private String nome;
	
	public TipoPecaDto(Long id, String nome) {
		this.tipoId = id;
		this.nome = nome;
	}
	
	public TipoPecaDto() {
		// TODO Auto-generated constructor stub
	}

	public Long getTipoId() {
		return tipoId;
	}

	public String getNome() {
		return nome;
	}

}
