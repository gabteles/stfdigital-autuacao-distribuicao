package br.jus.stf.autuacao.distribuicao.interfaces;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.autuacao.distribuicao.application.OrganizarPecaApplicationService;
import br.jus.stf.autuacao.distribuicao.application.commands.DividirPecaCommand;
import br.jus.stf.autuacao.distribuicao.application.commands.EditarPecaCommand;
import br.jus.stf.autuacao.distribuicao.application.commands.ExcluirPecasCommand;
import br.jus.stf.autuacao.distribuicao.application.commands.InserirPecasCommand;
import br.jus.stf.autuacao.distribuicao.application.commands.JuntarPecaCommand;
import br.jus.stf.autuacao.distribuicao.application.commands.OrganizarPecasCommand;
import br.jus.stf.autuacao.distribuicao.application.commands.UnirPecasCommand;

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
    public void juntarPecas(@RequestBody @Valid JuntarPecaCommand command, BindingResult binding) {
        if (binding.hasErrors()) {
            throw new IllegalArgumentException("Junção de Peças Inválida: " + binding.getAllErrors());
        }
        
        organizarPecasApplicationService.handle(command);
    }

}
