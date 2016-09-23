package br.jus.stf.autuacao.distribuicao.infra;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.distribuicao.domain.ProcessoAdapter;
import br.jus.stf.autuacao.distribuicao.infra.client.AutuacaoRestClient;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.ProcessoDistribuidoDto;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.ProcessoDto;

@Component
public class ProcessoAdapterImpl implements ProcessoAdapter {

	@Autowired
	private AutuacaoRestClient autuacaoRestClient;
	
	@Override
	public ProcessoDto consultar(Long processoId) {
		return autuacaoRestClient.processo(processoId);
	}

	@Override
	public List<ProcessoDistribuidoDto> consultarPrevencaoPorParte(String parte) {
		// TODO: Viniciusk implementar consulta ao contexto de pesquisa para consultar prevenção
		return Collections.emptyList();
	}

}
