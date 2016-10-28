package br.jus.stf.autuacao.distribuicao.infra.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.jus.stf.autuacao.distribuicao.interfaces.dto.DocumentoDto;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 04.08.2016
 */
@FeignClient(name = "documents")
public interface DocumentoRestClient {

    /**
     * @param documents
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/api/documentos", consumes = "application/json")
    List<Map<String, Object>> salvar(MultiValueMap<String, String> documents);

    /**
     * @param documents
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/api/documentos/{documentoId}/divisao",
            consumes = "application/json")
    List<Long> dividir(@PathVariable("documentoId") Long documentoId, Map<String, Object> documents);

    /**
     * @param documents
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/api/documentos/uniao", consumes = "application/json")
    Long unir(MultiValueMap<String, Long> documents);

    /**
     * @param documents
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/api/documentos/{documentoId}", consumes = "application/json")
    DocumentoDto consultar(@PathVariable("documentoId") Long documentoId);

}
