package br.jus.stf.autuacao.distribuicao;

import static com.github.jsonj.tools.JsonBuilder.array;
import static com.github.jsonj.tools.JsonBuilder.field;
import static com.github.jsonj.tools.JsonBuilder.object;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;

import com.github.jsonj.JsonObject;

import br.jus.stf.core.framework.testing.IntegrationTestsSupport;
import br.jus.stf.core.framework.testing.oauth2.WithMockOauth2User;

/**
 * Valida a API de distribuição de processos.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 17.02.2016
 */
@SpringBootTest(value = {"server.port:0", "eureka.client.enabled:false", "spring.cloud.config.enabled:false"}, classes = ApplicationContextInitializer.class)
@WithMockOauth2User("distribuidor")
public class DistribuicaoIntegrationTests extends IntegrationTestsSupport {
	
	@Test
	@WithMockOauth2User(value = "organizador-pecas", components = "distribuir-processo-comum")
	public void distribuiProcessoComum() throws Exception {
		loadDataTests("distribuirProcessoEletronicoOriginario.sql");

		JsonObject distribuirProcessoComumJson = object(
				field("distribuicaoId", 9000),
				field("processoId", 9002),
				field("tipoDistribuicao", "COMUM"),
				field("ministrosCandidatos", array(28, 30, 36, 42, 44, 45, 46, 47, 48, 49)),
				field("ministrosImpedidos", array(1, 41))
		);
		ResultActions result = mockMvc.perform(post("/api/distribuicao/comum").contentType(APPLICATION_JSON).content(distribuirProcessoComumJson.toString()));
		
		result.andExpect(status().isOk());
	}

	@Test
	@WithMockOauth2User(value = "organizador-pecas", components = "distribuir-processo-prevencao")
	public void distribuiProcessoPrevencao() throws Exception {
		loadDataTests("distribuirProcessoFisicoOriginario.sql");
		
		JsonObject distribuirProcessoPrevencaoJson = object(
				field("distribuicaoId", 9001),
				field("processoId", 9000),
				field("justificativa", "Processos correlatos."),
				field("tipoDistribuicao", "PREVENCAO"),
				field("processosPreventos", array(5521,4978823))
		);
		ResultActions result = mockMvc.perform(post("/api/distribuicao/prevencao").contentType(APPLICATION_JSON).content(distribuirProcessoPrevencaoJson.toString()));
		
		result.andExpect(status().isOk());
	}

	@Test
    public void naoDeveDistribuirUmProcessoInvalido() throws Exception {
        String processoInvalido = "{}";
        ResultActions result = mockMvc.perform(post("/api/distribuicao/prevencao").contentType(APPLICATION_JSON).content(processoInvalido));
        
        result.andExpect(status().isBadRequest());
    }
	
	@Test
	@WithMockOauth2User(value = "organizador-pecas", components = "distribuir-processo-comum")
	public void naoDeveDistribuirComumQuandoHaSobreposicaoDeMinistros() throws Exception {
		loadDataTests("distribuicaoQueNaoDevePassar-limpar.sql", "distribuicaoQueNaoDevePassar.sql");
		
		JsonObject processoInvalidoJson = object(
				field("distribuicaoId", 9002),
				field("processoId", 9003),
				field("tipoDistribuicao", "COMUM"),
				field("ministrosCandidatos", array(28,30,36,42,44,45,46,47,48,49)),
				field("ministrosImpedidos", array(28,1,41))
		);
		ResultActions result = mockMvc.perform(post("/api/distribuicao/prevencao").contentType(APPLICATION_JSON).content(processoInvalidoJson.toString()));
		
		result.andExpect(status().isBadRequest());
	}
	
	@Test
	@WithMockOauth2User(value = "organizador-pecas", components = "distribuir-processo-prevencao")
	public void naoDeveDistribuirPrevencaoQuandoProcessosTiveremRelatorDiferentes() throws Exception {
		loadDataTests("distribuicaoQueNaoDevePassar-limpar.sql", "distribuicaoQueNaoDevePassar.sql");
		
		JsonObject processoInvalidoJson = object(
				field("distribuicaoId", 9002),
				field("processoId", 9003),
				field("justificativa", "Processos com relatores diferentes."),
				field("tipoDistribuicao", "PREVENCAO"),
				field("processosPreventos", array(5585,5521,4978823))
		);
		ResultActions result = mockMvc.perform(post("/api/distribuicao/prevencao").contentType(APPLICATION_JSON).content(processoInvalidoJson.toString()));
		
		result.andExpect(status().isBadRequest());
	}
	
	@Test
	public void naoDeveDistribuirPrevencaoSemJustificativa() throws Exception {
		loadDataTests("distribuicaoQueNaoDevePassar-limpar.sql", "distribuicaoQueNaoDevePassar.sql");
		
		JsonObject processoInvalido = object(
				field("distribuicaoId", 9002),
				field("processoId", 9003),
				field("tipoDistribuicao", "PREVENCAO"),
				field("processosPreventos", array(5521,4978823))
		);
		ResultActions result = mockMvc.perform(post("/api/distribuicao/prevencao").contentType(APPLICATION_JSON).content(processoInvalido.toString()));
		
		result.andExpect(status().isBadRequest());
	}
    
}
