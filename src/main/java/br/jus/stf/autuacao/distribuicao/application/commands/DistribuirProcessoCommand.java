package br.jus.stf.autuacao.distribuicao.application.commands;

import javax.validation.constraints.NotNull;

import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.02.2016
 */
public abstract class DistribuirProcessoCommand {
    
    @NotNull
    @ApiModelProperty(value = "Identificador da distribuição na fila.", required=true)
    private Long distribuicaoId;
    
    @ApiModelProperty(value = "Justificativa da distribuição.")
    private String justificativa;
    
    public Long getDistribuicaoId() {
        return distribuicaoId;
    }
    
    public String getJustificativa() {
    	return justificativa;
    }

}
