package br.jus.stf.autuacao.distribuicao.interfaces.dto;

import java.util.Date;

public class DistribuicaoProcessoDto {
	
	private Long relatorId;
	private String relator;
	private Date data;
	
	public DistribuicaoProcessoDto() {
		
	}
	
	public DistribuicaoProcessoDto(Long relatorId, String relator, Date data) {
		this.relatorId = relatorId;
		this.relator = relator;
		this.data = data;
	}

	public Long getRelatorId() {
		return relatorId;
	}

	public void setRelatorId(Long relatorId) {
		this.relatorId = relatorId;
	}

	public String getRelator() {
		return relator;
	}

	public void setRelator(String relator) {
		this.relator = relator;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	

}
