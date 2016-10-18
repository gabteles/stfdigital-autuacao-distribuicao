package br.jus.stf.autuacao.distribuicao.interfaces.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * @author viniciusk
 */
public class ProcessoDistribuidoDto {

	private Long processoId;
	private Long relatorId;
	private List<PecaDto> pecas;
	private Long numero;
	private String classe;



	ProcessoDistribuidoDto() {
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	private Date dataAutuacao;

	
	/**
	 * @param processoId
	 * @param remessaDto
	 */
	public ProcessoDistribuidoDto(Long processoId) {
		this.processoId = processoId;
	}

	public Long getProcessoId() {
		return processoId;
	}

	public void setProcessoId(Long processoId) {
		this.processoId = processoId;
	}

	public Long getRelatorId() {
		return relatorId;
	}

	public void setRelatorId(Long relatorId) {
		this.relatorId = relatorId;
	}

	public List<PecaDto> getPecas() {
		return pecas;
	}

	public void setPecas(List<PecaDto> pecas) {
		this.pecas = pecas;
	}

	public Date getDataAutuacao() {
		return dataAutuacao;
	}

	public void setDataAutuacao(Date dataAutuacao) {
		this.dataAutuacao = dataAutuacao;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	
}
