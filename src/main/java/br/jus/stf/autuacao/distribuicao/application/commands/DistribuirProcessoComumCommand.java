package br.jus.stf.autuacao.distribuicao.application.commands;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.02.2016
 */
public class DistribuirProcessoComumCommand extends DistribuirProcessoCommand {
	
    @ApiModelProperty(value = "Lista dos ministros candidatos à relatoria.")
	private Set<Long> ministrosCandidatos;
	
	@ApiModelProperty(value = "Lista dos ministros impedidos de relatar o processo.")
	private Set<Long> ministrosImpedidos;
    
    public Set<Long> getMinistrosCandidatos() {
		return Optional.ofNullable(this.ministrosCandidatos).orElse(Collections.emptySet()); 
	}
	
	public Set<Long> getMinistrosImpedidos() {
		return Optional.ofNullable(this.ministrosImpedidos).orElse(Collections.emptySet()); 
	}

}
