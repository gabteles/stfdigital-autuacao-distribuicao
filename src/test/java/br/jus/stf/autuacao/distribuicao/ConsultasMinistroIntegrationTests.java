package br.jus.stf.autuacao.distribuicao;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import  br.jus.stf.core.framework.testing.IntegrationTestsSupport;
import br.jus.stf.core.framework.testing.oauth2.WithMockOauth2User;

/**
 * Teste de integração do serviço REST de ministros.
 * 
 * @author anderson.araujo
 * @since 18/05/2016
 *
 */
@SpringBootTest(value = {"server.port:0", "eureka.client.enabled:false", "spring.cloud.config.enabled:false"}, classes = ApplicationContextInitializer.class)
@WithMockOauth2User("distribuidor")
public class ConsultasMinistroIntegrationTests extends IntegrationTestsSupport {
	
	@Test
	public void listarMinistros() throws Exception{
		mockMvc.perform(get("/api/ministros")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(12)));
	}
}