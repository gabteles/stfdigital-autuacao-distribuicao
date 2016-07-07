package br.jus.stf.autuacao.distribuicao.domain.model;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 19.05.2016
 */
public enum TipoDistribuicao {
	
	COMUM("Comum", false),
	PREVENCAO("Prevenção", true);
	
	private String descricao;
	
	private boolean exigeJustificativa;
	
	private TipoDistribuicao(String descricao, boolean exigeJustificativa) {
		this.descricao = descricao;
		this.exigeJustificativa = exigeJustificativa;
	}
	
	/**
	 * @return
	 */
	public String descricao() {
		return descricao;
	}
	
	/**
	 * @return
	 */
	public boolean exigeJustificativa() {
		return exigeJustificativa;
	}

}
