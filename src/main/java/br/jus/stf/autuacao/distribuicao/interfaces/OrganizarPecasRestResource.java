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

import br.jus.stf.autuacao.distribuicao.application.OrganizarPecaApplicationService;
import br.jus.stf.autuacao.distribuicao.application.commands.DividirPecaCommand;
import br.jus.stf.autuacao.distribuicao.application.commands.EditarPecaCommand;
import br.jus.stf.autuacao.distribuicao.application.commands.ExcluirPecasCommand;
import br.jus.stf.autuacao.distribuicao.application.commands.InserirPecasCommand;
import br.jus.stf.autuacao.distribuicao.application.commands.JuntarPecasCommand;
import br.jus.stf.autuacao.distribuicao.application.commands.OrganizarPecasCommand;
import br.jus.stf.autuacao.distribuicao.application.commands.UnirPecasCommand;
import br.jus.stf.autuacao.distribuicao.domain.DocumentoAdapter;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoId;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoRepository;
import br.jus.stf.autuacao.distribuicao.domain.model.FilaDistribuicao;
import br.jus.stf.autuacao.distribuicao.domain.model.OrganizarPecaRepository;
import br.jus.stf.autuacao.distribuicao.domain.model.Visibilidade;
import br.jus.stf.autuacao.distribuicao.domain.model.documento.TipoPecaRepository;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.DocumentoDto;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.ProcessoDistribuidoDto;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.ProcessoDtoAssembler;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.TipoPecaDto;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.TipoPecaDtoAssembler;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.VisibilidadeDto;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 05.08.2016
 */
@RestController
@RequestMapping("/api/organizacao-pecas")
public class OrganizarPecasRestResource {
    
    @Autowired
    private OrganizarPecaApplicationService organizarPecasApplicationService;
    
    @Autowired
    private OrganizarPecaRepository organizarPecaRepository;
    
    @Autowired
    private DistribuicaoRepository distribuicaoRepository;
    
    @Autowired
    private ProcessoDtoAssembler processoDtoAssembler;
    
    @Autowired
    private TipoPecaRepository tipoPecaRepository;
    
    @Autowired
    private TipoPecaDtoAssembler tipoPecaDtoAssembler;
    
    @Autowired
    private DocumentoAdapter documentoAdapter;
    

    /**
     * @param command
     * @param binding
     */
    @RequestMapping(method = RequestMethod.POST, value = "/inserir")
    public void inserirPecas(@RequestBody @Valid InserirPecasCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Inserção de Peças Inválida: " + binding.getAllErrors());
        }
        
        organizarPecasApplicationService.handle(command);
    }
    
    /**
     * @param command
     * @param binding
     */
    @RequestMapping(method = RequestMethod.POST, value = "/excluir")
    public void excluirPecas(@RequestBody @Valid ExcluirPecasCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Exclusão de Peças Inválida: " + binding.getAllErrors());
        }
        
        organizarPecasApplicationService.handle(command);
    }
    
    /**
     * @param command
     * @param binding
     */
    @RequestMapping(method = RequestMethod.POST, value = "/organizar")
    public void organizarPecas(@RequestBody @Valid OrganizarPecasCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Organização de Peças Inválida: " + binding.getAllErrors());
        }
        
        organizarPecasApplicationService.handle(command);
    }
    
    /**
     * @param command
     * @param binding
     */
    @RequestMapping(method = RequestMethod.POST, value = "/dividir")
    public void dividirPecas(@RequestBody @Valid DividirPecaCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Divisão de Peças Inválida: " + binding.getAllErrors());
        }
        
        organizarPecasApplicationService.handle(command);
    }
    
    /**
     * @param command
     * @param binding
     */
    @RequestMapping(method = RequestMethod.POST, value = "/unir")
    public void unirPecas(@RequestBody @Valid UnirPecasCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("União de Peças Inválida: " + binding.getAllErrors());
        }
        
        organizarPecasApplicationService.handle(command);
    }
    
    /**
     * @param command
     * @param binding
     */
    @RequestMapping(method = RequestMethod.POST, value = "/editar")
    public void editarPecas(@RequestBody @Valid EditarPecaCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Edição de Peças Inválida: " + binding.getAllErrors());
        }
        
        organizarPecasApplicationService.handle(command);
    }
    
    /**
     * @param command
     * @param binding
     */
    @RequestMapping(method = RequestMethod.POST, value = "/juntar")
    public void juntarPecas(@RequestBody @Valid JuntarPecasCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Junção de Peças Inválida: " + binding.getAllErrors());
        }
        
        organizarPecasApplicationService.handle(command);
    }
    
	
    /**
     * Retorna o processo distribuído com o seu relator e suas peças correspondentes
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/processo", method = RequestMethod.GET)
    public ProcessoDistribuidoDto consultarProcesso(@PathVariable("id") Long id) {
    	
    	FilaDistribuicao fila =  distribuicaoRepository.findOneFilaDistribuicao(new DistribuicaoId(id));
    	
    	return Optional.ofNullable(organizarPecaRepository.findOne(fila.processo()))
    			.map(processoDtoAssembler::toDto).orElseThrow(IllegalArgumentException::new);
    	
    }
    
    @RequestMapping(value="/tipopeca", method = RequestMethod.GET)
    public List<TipoPecaDto> listarTipoPeca(){
    	return tipoPecaRepository.findAll().stream().
    			map(tipoPecaDtoAssembler::toDto).collect(Collectors.toList());
    }
    
	/**
	 * @return
	 */
	@RequestMapping(value="/visibilidade", method = RequestMethod.GET)
    public List<VisibilidadeDto> listarVisibilidadePeca(){
		return Arrays.asList(Visibilidade.values()).stream().map(tipo -> new VisibilidadeDto(tipo.name(), tipo.descricao()))
				.collect(Collectors.toList());
    }
	
	  @RequestMapping(value = "/pecas/documentos/{documentoId}", method = RequestMethod.GET)
	    public DocumentoDto consultarDocumento(@PathVariable("documentoId") Long documentoId) {
	    	return documentoAdapter.consultar(documentoId);
	    }

}
