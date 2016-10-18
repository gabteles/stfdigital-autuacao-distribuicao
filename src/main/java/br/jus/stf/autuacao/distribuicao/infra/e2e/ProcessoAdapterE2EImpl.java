package br.jus.stf.autuacao.distribuicao.infra.e2e;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.distribuicao.domain.ProcessoAdapter;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.ClasseDto;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.DistribuicaoProcessoDto;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.ParteDto;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.ProcessoConsultaDto;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.ProcessoDto;

@Component
@Primary
@Profile("e2e")
public class ProcessoAdapterE2EImpl implements ProcessoAdapter {

	
	@Override
	public ProcessoDto consultar(Long processoId) {
		ProcessoDto processoDto = new ProcessoDto();
		List<ParteDto> partesDto = new ArrayList<>();
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
	public List<ProcessoConsultaDto> consultarPrevencaoPorParte(String parte) {
		List<ProcessoConsultaDto> processosDistribuidos = new ArrayList<ProcessoConsultaDto>();
		ProcessoConsultaDto procDistribuido1 = new ProcessoConsultaDto("9001");
		procDistribuido1.setClasse("RE");
		procDistribuido1.setNumero("123");
		DistribuicaoProcessoDto distribuicao1 = new  DistribuicaoProcessoDto(41L,"RICARDO LEV", new Date());
		Set<DistribuicaoProcessoDto> distribuicoes1 = new HashSet<>();
		distribuicoes1.add(distribuicao1);
		procDistribuido1.setDistribuicoes(distribuicoes1);
		ProcessoConsultaDto procDistribuido2 = new ProcessoConsultaDto("9000");
		procDistribuido2.setClasse("AP");
		procDistribuido2.setNumero("470");
		DistribuicaoProcessoDto distribuicao2 = new  DistribuicaoProcessoDto(39L,"JOAQUIM BARBOSA", new Date());
		Set<DistribuicaoProcessoDto> distribuicoes2 = new HashSet<>();
		distribuicoes2.add(distribuicao2);
		procDistribuido2.setDistribuicoes(distribuicoes2);
		ProcessoConsultaDto procDistribuido3 = new ProcessoConsultaDto("9002");
		procDistribuido3.setClasse("ADI");
		procDistribuido3.setNumero("100");
		DistribuicaoProcessoDto distribuicao3 = new  DistribuicaoProcessoDto(41L,"RICARDO LEV", new Date());
		Set<DistribuicaoProcessoDto> distribuicoes3 = new HashSet<>();
		distribuicoes3.add(distribuicao3);
		procDistribuido3.setDistribuicoes(distribuicoes3);
		processosDistribuidos.add(procDistribuido1);
		processosDistribuidos.add(procDistribuido2);
		processosDistribuidos.add(procDistribuido3);
		return processosDistribuidos;
	}

}