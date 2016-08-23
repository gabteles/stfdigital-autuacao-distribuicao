package br.jus.stf.autuacao.distribuicao;

import org.junit.Assert;
import org.junit.Test;

import br.jus.stf.autuacao.distribuicao.domain.Situacao;
import br.jus.stf.autuacao.distribuicao.domain.Visibilidade;
import br.jus.stf.autuacao.distribuicao.domain.model.Peca;
import br.jus.stf.autuacao.distribuicao.domain.model.PecaId;
import br.jus.stf.autuacao.distribuicao.domain.model.documento.TipoPeca;
import br.jus.stf.core.shared.documento.DocumentoId;
import br.jus.stf.core.shared.documento.TipoDocumentoId;

/**
 * Teste unitário para peça.
 * 
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 08.08.2016
 */
public class PecaUnitTests {
	
	@Test
	public void criaPecaValida() {
		TipoPeca tipo = new TipoPeca(new TipoDocumentoId(1060L), "Despacho de Relator");
        Peca peca = pecaValida();
        
        Assert.assertNotNull("Peça não deve ser nula.", peca);
        Assert.assertEquals("Identidade deve ser igual a 1.", new PecaId(1L), peca.identity());
        Assert.assertEquals("Documento deve ser igual a 1.", new DocumentoId(1L), peca.documento());
        Assert.assertEquals("Tipo de peça deve ser igual a 1060.", tipo, peca.tipo());
        Assert.assertEquals("Descrição deve ser igual a Despacho de Relator.", tipo.nome(), peca.descricao());
        Assert.assertEquals("Visibilidade deve ser igual a PUBLICO.", Visibilidade.PUBLICO, peca.visibilidade());
        Assert.assertEquals("Situação deve ser igual a PENDENTE_JUNTADA.", Situacao.PENDENTE_JUNTADA, peca.situacao());
        Assert.assertNull("Número de ordem deve ser nulo.", peca.numeroOrdem());
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveCriarPecaComIdNulo() {
		TipoPeca tipo = new TipoPeca(new TipoDocumentoId(1060L), "Despacho de Relator");
        
		new Peca(null, new DocumentoId(1L), tipo, tipo.nome(), Visibilidade.PUBLICO, Situacao.PENDENTE_JUNTADA);
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveCriarPecaComDocumentoNulo() {
		TipoPeca tipo = new TipoPeca(new TipoDocumentoId(1060L), "Despacho de Relator");
        
		new Peca(new PecaId(1L), null, tipo, tipo.nome(), Visibilidade.PUBLICO, Situacao.PENDENTE_JUNTADA);
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveCriarPecaComTipoPecaNulo() {
		new Peca(new PecaId(1L), new DocumentoId(1L), null, "Nulo", Visibilidade.PUBLICO, Situacao.PENDENTE_JUNTADA);
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveCriarPecaComDescricaoNula() {
		TipoPeca tipo = new TipoPeca(new TipoDocumentoId(1060L), "Despacho de Relator");
        
		new Peca(new PecaId(1L), new DocumentoId(1L), tipo, null, Visibilidade.PUBLICO, Situacao.PENDENTE_JUNTADA);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveCriarPecaComDescricaoVazia() {
		TipoPeca tipo = new TipoPeca(new TipoDocumentoId(1060L), "Despacho de Relator");
        
		new Peca(new PecaId(1L), new DocumentoId(1L), tipo, "", Visibilidade.PUBLICO, Situacao.PENDENTE_JUNTADA);
	}
	
	@Test
	public void criaPecaComVisualizacaoESituacaoDefault() {
		TipoPeca tipo = new TipoPeca(new TipoDocumentoId(1060L), "Despacho de Relator");
        
		Peca peca = new Peca(new PecaId(1L), new DocumentoId(1L), tipo, tipo.nome(), null, null);
		
		Assert.assertEquals("Visibilidade deve ser igual a PUBLICO.", Visibilidade.PUBLICO, peca.visibilidade());
        Assert.assertEquals("Situação deve ser igual a JUNTADA.", Situacao.JUNTADA, peca.situacao());
	}
	
	@Test
	public void juntaPeca() {
		Peca peca = pecaValida();
		
		peca.juntar();
		
		Assert.assertEquals("Situação deve ser igual a JUNTADA.", Situacao.JUNTADA, peca.situacao());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveJuntarPecaComSituacaoInvalida() {
		TipoPeca tipo = new TipoPeca(new TipoDocumentoId(1060L), "Despacho de Relator");
        Peca peca = new Peca(new PecaId(1L), new DocumentoId(1L), tipo, tipo.nome(), Visibilidade.PUBLICO, Situacao.JUNTADA);
		
		peca.juntar();
	}
	
	@Test
	public void excluiPeca() {
		Peca peca = pecaValida();
		
		peca.excluir();
		
		Assert.assertEquals("Situação deve ser igual a EXCLUIDA.", Situacao.EXCLUIDA, peca.situacao());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveExcluirPecaComSituacaoInvalida() {
		TipoPeca tipo = new TipoPeca(new TipoDocumentoId(1060L), "Despacho de Relator");
        Peca peca = new Peca(new PecaId(1L), new DocumentoId(1L), tipo, tipo.nome(), Visibilidade.PUBLICO, Situacao.EXCLUIDA);
		
		peca.excluir();
	}
	
	@Test
	public void alteraOrdemDaPeca() {
		Peca peca = pecaValida();
		
		peca.alterarOrdem(1);
		
		Assert.assertEquals("Número de ordem deve ser igual a 1.", new Integer(1), peca.numeroOrdem());
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveAlterarOrdemDaPecaParaNulo() {
		Peca peca = pecaValida();
		
		peca.alterarOrdem(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveAlterarOrdemDaPecaParaZero() {
		Peca peca = pecaValida();
		
		peca.alterarOrdem(0);
	}
	
	@Test
	public void editaPeca() {
		TipoPeca tipo = new TipoPeca(new TipoDocumentoId(101L), "Petição inicial");
		Peca peca = pecaValida();
        
		peca.editar(tipo, tipo.nome(), Visibilidade.PENDENTE_VISUALIZACAO);
		
        Assert.assertEquals("Tipo de peça deve ser igual a 101.", tipo, peca.tipo());
        Assert.assertEquals("Descrição deve ser igual Petição inicial.", tipo.nome(), peca.descricao());
        Assert.assertEquals("Visibilidade deve ser igual a PENDENTE_VISUALIZACAO.", Visibilidade.PENDENTE_VISUALIZACAO, peca.visibilidade());
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveEditarPecaComTipoNulo() {
		Peca peca = pecaValida();
        
		peca.editar(null, "Nulo", Visibilidade.PENDENTE_VISUALIZACAO);
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveEditarPecaComDescricaoNula() {
		TipoPeca tipo = new TipoPeca(new TipoDocumentoId(101L), "Petição inicial");
		Peca peca = pecaValida();
        
		peca.editar(tipo, null, Visibilidade.PENDENTE_VISUALIZACAO);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveEditarPecaComDescricaoVazia() {
		TipoPeca tipo = new TipoPeca(new TipoDocumentoId(101L), "Petição inicial");
		Peca peca = pecaValida();
        
		peca.editar(tipo, "", Visibilidade.PENDENTE_VISUALIZACAO);
	}
	
	@Test(expected = NullPointerException.class)
	public void naoDeveEditarPecaComVisibilidadeNula() {
		TipoPeca tipo = new TipoPeca(new TipoDocumentoId(101L), "Petição inicial");
		Peca peca = pecaValida();
        
		peca.editar(tipo, tipo.nome(), null);
	}
	
	private Peca pecaValida() {
		TipoPeca tipo = new TipoPeca(new TipoDocumentoId(1060L), "Despacho de Relator");
        
		return new Peca(new PecaId(1L), new DocumentoId(1L), tipo, tipo.nome(), Visibilidade.PUBLICO, Situacao.PENDENTE_JUNTADA);
	}
    
}