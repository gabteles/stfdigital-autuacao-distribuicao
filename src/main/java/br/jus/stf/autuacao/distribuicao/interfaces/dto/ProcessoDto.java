package br.jus.stf.autuacao.distribuicao.interfaces.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * @author viniciusk
 * @author Lucas Rodrigues
 *
 */
public class ProcessoDto {

	private Long processoId;
	private String status;
	private ClasseDto classe;
	private Long numero;
	private String identificacao;
	private String autuador;
	private String meioTramitacao;
	private String sigilo;
	private List<ParteDto> partes;


	public ProcessoDto() {}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	private Date dataAutuacao;

	
	/**
	 * @param processoId
	 * @param remessaDto
	 */
	public ProcessoDto(Long processoId) {
		this.processoId = processoId;
	}

	public Long getProcessoId() {
		return processoId;
	}

	public void setProcessoId(Long processoId) {
		this.processoId = processoId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ClasseDto getClasse() {
		return classe;
	}

	public void setClasse(ClasseDto classe) {
		this.classe = classe;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public String getAutuador() {
		return autuador;
	}

	public void setAutuador(String autuador) {
		this.autuador = autuador;
	}

	public Date getDataAutuacao() {
		return dataAutuacao;
	}

	public void setDataAutuacao(Date dataAutuacao) {
		this.dataAutuacao = dataAutuacao;
	}

	public String getMeioTramitacao() {
		return meioTramitacao;
	}

	public void setMeioTramitacao(String meioTramitacao) {
		this.meioTramitacao = meioTramitacao;
	}

	public String getSigilo() {
		return sigilo;
	}

	public void setSigilo(String sigilo) {
		this.sigilo = sigilo;
	}

	public List<ParteDto> getPartes() {
		return partes;
	}

	public void setPartes(List<ParteDto> partes) {
		this.partes = partes;
	}
}
