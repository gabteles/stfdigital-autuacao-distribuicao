package br.jus.stf.autuacao.distribuicao.infra.e2e;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.distribuicao.domain.ProcessoAdapter;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.ClasseDto;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.ParteDto;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.ProcessoDto;

@Component
@Primary
@Profile("e2e")
public class ProcessoAdapterE2EImpl implements ProcessoAdapter {

	
	@Override
	public ProcessoDto consultar(Long processoId) {
		ProcessoDto processoDto = new ProcessoDto();
		List<ParteDto> partesDto = new ArrayList<ParteDto>();
		partesDto.add(new ParteDto("FULANO DE TAL", "ATIVO"));
		partesDto.add(new ParteDto("SICRANO DA SILVA", "PASSIVO"));
		
		processoDto.setProcessoId(processoId);
		processoDto.setDataAutuacao(new Date());
		processoDto.setNumero(9000L);
		processoDto.setSigilo("PUBLICO");
		processoDto.setStatus("ATIVO");
		processoDto.setIdentificacao("RE/9000");
		processoDto.setClasse(new ClasseDto("RE", "Recurso Extraordinário"));
		processoDto.setMeioTramitacao("FISICO");
		processoDto.setPartes(partesDto);
		return processoDto;
		
	}

}