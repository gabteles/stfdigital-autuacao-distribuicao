package br.jus.stf.autuacao.distribuicao;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.jus.stf.autuacao.distribuicao.domain.model.Distribuicao;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoComum;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoId;
import br.jus.stf.autuacao.distribuicao.domain.model.Distribuidor;
import br.jus.stf.autuacao.distribuicao.domain.model.FilaDistribuicao;
import br.jus.stf.autuacao.distribuicao.domain.model.Status;
import br.jus.stf.autuacao.distribuicao.domain.model.identidade.Ministro;
import br.jus.stf.autuacao.distribuicao.domain.model.identidade.MinistroRepository;
import br.jus.stf.core.shared.identidade.MinistroId;
import br.jus.stf.core.shared.identidade.PessoaId;
import br.jus.stf.core.shared.processo.ProcessoId;

public class DistribuicaoComumUnitTests {
	
	@Mock
	private MinistroRepository mockMinistroRepository;
	
	@Before
	public void configuracao() {
		MockitoAnnotations.initMocks(this);
		
		Mockito.when(mockMinistroRepository.count()).thenReturn(11L);
	}
	
	@Test
	public void realizaDistribuicaoComumValida() {
		Set<Ministro> ministrosCandidatos = new HashSet<>(8);
		
		ministrosCandidatos.add(new Ministro(new MinistroId(42L), "MIN. CÁRMEN LÚCIA"));
		ministrosCandidatos.add(new Ministro(new MinistroId(28L), "MIN. CELSO DE MELLO"));
		ministrosCandidatos.add(new Ministro(new MinistroId(44L), "MIN. DIAS TOFFOLI"));
		ministrosCandidatos.add(new Ministro(new MinistroId(49L), "MIN. EDSON FACHIN"));
		ministrosCandidatos.add(new Ministro(new MinistroId(36L), "MIN. GILMAR MENDES"));
		ministrosCandidatos.add(new Ministro(new MinistroId(45L), "MIN. LUIZ FUX"));
		ministrosCandidatos.add(new Ministro(new MinistroId(30L), "MIN. MARCO AURÉLIO"));
		ministrosCandidatos.add(new Ministro(new MinistroId(48L), "MIN. ROBERTO BARROSO"));
		
		Set<Ministro> ministrosImpedidos = new HashSet<>(3);
		
		ministrosImpedidos.add(new Ministro(new MinistroId(46L), "MIN. ROSA WEBER"));
		ministrosImpedidos.add(new Ministro(new MinistroId(47L), "MIN. TEORI ZAVASCKI"));
		ministrosImpedidos.add(new Ministro(new MinistroId(1L), "MINISTRO PRESIDENTE"));
		
		FilaDistribuicao fila = filaDistribuicao();
		Distribuicao distribuicao = distribuicaoComum(fila, ministrosCandidatos, ministrosImpedidos);
		
		distribuicao.executar(new Distribuidor("distribuidor", new PessoaId(1L)));
		
		Ministro relator = distribuicao.relator();
		
		Assert.assertTrue(ministrosCandidatos.contains(relator));
		Assert.assertFalse(ministrosImpedidos.contains(relator));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaDistribuirComIntersecaoEntreListasMinistros() {
		Set<Ministro> ministrosCandidatos = new HashSet<>(8);
		
		ministrosCandidatos.add(new Ministro(new MinistroId(42L), "MIN. CÁRMEN LÚCIA"));
		ministrosCandidatos.add(new Ministro(new MinistroId(28L), "MIN. CELSO DE MELLO"));
		ministrosCandidatos.add(new Ministro(new MinistroId(44L), "MIN. DIAS TOFFOLI"));
		ministrosCandidatos.add(new Ministro(new MinistroId(49L), "MIN. EDSON FACHIN"));
		ministrosCandidatos.add(new Ministro(new MinistroId(36L), "MIN. GILMAR MENDES"));
		ministrosCandidatos.add(new Ministro(new MinistroId(45L), "MIN. LUIZ FUX"));
		ministrosCandidatos.add(new Ministro(new MinistroId(30L), "MIN. MARCO AURÉLIO"));
		ministrosCandidatos.add(new Ministro(new MinistroId(48L), "MIN. ROBERTO BARROSO"));
		
		Set<Ministro> ministrosImpedidos = new HashSet<>(3);
		
		ministrosImpedidos.add(new Ministro(new MinistroId(46L), "MIN. ROSA WEBER"));
		ministrosImpedidos.add(new Ministro(new MinistroId(47L), "MIN. TEORI ZAVASCKI"));
		ministrosImpedidos.add(new Ministro(new MinistroId(48L), "MIN. ROBERTO BARROSO"));
		
		FilaDistribuicao fila = filaDistribuicao();
		Distribuicao distribuicao = distribuicaoComum(fila, ministrosCandidatos, ministrosImpedidos);
		
		distribuicao.executar(new Distribuidor("distribuidor", new PessoaId(1L)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaDistribuirSemApresentarTodosMinistros() {
		Set<Ministro> ministrosCandidatos = new HashSet<>(8);
		
		ministrosCandidatos.add(new Ministro(new MinistroId(42L), "MIN. CÁRMEN LÚCIA"));
		ministrosCandidatos.add(new Ministro(new MinistroId(28L), "MIN. CELSO DE MELLO"));
		ministrosCandidatos.add(new Ministro(new MinistroId(44L), "MIN. DIAS TOFFOLI"));
		ministrosCandidatos.add(new Ministro(new MinistroId(49L), "MIN. EDSON FACHIN"));
		ministrosCandidatos.add(new Ministro(new MinistroId(36L), "MIN. GILMAR MENDES"));
		ministrosCandidatos.add(new Ministro(new MinistroId(45L), "MIN. LUIZ FUX"));
		ministrosCandidatos.add(new Ministro(new MinistroId(30L), "MIN. MARCO AURÉLIO"));
		ministrosCandidatos.add(new Ministro(new MinistroId(48L), "MIN. ROBERTO BARROSO"));
		
		FilaDistribuicao fila = filaDistribuicao();
		Distribuicao distribuicao = distribuicaoComum(fila, ministrosCandidatos, Collections.emptySet());
		
		distribuicao.executar(new Distribuidor("distribuidor", new PessoaId(1L)));
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaDistribuirComMinistrosCandidatosNulo() {
		Set<Ministro> ministrosImpedidos = new HashSet<>(0);
		
		ministrosImpedidos.add(new Ministro(new MinistroId(46L), "MIN. ROSA WEBER"));
		ministrosImpedidos.add(new Ministro(new MinistroId(47L), "MIN. TEORI ZAVASCKI"));
		ministrosImpedidos.add(new Ministro(new MinistroId(48L), "MIN. ROBERTO BARROSO"));
		
		FilaDistribuicao fila = filaDistribuicao();
		Distribuicao distribuicao = distribuicaoComum(fila, null, ministrosImpedidos);
		
		distribuicao.executar(new Distribuidor("distribuidor", new PessoaId(1L)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaDistribuirComMinistrosCandidatosVazio() {
		FilaDistribuicao fila = filaDistribuicao();
		Distribuicao distribuicao = distribuicaoComum(fila, Collections.emptySet(), null);
		
		distribuicao.executar(new Distribuidor("distribuidor", new PessoaId(1L)));
	}
	
	private FilaDistribuicao filaDistribuicao() {
		return new FilaDistribuicao(new DistribuicaoId(1L), new ProcessoId(1L), Status.DISTRIBUICAO);
	}
	
	private DistribuicaoComum distribuicaoComum(FilaDistribuicao filaDistribuicao, Set<Ministro> ministrosCandidatos,
			Set<Ministro> ministrosImpedidos) {
		return new DistribuicaoComum(filaDistribuicao, mockMinistroRepository, ministrosCandidatos, ministrosImpedidos);
	}
	
}
