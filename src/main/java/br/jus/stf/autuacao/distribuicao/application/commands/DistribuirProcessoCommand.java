package br.jus.stf.autuacao.distribuicao.application.commands;

import javax.validation.constraints.NotNull;

import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.02.2016
 */
public class DistribuirProcessoCommand {
    
    @NotNull
    private DistribuicaoId distribuicaoId;
    
    public DistribuicaoId getDistribuicaoId() {
        return distribuicaoId;
    }

}
