package br.jus.stf.autuacao.distribuicao;

import org.junit.Assert;
import org.junit.Test;

import br.jus.stf.autuacao.distribuicao.domain.Situacao;
import br.jus.stf.autuacao.distribuicao.domain.Visibilidade;
import br.jus.stf.autuacao.distribuicao.domain.model.Peca;
import br.jus.stf.autuacao.distribuicao.domain.model.Processo;
import br.jus.stf.autuacao.distribuicao.domain.model.documento.TipoPeca;
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
        Assert.assertEquals("Ministro deve ser igual a 2.", new MinistroId(2L), processo.relator());
        Assert.assertEquals("Processo não deve ter peças.", 0, processo.pecas().size());
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveCriarProcessoComIdNulo() {
		new Processo(null, new MinistroId(1L));
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
	
	private Processo processoValido() {
		return new Processo(new ProcessoId(1L), new MinistroId(2L));
	}
	
	private Peca pecaValida(Long identificador) {
		TipoPeca tipo = new TipoPeca(new TipoDocumentoId(1060L), "Despacho de Relator");
        
		return new Peca(identificador, new DocumentoId(identificador), tipo, tipo.nome(), Visibilidade.PUBLICO, Situacao.PENDENTE_JUNTADA);
	}
    
}