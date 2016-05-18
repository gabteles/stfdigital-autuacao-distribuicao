package br.jus.stf.autuacao.distribuicao.interfaces.dto;

import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.distribuicao.domain.model.identidade.Ministro;

/**
 * Responsável por converter uma entidade Ministro num objeto MinistroDto
 * 
 * @author anderson.araujo
 * @since 18/05/2016
 *
 */
@Component
public class MinistroDtoAssembler {
	public MinistroDto toDto(Ministro ministro){
		return new MinistroDto(ministro.identity().toLong(), ministro.nome());
	}
}
