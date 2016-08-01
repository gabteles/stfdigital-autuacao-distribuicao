package br.jus.stf.autuacao.distribuicao.interfaces.dto;

public class TipoDistribuicaoDto {
	
	private String id;
	private boolean exigeJustificativa;
	
	public TipoDistribuicaoDto(String id, boolean exigeJustificativa) {
		this.id = id;
		this.exigeJustificativa = exigeJustificativa;
	}

	public String getId() {
		return id;
	}

	public boolean isExigeJustificativa() {
		return exigeJustificativa;
	}

	

}
