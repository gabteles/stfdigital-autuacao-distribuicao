package br.jus.stf.autuacao.distribuicao.interfaces.dto;


import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.distribuicao.domain.model.Peca;
import br.jus.stf.autuacao.distribuicao.domain.model.Processo;


/**
 * Cria objetos ProcessoDto
 * 
 * @author anderson.araujo
 * @author lucas.rodrigues
 * @since 06/06/2016
 *
 */
@Component
public class ProcessoDtoAssembler {
	
	@Autowired
	private PecaDtoAssembler pecaDtoAssembler;
	

	public ProcessoDistribuidoDto toDto(Processo processo) {
		Validate.notNull(processo);
		
		ProcessoDistribuidoDto dto = new ProcessoDistribuidoDto();
		
		Long processoId = processo.identity().toLong();
		
		dto.setProcessoId(processoId);
		dto.setPecas(toPecaDto(processo.pecas()));
		dto.setRelatorId(processo.relator().identity().toLong());
		return dto;
	}
	
	private List<PecaDto> toPecaDto(List<Peca> pecas) {
		return pecas.stream()
			.map(pecaDtoAssembler::toDto)
			.collect(Collectors.toList());
	}
}
