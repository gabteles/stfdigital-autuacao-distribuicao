package br.jus.stf.autuacao.distribuicao.infra.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.jus.stf.autuacao.distribuicao.interfaces.dto.ProcessoDto;

/**
 * Cliente REST para consultar o processo
 * @author viniciusk
 *
 */
@FeignClient(name="autuacao")
public interface ProcessoRestClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/processos/{id}")
	ProcessoDto processo(@PathVariable("id") Long processoId);
}
