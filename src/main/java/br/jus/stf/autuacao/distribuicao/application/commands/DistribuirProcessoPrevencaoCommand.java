package br.jus.stf.autuacao.distribuicao.application.commands;

import java.util.Set;

import org.hibernate.validator.constraints.NotBlank;

import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.02.2016
 */
public class DistribuirProcessoPrevencaoCommand extends DistribuirProcessoCommand {
	
	@ApiModelProperty(value = "Lista dos processos que embasam a prevenção.")
	private Set<Long> processosPreventos;
	
	@NotBlank
	public String getJustificativa() {
		return super.getJustificativa();
	}
	
	public Set<Long> getProcessosPreventos() {
		return this.processosPreventos; 
	}

}
