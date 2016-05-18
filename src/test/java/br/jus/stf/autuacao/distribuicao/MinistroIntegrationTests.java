package br.jus.stf.autuacao.distribuicao;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;

import  br.jus.stf.core.framework.testing.IntegrationTestsSupport;

/**
 * Teste de integração do serviço REST de ministros.
 * 
 * @author anderson.araujo
 * @since 18/05/2016
 *
 */
@Ignore
@SpringApplicationConfiguration(ApplicationContextInitializer.class)
public class MinistroIntegrationTests extends IntegrationTestsSupport {
	
	@Test
	public void listarMinistros() throws Exception{
		mockMvc.perform(get("/api/ministros")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(11)));
	}
}

