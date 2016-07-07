package br.jus.stf.autuacao.distribuicao.application.commands;

import java.util.Set;

import javax.validation.constraints.NotNull;

import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.02.2016
 */
public class DistribuirProcessoCommand {
    
    @NotNull
    @ApiModelProperty(value = "Identificador da distribuição na fila.", required=true)
    private Long distribuicaoId;
    
    @NotNull
    @ApiModelProperty(value = "Tipo da distribuição.", required=true)
    private String tipoDistribuicao;
    
    @ApiModelProperty(value = "Justificativa da distribuição.")
    private String justificativa;
    
    @ApiModelProperty(value = "Lista dos ministros candidatos à relatoria.")
	private Set<Long> ministrosCandidatos;
	
	@ApiModelProperty(value = "Lista dos ministros impedidos de relatar o processo.")
	private Set<Long> ministrosImpedidos;
	
	@ApiModelProperty(value = "Lista dos processos que embasam a prevenção.")
	private Set<Long> processosPreventos;
	
	public DistribuirProcessoCommand() {
		// Construtor default
	}
    
    public Long getDistribuicaoId() {
        return distribuicaoId;
    }
    
    public String getTipoDistribuicao() {
    	return tipoDistribuicao;
    }
    
    public String getJustificativa() {
    	return justificativa;
    }
    
    public Set<Long> getMinistrosCandidatos() {
		return this.ministrosCandidatos; 
	}
	
	public Set<Long> getMinistrosImpedidos() {
		return this.ministrosImpedidos; 
	}
	
	public Set<Long> getProcessosPreventos() {
		return this.processosPreventos; 
	}

}
