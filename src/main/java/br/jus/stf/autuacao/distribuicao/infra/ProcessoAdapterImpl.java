package br.jus.stf.autuacao.distribuicao.infra;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.distribuicao.domain.ProcessoAdapter;
import br.jus.stf.autuacao.distribuicao.infra.client.AutuacaoRestClient;
import br.jus.stf.autuacao.distribuicao.infra.client.ProcessoRestClient;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.ProcessoConsultaDto;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.ProcessoDto;

/**
 * 
 * @author viniciusk
 *
 */
@Component
public class ProcessoAdapterImpl implements ProcessoAdapter {

	@Autowired
	private AutuacaoRestClient autuacaoRestClient;
	
	@Autowired
	private ProcessoRestClient processoRestClient;
	
	@Override
	public ProcessoDto consultar(Long processoId) {
		return autuacaoRestClient.processo(processoId);
	}

	@Override
	public List<ProcessoConsultaDto> consultarPrevencaoPorParte(String parte) {
		
		Map<String, String> partes = new HashMap<>();
		partes.put("parte", parte);
		
		return processoRestClient.pesquisar(partes);
	}

}
