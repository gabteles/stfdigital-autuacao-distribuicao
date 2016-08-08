package br.jus.stf.autuacao.distribuicao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Range;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.jus.stf.autuacao.distribuicao.infra.PecaRestAdapter;
import br.jus.stf.autuacao.distribuicao.infra.client.DocumentoRestClient;
import br.jus.stf.core.shared.documento.DocumentoId;
import br.jus.stf.core.shared.documento.DocumentoTemporarioId;

/**
 * Valida a API de documentos.
 * 
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 08.08.2016
 */
public class PecaRestAdapterUnitTests {
	
	@Mock
	private DocumentoRestClient documentoRestClient;
	
	@InjectMocks
	private PecaRestAdapter pecaRestAdapter;
	
	private String idDocTemp = "_DocTemp_";
	
	@Before
	public void configuracao() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void salvar() {
        MultiValueMap<String, String> command = new LinkedMultiValueMap<>();
		
		command.add("idsDocumentosTemporarios", idDocTemp);
		
		Map<String,Object> resposta = new HashMap<>(0);
		
		resposta.put("documentoId", 1L);
		
		Mockito.when(documentoRestClient.salvar(command)).thenReturn(Arrays.asList(resposta));
		
		List<DocumentoId> documentosId = pecaRestAdapter.salvar(Arrays.asList(new DocumentoTemporarioId(idDocTemp)));
		
		Assert.assertNotNull("Lista de documentos não pode ser nula.", documentosId);
		Assert.assertEquals("Listas de documentos devem ser iguais.", Arrays.asList(new DocumentoId(1L)), documentosId);
		
		Mockito.verify(documentoRestClient, Mockito.times(1)).salvar(command);
	}
	
	@Test
	public void unir() {
		MultiValueMap<String, Long> command = new LinkedMultiValueMap<>();
		
		command.add("idsDocumentos", 1L);
		command.add("idsDocumentos", 2L);
		
		Mockito.when(documentoRestClient.unir(command)).thenReturn(3L);
		
		DocumentoId documentoId = pecaRestAdapter.unir(Arrays.asList(new DocumentoId(1L), new DocumentoId(2L)));
		
		Assert.assertNotNull("Documento não pode ser nulo.", documentoId);
		Assert.assertEquals("Documentos devem ser iguais.", new DocumentoId(3L), documentoId);
		
		Mockito.verify(documentoRestClient, Mockito.times(1)).unir(command);
	}
	
	@Test
	public void dividir() {
		MultiValueMap<String, Object> command = new LinkedMultiValueMap<>();
		Map<String, Integer> primeiraQuebra = new HashMap<>(0);
		Map<String, Integer> segundaQuebra = new HashMap<>(0);
		
		command.add("documentoId", 1L);
		
		primeiraQuebra.put("paginaInicial", 1);
		primeiraQuebra.put("paginaFinal", 2);
		command.add("intervalos", primeiraQuebra);
		
		segundaQuebra.put("paginaInicial", 3);
		segundaQuebra.put("paginaFinal", 4);
		command.add("intervalos", segundaQuebra);
		
		Mockito.when(documentoRestClient.dividir(command)).thenReturn(Arrays.asList(2L, 3L));
		
		List<DocumentoId> documentosId = pecaRestAdapter.dividir(new DocumentoId(1L),
				Arrays.asList(Range.between(1, 2), Range.between(3, 4)));
		
		Assert.assertNotNull("Documento não pode ser nulo.", documentosId);
		Assert.assertEquals("Listas de documentos devem ser iguais.",
				Arrays.asList(new DocumentoId(2L), new DocumentoId(3L)), documentosId);
		
		Mockito.verify(documentoRestClient, Mockito.times(1)).dividir(command);
	}
    
}