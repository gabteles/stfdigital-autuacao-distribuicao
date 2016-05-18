package br.jus.stf.autuacao.distribuicao.interfaces.dto;

/**
 * Objeto usado para transportar os dados de ministros.
 * 
 * @author anderson.araujo
 * @since 18/05/2016
 *
 */
public class MinistroDto {
	private Long id;
	private String nome;
	
	public MinistroDto(Long id, String nome){
		this.id = id;
		this.nome = nome;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
}
