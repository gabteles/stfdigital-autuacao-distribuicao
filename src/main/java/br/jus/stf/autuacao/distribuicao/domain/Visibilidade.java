package br.jus.stf.autuacao.distribuicao.domain;

/**
 * @author rafael.alencar
 *
 * @since 03.08.2016
 */
public enum Visibilidade {
	
	PUBLICO("Público"),
	PENDENTE_VISUALIZACAO("Pendente de visualização");
	
	private String descricao;
	
	private Visibilidade(final String descricao) {
		this.descricao = descricao;
	}
	
	/**
	 * @return
	 */
	public String descricao() {
		return descricao;
	}
	
}