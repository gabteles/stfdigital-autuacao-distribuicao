package br.jus.stf.autuacao.distribuicao.interfaces.dto;

/**
 * @author viniciusk
 * @author Lucas Rodrigues
 *
 */
public class ParteDto {
	
	private String apresentacao;
	
	private String polo;
	
	/**
	 * @param apresentacao
	 * @param polo
	 */
	public ParteDto(String apresentacao, String polo) {
		this.apresentacao = apresentacao;
		this.polo = polo;
	}
	
	public ParteDto() {
		// Construtor default
	}

	public String getApresentacao() {
		return apresentacao;
	}

	public String getPolo() {
		return polo;
	}
	
	

}
