package br.jus.stf.autuacao.distribuicao;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import br.jus.stf.autuacao.distribuicao.application.DistribuicaoApplicationService;
import br.jus.stf.autuacao.distribuicao.application.commands.IniciarDistribuicaoCommand;
import br.jus.stf.core.framework.testing.IntegrationTestsSupport;

/**
 * Valida a API de distribuição de processos.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 17.02.2016
 */
@Ignore
@SpringApplicationConfiguration(ApplicationContextInitializer.class)
public class DistribuicaoIntegrationTests extends IntegrationTestsSupport {
    
	@Autowired
	private DistribuicaoApplicationService distribuicaoAppService;

	@Test
	public void distribuiProcessoComum() throws Exception {
		IniciarDistribuicaoCommand command = new IniciarDistribuicaoCommand(1L);
		String distribuicaoId = distribuicaoAppService.handle(command).toString();
		
		String processo = "{\"distribuicaoId\":@distribuicaoId,\"processoId\":1,\"tipoDistribuicao\":\"COMUM\",\"ministrosCandidatos\":[28,30,36,42,44,45,46,47,48,49],\"ministrosImpedidos\":[1,41]}";
		ResultActions result = mockMvc.perform(post("/api/distribuicao").contentType(APPLICATION_JSON).content(processo.replace("@distribuicaoId", distribuicaoId)));
		result.andExpect(status().isOk());
	}
	
	@Test
	public void distribuiProcessoPrevencao() throws Exception {
		IniciarDistribuicaoCommand command = new IniciarDistribuicaoCommand(1L);
		String distribuicaoId = distribuicaoAppService.handle(command).toString();
		
		String processo = "{\"distribuicaoId\":@distribuicaoId,\"processoId\":2,\"justificativa\":\"Processos correlatos.\",\"tipoDistribuicao\":\"PREVENCAO\", \"processosPreventos\":[5521,4978823]}";
		ResultActions result = mockMvc.perform(post("/api/distribuicao").contentType(APPLICATION_JSON).content(processo.replace("@distribuicaoId", distribuicaoId)));
		result.andExpect(status().isOk());
	}

	@Test
    public void naoDeveDistribuirUmProcessoInvalido() throws Exception {
        String processo = "{}";
        
        ResultActions result = mockMvc.perform(post("/api/distribuicao").contentType(APPLICATION_JSON).content(processo));
        
        result.andExpect(status().isBadRequest());
    }
    
}
