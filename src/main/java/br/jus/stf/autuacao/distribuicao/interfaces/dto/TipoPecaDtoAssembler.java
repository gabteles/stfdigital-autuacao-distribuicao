package br.jus.stf.autuacao.distribuicao.interfaces.dto;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.distribuicao.domain.model.documento.TipoPeca;

@Component
public class TipoPecaDtoAssembler {
	
	public TipoPecaDto toDto(TipoPeca tipo){
		Validate.notNull(tipo);
		return new TipoPecaDto(tipo.identity().toLong(), tipo.nome());
	}

}
