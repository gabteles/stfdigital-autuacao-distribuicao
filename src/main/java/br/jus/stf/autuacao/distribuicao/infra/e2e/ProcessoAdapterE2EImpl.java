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
import br.jus.stf.autuacao.distribuicao.interfaces.dto.ProcessoDistribuidoDto;
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

	@Override
	public List<ProcessoDistribuidoDto> consultarPrevencaoPorParte(String parte) {
		List<ProcessoDistribuidoDto> processosDistribuidos = new ArrayList<ProcessoDistribuidoDto>();
		ProcessoDistribuidoDto procDistribuido1 = new ProcessoDistribuidoDto(9001L);
		procDistribuido1.setRelatorId(41L);
		procDistribuido1.setClasse("RE");
		procDistribuido1.setNumero(123L);
		ProcessoDistribuidoDto procDistribuido2 = new ProcessoDistribuidoDto(9000L);
		procDistribuido2.setRelatorId(43L);
		procDistribuido2.setClasse("AP");
		procDistribuido2.setNumero(470L);
		processosDistribuidos.add(procDistribuido1);
		processosDistribuidos.add(procDistribuido2);
		return processosDistribuidos;
	}

}