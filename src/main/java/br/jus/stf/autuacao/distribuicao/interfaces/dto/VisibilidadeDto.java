package br.jus.stf.autuacao.distribuicao.interfaces.dto;

public class VisibilidadeDto {
	
	
	private String id;
	private String nome;
	
	public VisibilidadeDto(String id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public String getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

}
