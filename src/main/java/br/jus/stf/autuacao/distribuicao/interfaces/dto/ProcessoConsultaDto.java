package br.jus.stf.autuacao.distribuicao.interfaces.dto;

import java.util.Set;
/**
 * 
 * @author viniciusk
 *
 */
public class ProcessoConsultaDto {
	
	private String processoId;
	private String numero;
	private String classe;
	private Set<DistribuicaoProcessoDto> distribuicoes;
	
	/**
	 * 
	 * @param processoId
	 */
	public ProcessoConsultaDto(String processoId) {
		this.processoId = processoId;
	}

	public String getProcessoId() {
		return processoId;
	}

	public void setProcessoId(String processoId) {
		this.processoId = processoId;
	}
	
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public Set<DistribuicaoProcessoDto> getDistribuicoes() {
		return distribuicoes;
	}

	public void setDistribuicoes(Set<DistribuicaoProcessoDto> distribuicoes) {
		this.distribuicoes = distribuicoes;
	}

}
