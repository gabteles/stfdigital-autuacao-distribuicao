package br.jus.stf.autuacao.distribuicao;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import br.jus.stf.autuacao.distribuicao.domain.Situacao;
import br.jus.stf.autuacao.distribuicao.domain.Visibilidade;
import br.jus.stf.autuacao.distribuicao.domain.model.Peca;
import br.jus.stf.autuacao.distribuicao.domain.model.PecaId;
import br.jus.stf.autuacao.distribuicao.domain.model.Processo;
import br.jus.stf.autuacao.distribuicao.domain.model.documento.TipoPeca;
import br.jus.stf.autuacao.distribuicao.domain.model.identidade.Ministro;
import br.jus.stf.core.shared.documento.DocumentoId;
import br.jus.stf.core.shared.documento.TipoDocumentoId;
import br.jus.stf.core.shared.identidade.MinistroId;
import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * Teste unitário para peça.
 * 
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 09.08.2016
 */
public class ProcessoUnitTests {
	
	@Test
	public void criaProcessoValido() {
		Processo processo = processoValido();
        
        Assert.assertNotNull("Processo não deve ser nulo.", processo);
        Assert.assertEquals("Identidade deve ser igual a 1.", new ProcessoId(1L), processo.identity());
        Assert.assertEquals("Ministro deve ser igual a 2.", new Ministro(new MinistroId(2L), "Ministro 2"), processo.relator());
        Assert.assertEquals("Processo não deve ter peças.", 0, processo.pecas().size());
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveCriarProcessoComIdNulo() {
		new Processo(null, new Ministro(new MinistroId(1L), "Ministro 1"));
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveCriarProcessoComMinistroNulo() {
		new Processo(new ProcessoId(1L), null);
	}
	
	@Test
	public void adicionarPeca() {
		Processo processo = processoValido();
		
		Assert.assertEquals("Processo não deve ter peças.", 0, processo.pecas().size());
		
		processo.adicionarPeca(pecaValida(1L));
		
		Assert.assertEquals("Processo deve ter 1 peça.", 1, processo.pecas().size());
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveAdicionarPecaNula() {
		Processo processo = processoValido();
		
		processo.adicionarPeca(null);
	}
	
	@Test
	public void juntarPeca() {
		Processo processo = processoValido();
		Peca peca = pecaValida(1L);
		
		processo.adicionarPeca(peca);
		
		Assert.assertEquals("Situação da peça deve ser PENDENTE_JUNTADA.", Situacao.PENDENTE_JUNTADA, processo.pecas().get(0).situacao());
		
		processo.juntarPeca(peca);
		
		Assert.assertEquals("Situação da peça deve ser JUNTADA.", Situacao.JUNTADA, processo.pecas().get(0).situacao());
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveJuntarPecaNula() {
		Processo processo = processoValido();
		
		processo.juntarPeca(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveJuntarPecaQueNaoPertenceAoProcesso() {
		Processo processo = processoValido();
		Peca peca = pecaValida(1L);
		
		processo.juntarPeca(peca);
	}
	
	@Test
	public void removerPeca() {
		Processo processo = processoValido();
		Peca peca = pecaValida(1L);
		
		processo.adicionarPeca(peca);
		
		Assert.assertEquals("Situação da peça deve ser PENDENTE_JUNTADA.", Situacao.PENDENTE_JUNTADA, processo.pecas().get(0).situacao());
		Assert.assertEquals("Processo deve ter 1 peça.", 1, processo.pecas().size());
		
		processo.removerPeca(peca);
		
		Assert.assertEquals("Situação da peça deve ser EXCLUIDA.", Situacao.EXCLUIDA, processo.pecas().get(0).situacao());
		Assert.assertEquals("Processo continua com 1 peça, pois a exclusão é lógica.", 1, processo.pecas().size());
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveRemoverPecaNula() {
		Processo processo = processoValido();
		
		processo.removerPeca(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveRemoverPecaQueNaoPertenceAoProcesso() {
		Processo processo = processoValido();
		Peca peca = pecaValida(1L);
		
		processo.removerPeca(peca);
	}
	
	@Test
	public void substituirPeca() {
		Processo processo = processoValido();
		Peca pecaOriginal = pecaValida(1L);
		Peca pecaSubstituta = pecaValida(2L);
		
		processo.adicionarPeca(pecaOriginal);
		processo.substituirPeca(pecaOriginal, pecaSubstituta);
		
		Assert.assertFalse("Peça original não deve mais pertencer ao processo.", processo.pecas().contains(pecaOriginal));
		Assert.assertTrue("Peça substituta deve pertencer ao processo.", processo.pecas().contains(pecaSubstituta));
		Assert.assertEquals("Processo deve continuar com 1 peça.", 1, processo.pecas().size());
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveSubstituirComPecaOriginalNula() {
		Processo processo = processoValido();
		Peca pecaSubstituta = pecaValida(2L);
		
		processo.substituirPeca(null, pecaSubstituta);
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveSubstituirComPecaSubstitutaNula() {
		Processo processo = processoValido();
		Peca pecaOriginal = pecaValida(1L);
		
		processo.adicionarPeca(pecaOriginal);
		processo.substituirPeca(pecaOriginal, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveSubstituirPecaComOriginalQueNaoPertenceAoProcesso() {
		Processo processo = processoValido();
		Peca pecaOriginal = pecaValida(1L);
		Peca pecaSubstituta = pecaValida(2L);
		
		processo.substituirPeca(pecaOriginal, pecaSubstituta);
	}
	
	@Test
	public void editarPeca() {
		Processo processo = processoValido();
		Peca pecaOriginal = pecaValida(1L);
		Peca despacho = pecaValida(2L);
		TipoPeca tipo = new TipoPeca(new TipoDocumentoId(101L), "Petição inicial");
		
		processo.adicionarPeca(pecaOriginal);
		processo.adicionarPeca(despacho);
		processo.editarPeca(pecaOriginal, tipo, tipo.nome(), 2, Visibilidade.PENDENTE_VISUALIZACAO);
		
		Peca peca = processo.pecas().get(1);
		
		Assert.assertEquals("Peça que não foi editada deve mudar para primeira da lista.", despacho, processo.pecas().get(0));
        Assert.assertEquals("Tipo de peça deve ser igual a 101.", tipo, peca.tipo());
        Assert.assertEquals("Descrição deve ser igual a Petição inicial.", tipo.nome(), peca.descricao());
        Assert.assertEquals("Visibilidade deve ser igual a PENDENTE_VISUALIZACAO.", Visibilidade.PENDENTE_VISUALIZACAO, peca.visibilidade());
        Assert.assertEquals("Número de ordem deve ser 2.", new Integer(2), peca.numeroOrdem());
        // Atributos que não devem ser modificados após a exução do método editarPeca(...)
        Assert.assertEquals("Documento não deve ser alterado.", new DocumentoId(1L), peca.documento());
        Assert.assertEquals("Situação não deve ser alterada.", Situacao.PENDENTE_JUNTADA, peca.situacao());
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveEditarPecaComPecaOriginalNula() {
		Processo processo = processoValido();
		Peca pecaOriginal = pecaValida(1L);
		TipoPeca tipo = new TipoPeca(new TipoDocumentoId(101L), "Petição inicial");
		
		processo.adicionarPeca(pecaOriginal);
		processo.editarPeca(null, tipo, tipo.nome(), 1, Visibilidade.PENDENTE_VISUALIZACAO);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveEditarPecaComOriginalQueNaoPertenceAoProcesso() {
		Processo processo = processoValido();
		Peca despacho = pecaValida(1L);
		Peca pecaOriginal = pecaValida(2L);
		TipoPeca tipo = new TipoPeca(new TipoDocumentoId(101L), "Petição inicial");
		
		processo.adicionarPeca(despacho);
		processo.editarPeca(pecaOriginal, tipo, tipo.nome(), 2, Visibilidade.PENDENTE_VISUALIZACAO);
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveEditarPecaComTipoNulo() {
		Processo processo = processoValido();
		Peca pecaOriginal = pecaValida(1L);
		
		processo.adicionarPeca(pecaOriginal);
		processo.editarPeca(pecaOriginal, null, "Peça inválida.", 1, Visibilidade.PENDENTE_VISUALIZACAO);
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveEditarPecaComDescricaoNula() {
		Processo processo = processoValido();
		Peca pecaOriginal = pecaValida(1L);
		TipoPeca tipo = new TipoPeca(new TipoDocumentoId(101L), "Petição inicial");
		
		processo.adicionarPeca(pecaOriginal);
		processo.editarPeca(pecaOriginal, tipo, null, 1, Visibilidade.PENDENTE_VISUALIZACAO);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveEditarPecaComDescricaoVazia() {
		Processo processo = processoValido();
		Peca pecaOriginal = pecaValida(1L);
		TipoPeca tipo = new TipoPeca(new TipoDocumentoId(101L), "Petição inicial");
		
		processo.adicionarPeca(pecaOriginal);
		processo.editarPeca(pecaOriginal, tipo, "", 1, Visibilidade.PENDENTE_VISUALIZACAO);
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveEditarPecaComNumeroOrdemNulo() {
		Processo processo = processoValido();
		Peca pecaOriginal = pecaValida(1L);
		TipoPeca tipo = new TipoPeca(new TipoDocumentoId(101L), "Petição inicial");
		
		processo.adicionarPeca(pecaOriginal);
		processo.editarPeca(pecaOriginal, tipo, tipo.nome(), null, Visibilidade.PENDENTE_VISUALIZACAO);
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveEditarPecaComVisibilidadeNula() {
		Processo processo = processoValido();
		Peca pecaOriginal = pecaValida(1L);
		TipoPeca tipo = new TipoPeca(new TipoDocumentoId(101L), "Petição inicial");
		
		processo.adicionarPeca(pecaOriginal);
		processo.editarPeca(pecaOriginal, tipo, tipo.nome(), 1, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveEditarPecaComNumeroOrdemForaDoIntervaloDeNumeracaoDasPecas() {
		Processo processo = processoValido();
		Peca pecaOriginal = pecaValida(1L);
		TipoPeca tipo = new TipoPeca(new TipoDocumentoId(101L), "Petição inicial");
		
		processo.adicionarPeca(pecaOriginal);
		processo.editarPeca(pecaOriginal, tipo, tipo.nome(), 2, Visibilidade.PENDENTE_VISUALIZACAO);
	}
	
	@Test
	public void dividirPeca() {
		Processo processo = processoValido();
		Peca pecaDividida = pecaValida(1L);
		Peca pedaco1 = pecaValida(2L);
		Peca pedaco2 = pecaValida(3L);
		
		processo.adicionarPeca(pecaDividida);
		processo.dividirPeca(pecaDividida, Arrays.asList(pedaco1, pedaco2));
		
		Assert.assertFalse("Peça dividida não deve pertencer a lista de peças.", processo.pecas().contains(pecaDividida));
		Assert.assertTrue("Primeiro pedaço deve pertencer a lista de peças.", processo.pecas().contains(pedaco1));
		Assert.assertTrue("Segundo pedaço deve pertencer a lista de peças.", processo.pecas().contains(pedaco2));
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveDividirPecaComPecaDivididaNula() {
		Processo processo = processoValido();
		Peca pecaDividida = pecaValida(1L);
		Peca pedaco1 = pecaValida(2L);
		Peca pedaco2 = pecaValida(3L);
		
		processo.adicionarPeca(pecaDividida);
		processo.dividirPeca(null, Arrays.asList(pedaco1, pedaco2));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveDividirPecaQueNaoPertencaAoProcesso() {
		Processo processo = processoValido();
		Peca pecaDividida = pecaValida(1L);
		Peca pedaco1 = pecaValida(2L);
		Peca pedaco2 = pecaValida(3L);
		
		processo.dividirPeca(pecaDividida, Arrays.asList(pedaco1, pedaco2));
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveDividirPecaComPecaDivisaoNula() {
		Processo processo = processoValido();
		Peca pecaDividida = pecaValida(1L);
		
		processo.adicionarPeca(pecaDividida);
		processo.dividirPeca(pecaDividida, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveDividirPecaComPecaDivisaoVazia() {
		Processo processo = processoValido();
		Peca pecaDividida = pecaValida(1L);
		
		processo.adicionarPeca(pecaDividida);
		processo.dividirPeca(pecaDividida, Collections.emptyList());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveDividirPecaEmApenasUmPedaco() {
		Processo processo = processoValido();
		Peca pecaDividida = pecaValida(1L);
		Peca pedaco1 = pecaValida(2L);
		
		processo.dividirPeca(pecaDividida, Arrays.asList(pedaco1));
	}
	
	@Test
	public void unirPecas() {
		Processo processo = processoValido();
		Peca pedaco1 = pecaValida(2L);
		Peca pedaco2 = pecaValida(3L);
		Peca pecaUnida = pecaValida(1L);
		
		processo.adicionarPeca(pedaco1);
		processo.adicionarPeca(pedaco2);
		processo.unirPecas(Arrays.asList(pedaco1, pedaco2), pecaUnida);
		
		Assert.assertTrue("Peça unida deve pertencer a lista de peças.", processo.pecas().contains(pecaUnida));
		Assert.assertFalse("Primeiro pedaço não deve pertencer a lista de peças.", processo.pecas().contains(pedaco1));
		Assert.assertFalse("Segundo pedaço não deve pertencer a lista de peças.", processo.pecas().contains(pedaco2));
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveUnirPecasComPecasUniaoNula() {
		Processo processo = processoValido();
		Peca pedaco1 = pecaValida(2L);
		Peca pedaco2 = pecaValida(3L);
		Peca pecaUnida = pecaValida(1L);
		
		processo.adicionarPeca(pedaco1);
		processo.adicionarPeca(pedaco2);
		processo.unirPecas(null, pecaUnida);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveUnirPecasComPecasUniaoVazia() {
		Processo processo = processoValido();
		Peca pedaco1 = pecaValida(2L);
		Peca pedaco2 = pecaValida(3L);
		Peca pecaUnida = pecaValida(1L);
		
		processo.adicionarPeca(pedaco1);
		processo.adicionarPeca(pedaco2);
		processo.unirPecas(Collections.emptyList(), pecaUnida);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveUnirPecasComMenosDeDuasEmPecasUniao() {
		Processo processo = processoValido();
		Peca pedaco1 = pecaValida(2L);
		Peca pedaco2 = pecaValida(3L);
		Peca pecaUnida = pecaValida(1L);
		
		processo.adicionarPeca(pedaco1);
		processo.adicionarPeca(pedaco2);
		processo.unirPecas(Arrays.asList(pedaco1), pecaUnida);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveUnirPecasComPecasUniaoQueNaoPertencamAoProcesso() {
		Processo processo = processoValido();
		Peca pedaco1 = pecaValida(2L);
		Peca pedaco2 = pecaValida(3L);
		Peca pecaUnida = pecaValida(1L);
		
		processo.adicionarPeca(pedaco1);
		processo.unirPecas(Arrays.asList(pedaco1, pedaco2), pecaUnida);
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveUnirPecasComPecaUnidaNula() {
		Processo processo = processoValido();
		Peca pedaco1 = pecaValida(1L);
		Peca pedaco2 = pecaValida(2L);
		
		processo.adicionarPeca(pedaco1);
		processo.adicionarPeca(pedaco2);
		processo.unirPecas(Arrays.asList(pedaco1, pedaco2), null);
	}
	
	@Test
	public void organizarPecas() {
		Processo processo = processoValido();
		Peca peca1 = pecaValida(1L);
		Peca peca2 = pecaValida(2L);
		
		processo.adicionarPeca(peca1);
		processo.adicionarPeca(peca2);
		
		Assert.assertEquals("Primeira peça deve peça 1.", peca1, processo.pecas().get(0));
		Assert.assertEquals("Segunda peça deve peça 2.", peca2, processo.pecas().get(1));
		
		processo.organizarPecas(Arrays.asList(2L, 1L));
		
		Assert.assertEquals("Primeira peça deve peça 2.", peca2, processo.pecas().get(0));
		Assert.assertEquals("Segunda peça deve peça 1.", peca1, processo.pecas().get(1));
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveOrganizarPecasComListaNula() {
		Processo processo = processoValido();
		Peca peca1 = pecaValida(1L);
		Peca peca2 = pecaValida(2L);
		
		processo.adicionarPeca(peca1);
		processo.adicionarPeca(peca2);
		processo.organizarPecas(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveOrganizarPecasComListaVazia() {
		Processo processo = processoValido();
		Peca peca1 = pecaValida(1L);
		Peca peca2 = pecaValida(2L);
		
		processo.adicionarPeca(peca1);
		processo.adicionarPeca(peca2);
		processo.organizarPecas(Collections.emptyList());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveOrganizarPecasQueModifiqueTamanhoDaListaDePecas() {
		Processo processo = processoValido();
		Peca peca1 = pecaValida(1L);
		Peca peca2 = pecaValida(2L);
		
		processo.adicionarPeca(peca1);
		processo.adicionarPeca(peca2);
		processo.organizarPecas(Arrays.asList(2L));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveOrganizarPecasComPecaQueNaoPertenceAoProcesso() {
		Processo processo = processoValido();
		Peca peca1 = pecaValida(1L);
		
		processo.adicionarPeca(peca1);
		processo.organizarPecas(Arrays.asList(3L));
	}
	
	private Processo processoValido() {
		return new Processo(new ProcessoId(1L), new Ministro(new MinistroId(2L), "Ministro 2"));
	}
	
	private Peca pecaValida(Long identificador) {
		TipoPeca tipo = new TipoPeca(new TipoDocumentoId(1060L), "Despacho de Relator");
        
		return new Peca(new PecaId(identificador), new DocumentoId(identificador), tipo, tipo.nome(), Visibilidade.PUBLICO, Situacao.PENDENTE_JUNTADA);
	}
    
}