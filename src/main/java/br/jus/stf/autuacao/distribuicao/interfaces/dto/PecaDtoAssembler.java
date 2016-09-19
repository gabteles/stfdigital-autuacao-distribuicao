package br.jus.stf.autuacao.distribuicao.interfaces.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.distribuicao.domain.model.Peca;

@Component
public class PecaDtoAssembler {

	public PecaDto toDto(Peca peca) {
		Validate.notNull(peca);
		return new PecaDto(peca.identity().toLong(), peca.documento().toLong(), peca.tipo().identity().toLong(), peca.descricao(), peca.numeroOrdem(), 
				peca.visibilidade().name(), peca.situacao().descricao());
		
	}
	
	public List<PecaDto> toDto(List<Peca> pecas) {
		return pecas.stream().map(peca -> toDto(peca)).collect(Collectors.toList());
	}
	
}
