package br.jus.stf.autuacao.distribuicao.interfaces;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.autuacao.distribuicao.application.DistribuicaoApplicationService;
import br.jus.stf.autuacao.distribuicao.application.commands.DistribuirProcessoComumCommand;
import br.jus.stf.autuacao.distribuicao.application.commands.DistribuirProcessoPrevencaoCommand;
import br.jus.stf.autuacao.distribuicao.domain.ProcessoAdapter;
import br.jus.stf.autuacao.distribuicao.domain.model.Distribuicao;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoId;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoRepository;
import br.jus.stf.autuacao.distribuicao.domain.model.TipoDistribuicao;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.ProcessoDto;
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
    
    @Autowired
    private DistribuicaoRepository distribuicaoRepository;
    
    @Autowired
    private ProcessoAdapter processoAdapter;

    /**
     * @param command
     * @param binding
     */
    @RequestMapping(value = "/comum", method = RequestMethod.POST)
    public void distribuir(@RequestBody @Valid DistribuirProcessoComumCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Distribuição Inválida: " + binding.getAllErrors());
        }
        
        distribuicaoApplicationService.handle(command);
    }
    
    /**
     * @param command
     * @param binding
     */
    @RequestMapping(value = "/prevencao", method = RequestMethod.POST)
    public void distribuir(@RequestBody @Valid DistribuirProcessoPrevencaoCommand command, BindingResult binding) {
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
	
    /**
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/processo", method = RequestMethod.GET)
    public ProcessoDto consultarDistribuicao(@PathVariable("id") Long id) {
    	Optional<Distribuicao> distribuicao =  Optional.ofNullable(distribuicaoRepository.findOne(new DistribuicaoId(id)));
    	
    	return Optional.ofNullable(processoAdapter.consultar(distribuicao.get().processo().toLong()))
    			.orElseThrow(() -> new IllegalArgumentException("Processo inválido."));
    	
    }
	

}
