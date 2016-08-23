package br.jus.stf.autuacao.distribuicao.domain.model;

/**
 * @author rafael.alencar
 *
 * @since 03.08.2016
 */
public enum Situacao {
	
	EXCLUIDA("Excluída"),
	JUNTADA("Juntada"),
	PENDENTE_JUNTADA("Pendente de juntada");
	
	private String descricao;
	
	private Situacao(final String descricao) {
		this.descricao = descricao;
	}
	
	/**
	 * @return
	 */
	public String descricao() {
		return descricao;
	}
	
}