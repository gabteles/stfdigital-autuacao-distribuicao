package br.jus.stf.autuacao.distribuicao.infra.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.jus.stf.autuacao.distribuicao.interfaces.dto.ProcessoConsultaDto;


@FeignClient(name="processos")
public interface ProcessoRestClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/pesquisa-avancada", consumes = "application/json")
	List<ProcessoConsultaDto> pesquisar(Map<String, String> partes);

}
