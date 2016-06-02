package br.jus.stf.autuacao.distribuicao;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.web.servlet.ResultActions;

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
    
	@Test
	public void distribuiProcessoComum() throws Exception {
		loadDataTests("distribuirProcessoEletronicoOriginario.sql");

		String processo = "{\"distribuicaoId\":@distribuicaoId,\"processoId\":9002,\"tipoDistribuicao\":\"COMUM\",\"ministrosCandidatos\":[28,30,36,42,44,45,46,47,48,49],\"ministrosImpedidos\":[1,41]}";
		String distribuicaoId = "9000";
		ResultActions result = mockMvc.perform(post("/api/distribuicao").contentType(APPLICATION_JSON).content(processo.replace("@distribuicaoId", distribuicaoId)));
		
		result.andExpect(status().isOk());
	}
	
	@Test
	public void distribuiProcessoPrevencao() throws Exception {
		loadDataTests("distribuirProcessoFisicoOriginario.sql");
		
		String processo = "{\"distribuicaoId\":@distribuicaoId,\"processoId\":9000,\"justificativa\":\"Processos correlatos.\",\"tipoDistribuicao\":\"PREVENCAO\", \"processosPreventos\":[5521,4978823]}";
		String distribuicaoId = "9001";
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
