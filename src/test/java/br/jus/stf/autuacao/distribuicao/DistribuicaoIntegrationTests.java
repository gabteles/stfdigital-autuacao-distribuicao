package br.jus.stf.autuacao.distribuicao;

import static br.jus.stf.core.framework.testing.Oauth2TestHelpers.oauthAuthentication;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import br.jus.stf.autuacao.distribuicao.domain.model.Distribuidor;
import br.jus.stf.autuacao.distribuicao.infra.DistribuidorOauth2Adapter;
import br.jus.stf.core.framework.testing.IntegrationTestsSupport;
import br.jus.stf.core.shared.identidade.PessoaId;

/**
 * Valida a API de distribuição de processos.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 17.02.2016
 */
@SpringBootTest(value = {"server.port:0", "eureka.client.enabled:false"}, classes = ApplicationContextInitializer.class)
public class DistribuicaoIntegrationTests extends IntegrationTestsSupport {
	
	@MockBean
	private DistribuidorOauth2Adapter distribuidorOauth2Adapter;
	
	@Before
	public void configuracao() {
		given(distribuidorOauth2Adapter.distribuidor()).willReturn(new Distribuidor("distribuidor", new PessoaId(1L)));
	}
	
	@Test
	public void distribuiProcessoComum() throws Exception {
		loadDataTests("distribuirProcessoEletronicoOriginario.sql");

		String processo = "{\"distribuicaoId\":@distribuicaoId,\"processoId\":9002,\"tipoDistribuicao\":\"COMUM\",\"ministrosCandidatos\":[28,30,36,42,44,45,46,47,48,49],\"ministrosImpedidos\":[1,41]}";
		String distribuicaoId = "9000";
		ResultActions result = mockMvc.perform(post("/api/distribuicao").with(oauthAuthentication("autuador")).contentType(APPLICATION_JSON).content(processo.replace("@distribuicaoId", distribuicaoId)));
		
		result.andExpect(status().isOk());
	}

	@Test
	public void distribuiProcessoPrevencao() throws Exception {
		loadDataTests("distribuirProcessoFisicoOriginario.sql");
		
		String processo = "{\"distribuicaoId\":@distribuicaoId,\"processoId\":9000,\"justificativa\":\"Processos correlatos.\",\"tipoDistribuicao\":\"PREVENCAO\", \"processosPreventos\":[5521,4978823]}";
		String distribuicaoId = "9001";
		ResultActions result = mockMvc.perform(post("/api/distribuicao").with(oauthAuthentication("autuador")).contentType(APPLICATION_JSON).content(processo.replace("@distribuicaoId", distribuicaoId)));
		
		result.andExpect(status().isOk());
	}

	@Test
    public void naoDeveDistribuirUmProcessoInvalido() throws Exception {
        String processo = "{}";
        
        ResultActions result = mockMvc.perform(post("/api/distribuicao").contentType(APPLICATION_JSON).content(processo));
        
        result.andExpect(status().isBadRequest());
    }
	
	@Test
	public void naoDeveDistribuirComumQuandoHaSobreposicaoDeMinistros() throws Exception {
		loadDataTests("distribuicaoQueNaoDevePassar.sql");
		
		String processo = "{\"distribuicaoId\":@distribuicaoId,\"processoId\":9003,\"tipoDistribuicao\":\"COMUM\",\"ministrosCandidatos\":[28,30,36,42,44,45,46,47,48,49],\"ministrosImpedidos\":[28,1,41]}";
		String distribuicaoId = "9002";
		ResultActions result = mockMvc.perform(post("/api/distribuicao").contentType(APPLICATION_JSON).content(processo.replace("@distribuicaoId", distribuicaoId)));
		
		result.andExpect(status().isBadRequest());
		
		loadDataTests("distribuicaoQueNaoDevePassar-limpar.sql");
	}
	
	@Test
	public void naoDeveDistribuirPrevencaoQuandoProcessosTiveremRelatorDiferentes() throws Exception {
		loadDataTests("distribuicaoQueNaoDevePassar.sql");
		
		String processo = "{\"distribuicaoId\":@distribuicaoId,\"processoId\":9003,\"justificativa\":\"Processos com relatores diferentes.\",\"tipoDistribuicao\":\"PREVENCAO\", \"processosPreventos\":[5585,5521,4978823]}";
		String distribuicaoId = "9002";
		ResultActions result = mockMvc.perform(post("/api/distribuicao").contentType(APPLICATION_JSON).content(processo.replace("@distribuicaoId", distribuicaoId)));
		
		result.andExpect(status().isBadRequest());
		
		loadDataTests("distribuicaoQueNaoDevePassar-limpar.sql");
	}
	
	@Test
	public void naoDeveDistribuirPrevencaoSemJustificativa() throws Exception {
		loadDataTests("distribuicaoQueNaoDevePassar.sql");
		
		String processo = "{\"distribuicaoId\":@distribuicaoId,\"processoId\":9003,\"tipoDistribuicao\":\"PREVENCAO\", \"processosPreventos\":[5521,4978823]}";
		String distribuicaoId = "9002";
		ResultActions result = mockMvc.perform(post("/api/distribuicao").with(oauthAuthentication("autuador")).contentType(APPLICATION_JSON).content(processo.replace("@distribuicaoId", distribuicaoId)));
		
		result.andExpect(status().isBadRequest());
		
		loadDataTests("distribuicaoQueNaoDevePassar-limpar.sql");
	}
    
}
