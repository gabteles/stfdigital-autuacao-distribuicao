package br.jus.stf.autuacao.distribuicao.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.distribuicao.domain.ProcessoAdapter;
import br.jus.stf.autuacao.distribuicao.infra.client.ProcessoRestClient;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.ProcessoDto;

@Component
public class ProcessoAdapterImpl implements ProcessoAdapter {

	@Autowired
	private ProcessoRestClient processoRestClient;
	
	@Override
	public ProcessoDto consultar(Long processoId) {
		return processoRestClient.processo(processoId);
	}

}
