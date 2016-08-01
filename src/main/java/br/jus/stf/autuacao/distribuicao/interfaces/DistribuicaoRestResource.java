package br.jus.stf.autuacao.distribuicao.interfaces;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.autuacao.distribuicao.application.DistribuicaoApplicationService;
import br.jus.stf.autuacao.distribuicao.application.commands.DistribuirProcessoCommand;
import br.jus.stf.autuacao.distribuicao.domain.model.TipoDistribuicao;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.TipoDistribuicaoDto;

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
    

    /**
     * @param command
     * @param binding
     */
    @RequestMapping(method = RequestMethod.POST)
    public void distribuir(@RequestBody @Valid DistribuirProcessoCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Distribuição Inválida: " + binding.getAllErrors());
        }
        
        distribuicaoApplicationService.handle(command);
    }
    
	/**
	 * @return
	 */
	@RequestMapping(value="/tipo-distribuicao", method = RequestMethod.GET)
    public List<TipoDistribuicaoDto> listarTiposDistribuicao(){
		return Arrays.asList(TipoDistribuicao.values()).stream().map(tipo -> new TipoDistribuicaoDto(tipo.name(), tipo.exigeJustificativa()))
				.collect(Collectors.toList());
    }
	

}
