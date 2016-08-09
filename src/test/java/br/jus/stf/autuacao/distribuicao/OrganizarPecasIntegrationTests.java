package br.jus.stf.autuacao.distribuicao;

import static com.github.jsonj.tools.JsonBuilder.array;
import static com.github.jsonj.tools.JsonBuilder.field;
import static com.github.jsonj.tools.JsonBuilder.object;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.SQLException;
import java.util.Arrays;

import org.apache.commons.lang3.Range;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;

import com.github.jsonj.JsonObject;

import br.jus.stf.autuacao.distribuicao.domain.PecaAdapter;
import br.jus.stf.core.framework.testing.IntegrationTestsSupport;
import br.jus.stf.core.framework.testing.oauth2.WithMockOauth2User;
import br.jus.stf.core.shared.documento.DocumentoId;
import br.jus.stf.core.shared.documento.DocumentoTemporarioId;

/**
 * Valida a API de organização de peças.
 * 
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 05.08.2016
 */
@SpringBootTest(value = {"server.port:0", "eureka.client.enabled:false"}, classes = ApplicationContextInitializer.class)
@WithMockOauth2User("organizador-pecas")
@ActiveProfiles("development")
public class OrganizarPecasIntegrationTests extends IntegrationTestsSupport {
	
	@MockBean
	private PecaAdapter pecaAdapter;
	
	private String idDocTemp = "_DocTemp_";
	
	@Before
	public void configuracao() throws SQLException {
		loadDataTests("manipularPecas-limpar.sql", "manipularPecas.sql");
		
		given(pecaAdapter.salvar(Arrays.asList(new DocumentoTemporarioId(idDocTemp)))).willReturn(Arrays.asList(new DocumentoId(3L)));
		given(pecaAdapter.unir(Arrays.asList(new DocumentoId(2L), new DocumentoId(1L)))).willReturn(new DocumentoId(4L));
		given(pecaAdapter.dividir(new DocumentoId(1L), Arrays.asList(Range.between(1, 2), Range.between(3, 4)))).willReturn(Arrays.asList(new DocumentoId(5L), new DocumentoId(6L)));
	}
	
	@Test
	public void inserirPeca() throws Exception {
		JsonObject pecaJson = object(
				field("processoId", 9002),
				field("pecas", array(object(
						field("documentoTemporarioId", idDocTemp),
						field("tipoPecaId", 101),
						field("numeroOrdem", 1))))
		);
		ResultActions result = mockMvc.perform(post("/api/organizacao-pecas/inserir").contentType(APPLICATION_JSON).content(pecaJson.toString()));
		
		result.andExpect(status().isOk());
	}
	
	@Test
	public void naoDeveInserirPecaSemDadosObrigatorios() throws Exception {
		JsonObject pecaJson = object(
				field("pecas", array(object(
						field("documentoTemporarioId", idDocTemp),
						field("tipoPecaId", 101),
						field("numeroOrdem", 1))))
		);
		ResultActions result = mockMvc.perform(post("/api/organizacao-pecas/inserir").contentType(APPLICATION_JSON).content(pecaJson.toString()));
		
		result.andExpect(status().isBadRequest());
	}
	
	@Test
	public void excluirPeca() throws Exception {
		JsonObject pecaJson = object(
				field("processoId", 9002),
				field("pecas", array(9000, 9001))
		);
		ResultActions result = mockMvc.perform(post("/api/organizacao-pecas/excluir").contentType(APPLICATION_JSON).content(pecaJson.toString()));
		
		result.andExpect(status().isOk());
	}
	
	@Test
	public void naoDeveExcluirPecaSemDadosObrigatorios() throws Exception {
		JsonObject pecaJson = object(
				field("pecas", array(9000, 9001))
		);
		ResultActions result = mockMvc.perform(post("/api/organizacao-pecas/excluir").contentType(APPLICATION_JSON).content(pecaJson.toString()));
		
		result.andExpect(status().isBadRequest());
	}
	
	@Test
	public void juntarPeca() throws Exception {
		JsonObject pecaJson = object(
				field("processoId", 9002),
				field("pecaId", 9001)
		);
		ResultActions result = mockMvc.perform(post("/api/organizacao-pecas/juntar").contentType(APPLICATION_JSON).content(pecaJson.toString()));
		
		result.andExpect(status().isOk());
	}
	
	@Test
	public void naoDeveJuntarPecaSemDadosObrigatorios() throws Exception {
		JsonObject pecaJson = object(
				field("processoId", 9002)
		);
		ResultActions result = mockMvc.perform(post("/api/organizacao-pecas/juntar").contentType(APPLICATION_JSON).content(pecaJson.toString()));
		
		result.andExpect(status().isBadRequest());
	}
	
	@Test
	public void editarPeca() throws Exception {
		JsonObject pecaJson = object(
				field("processoId", 9002),
				field("pecaId", 9001),
				field("tipoPecaId", 1060),
				field("descricao", "Despacho de Relator"),
				field("numeroOrdem", 2),
				field("visibilidade", "PUBLICO")
		);
		ResultActions result = mockMvc.perform(post("/api/organizacao-pecas/editar").contentType(APPLICATION_JSON).content(pecaJson.toString()));
		
		result.andExpect(status().isOk());
	}
	
	@Test
	public void naoDeveEditarPecaSemDadosObrigatorios() throws Exception {
		JsonObject pecaJson = object(
				field("processoId", 9002),
				field("pecaId", 9001),
				field("tipoPecaId", 1060),
				field("numeroOrdem", 2),
				field("visibilidade", "PUBLICO")
		);
		ResultActions result = mockMvc.perform(post("/api/organizacao-pecas/editar").contentType(APPLICATION_JSON).content(pecaJson.toString()));
		
		result.andExpect(status().isBadRequest());
	}
    
	@Test
	public void organizarPecasSemFinalizarTarefa() throws Exception {
		JsonObject pecaJson = object(
				field("distribuicaoId", 9003),
				field("pecas", array(9001, 9000)),
				field("finalizarTarefa", false)
		);
		ResultActions result = mockMvc.perform(post("/api/organizacao-pecas/organizar").contentType(APPLICATION_JSON).content(pecaJson.toString()));
		
		result.andExpect(status().isOk());
	}
	
	@Test
	public void organizarPecasFinalizandoTarefa() throws Exception {
		loadDataTests("organizarPecasFinalizandoTarefa.sql");
		
		JsonObject pecaJson = object(
				field("distribuicaoId", 9004),
				field("pecas", array(9003, 9002)),
				field("finalizarTarefa", true)
		);
		ResultActions result = mockMvc.perform(post("/api/organizacao-pecas/organizar").contentType(APPLICATION_JSON).content(pecaJson.toString()));
		
		result.andExpect(status().isOk());
	}
	
	@Test
	public void naoDeveOrganizarPecasSemDadosObrigatorios() throws Exception {
		JsonObject pecaJson = object(
				field("distribuicaoId", 9003),
				field("pecas", array(9001, 9000))
		);
		ResultActions result = mockMvc.perform(post("/api/organizacao-pecas/organizar").contentType(APPLICATION_JSON).content(pecaJson.toString()));
		
		result.andExpect(status().isBadRequest());
	}
	
	@Test
	public void unirPecas() throws Exception {
		JsonObject pecaJson = object(
				field("processoId", 9002),
				field("pecas", array(9001, 9000))
		);
		ResultActions result = mockMvc.perform(post("/api/organizacao-pecas/unir").contentType(APPLICATION_JSON).content(pecaJson.toString()));
		
		result.andExpect(status().isOk());
	}
	
	@Test
	public void naoDeveUnirPecasSemDadosObrigatorios() throws Exception {
		JsonObject pecaJson = object(
				field("processoId", 9002),
				field("pecas", array())
		);
		ResultActions result = mockMvc.perform(post("/api/organizacao-pecas/unir").contentType(APPLICATION_JSON).content(pecaJson.toString()));
		
		result.andExpect(status().isBadRequest());
	}
	
	@Test
	public void dividirPeca() throws Exception {
		JsonObject pecaJson = object(
				field("processoId", 9002),
				field("pecaOriginalId", 9000),
				field("pecas", array(
						object(
								field("documentoTemporarioId", idDocTemp),
								field("tipoPecaId", 26L),
								field("visibilidade", "PUBLICO"),
								field("situacao", "JUNTADA"),
								field("descricao", "Petição"),
								field("paginaInicial", 1),
								field("paginaFinal", 2)
						),
						object(
								field("documentoTemporarioId", idDocTemp),
								field("tipoPecaId", 97L),
								field("visibilidade", "PUBLICO"),
								field("situacao", "JUNTADA"),
								field("descricao", "Aviso de Recebimento"),
								field("paginaInicial", 3),
								field("paginaFinal", 4)
				))));
		ResultActions result = mockMvc.perform(post("/api/organizacao-pecas/dividir").contentType(APPLICATION_JSON).content(pecaJson.toString()));
		
		result.andExpect(status().isOk());
	}
	
	@Test
	public void naoDeveDividirPecaSemDadosObrigatorios() throws Exception {
		JsonObject pecaJson = object(
				field("processoId", 9002),
				field("pecas", array(
						object(
								field("documentoTemporarioId", idDocTemp),
								field("tipoPecaId", 26L),
								field("visibilidade", "PUBLICO"),
								field("situacao", "JUNTADA"),
								field("descricao", "Petição"),
								field("paginaInicial", 1),
								field("paginaFinal", 2)
						),
						object(
								field("documentoTemporarioId", idDocTemp),
								field("tipoPecaId", 97L),
								field("visibilidade", "PUBLICO"),
								field("situacao", "JUNTADA"),
								field("descricao", "Aviso de Recebimento"),
								field("paginaInicial", 3),
								field("paginaFinal", 4)
				))));
		ResultActions result = mockMvc.perform(post("/api/organizacao-pecas/dividir").contentType(APPLICATION_JSON).content(pecaJson.toString()));
		
		result.andExpect(status().isBadRequest());
	}
	
}