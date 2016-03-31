package br.jus.stf.autuacao.distribuicao.interfaces;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.autuacao.distribuicao.application.DistribuicaoApplicationService;
import br.jus.stf.autuacao.distribuicao.application.commands.DistribuirProcessoCommand;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 02.02.2016
 */
@RestController
@RequestMapping("/api/distribuicao")
public class DistribuicaoRestResource {
    
    @Autowired
    private DistribuicaoApplicationService distribuicaoApplicationService;

    @RequestMapping(method = RequestMethod.POST)
    public void distribuir(@RequestBody @Valid DistribuirProcessoCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Distribuição Inválida: " + binding.getAllErrors());
        }
        
        distribuicaoApplicationService.handle(command);
    }

}
