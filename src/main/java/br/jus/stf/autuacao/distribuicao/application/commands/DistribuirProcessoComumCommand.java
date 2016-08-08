package br.jus.stf.autuacao.distribuicao.application.commands;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;

import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.02.2016
 */
public class DistribuirProcessoComumCommand extends DistribuirProcessoCommand {
	
    @NotEmpty
	@ApiModelProperty(value = "Lista dos ministros candidatos à relatoria.")
	private Set<Long> ministrosCandidatos;
	
	@ApiModelProperty(value = "Lista dos ministros impedidos de relatar o processo.")
	private Set<Long> ministrosImpedidos;
    
    public Set<Long> getMinistrosCandidatos() {
		return ministrosCandidatos; 
	}
	
	public Set<Long> getMinistrosImpedidos() {
		return Optional.ofNullable(ministrosImpedidos).orElse(Collections.emptySet()); 
	}

}
