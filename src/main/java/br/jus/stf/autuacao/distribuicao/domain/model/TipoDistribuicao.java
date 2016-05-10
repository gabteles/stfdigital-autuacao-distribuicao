package br.jus.stf.autuacao.distribuicao.domain.model;

public enum TipoDistribuicao {
	
	COMUM("Comum", false),
	PREVENCAO("Prevenção", true);
	
	private String descricao;
	
	private boolean exigeJustificativa;
	
	private TipoDistribuicao(String descricao, boolean exigeJustificativa) {
		this.descricao = descricao;
		this.exigeJustificativa = exigeJustificativa;
	}
	
	public String descricao() {
		return descricao;
	}
	
	public boolean exigeJustificativa() {
		return exigeJustificativa;
	}

}
