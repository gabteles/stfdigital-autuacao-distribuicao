package br.jus.stf.autuacao.distribuicao.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.distribuicao.domain.DocumentoAdapter;
import br.jus.stf.autuacao.distribuicao.infra.client.DocumentoRestClient;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.DocumentoDto;

/**
 * 
 * @author viniciusk
 *
 */
@Component
public class DocumentoAdapterImpl implements DocumentoAdapter {

	@Autowired
	private DocumentoRestClient documentoRestClient;
	
	@Override
	public DocumentoDto consultar(Long documentoId) {
		return documentoRestClient.consultar(documentoId);
	}

}
