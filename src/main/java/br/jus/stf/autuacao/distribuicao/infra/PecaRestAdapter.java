package br.jus.stf.autuacao.distribuicao.infra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.jus.stf.autuacao.distribuicao.domain.PecaAdapter;
import br.jus.stf.autuacao.distribuicao.infra.client.DocumentoRestClient;
import br.jus.stf.core.shared.documento.DocumentoId;
import br.jus.stf.core.shared.documento.DocumentoTemporarioId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 04.08.2016
 */
@Component
public class PecaRestAdapter implements PecaAdapter {

	@Autowired
    private DocumentoRestClient documentoRestClient;
	
	@Override
	public List<DocumentoId> salvar(List<DocumentoTemporarioId> documentosTemporarios) {
		MultiValueMap<String, String> command = new LinkedMultiValueMap<>();
		
		documentosTemporarios.forEach(doc -> command.add("idsDocumentosTemporarios", doc.toString()));
		
		List<Map<String, Object>> documentos = documentoRestClient.salvar(command);
		
		return documentos.stream().map(doc -> new DocumentoId(new Long(doc.get("documentoId").toString()))).collect(Collectors.toList());
	}
	
	@Override
	public List<DocumentoId> dividir(DocumentoId documento, List<Range<Integer>> intervalosDivisao) {
		Map<String, Object> command = new HashMap<>();
		
		command.put("documentoId", documento.toLong());
		List<Map<String,Integer>> intervalos = new ArrayList<Map<String,Integer>>();
		intervalosDivisao.forEach(i -> {
			Map<String, Integer> intervalo = new HashMap<>(0);
			
			intervalo.put("paginaInicial", i.getMinimum());
			intervalo.put("paginaFinal", i.getMaximum());
			intervalos.add(intervalo);
		});
		command.put("intervalos", intervalos);
		
		List<Long> documentos = documentoRestClient.dividir(command);
		
		return documentos.stream().map(DocumentoId::new).collect(Collectors.toList());
	}

	@Override
	public DocumentoId unir(List<DocumentoId> documentos) {
		MultiValueMap<String, Long> command = new LinkedMultiValueMap<>();
		
		documentos.forEach(doc -> command.add("idsDocumentos", doc.toLong()));
		
		return new DocumentoId(documentoRestClient.unir(command));
	}

}