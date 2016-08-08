package br.jus.stf.autuacao.distribuicao.interfaces.dto;

/**
 * @author viniciusk
 *
 */
public class ClasseDto {
	
	private String id;
	private String nome;
	
	/**
	 * @param id
	 * @param nome
	 */
	public ClasseDto(String id, String nome) {
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
