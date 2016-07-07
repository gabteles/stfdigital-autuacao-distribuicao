package br.jus.stf.autuacao.distribuicao.interfaces;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.autuacao.distribuicao.domain.model.identidade.MinistroRepository;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.MinistroDto;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.MinistroDtoAssembler;

/**
 * Serviço REST destinado à consulta de dados de ministros.
 * 
 * @author anderson.araujo
 * @since 18/05/2016
 *
 */
@RestController
@RequestMapping("/api/ministros")
public class MinistroRestResource {
	
	@Autowired
	MinistroRepository ministroRepository;
	
	@Autowired
	MinistroDtoAssembler ministroDtoAssembler; 
	
	/**
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<MinistroDto> listar(){
		return ministroRepository.findAll().stream().map(ministroDtoAssembler::toDto).collect(Collectors.toList());
	}
}
