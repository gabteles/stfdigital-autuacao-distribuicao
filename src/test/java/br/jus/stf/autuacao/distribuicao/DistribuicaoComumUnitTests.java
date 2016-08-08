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
		Set<MinistroId> ministrosCandidatos = new HashSet<>(8);
		
		ministrosCandidatos.add(new MinistroId(42L));
		ministrosCandidatos.add(new MinistroId(28L));
		ministrosCandidatos.add(new MinistroId(44L));
		ministrosCandidatos.add(new MinistroId(49L));
		ministrosCandidatos.add(new MinistroId(36L));
		ministrosCandidatos.add(new MinistroId(45L));
		ministrosCandidatos.add(new MinistroId(30L));
		ministrosCandidatos.add(new MinistroId(48L));
		
		Set<MinistroId> ministrosImpedidos = new HashSet<>(3);
		
		ministrosImpedidos.add(new MinistroId(46L));
		ministrosImpedidos.add(new MinistroId(47L));
		ministrosImpedidos.add(new MinistroId(1L));
		
		FilaDistribuicao fila = filaDistribuicao();
		Distribuicao distribuicao = distribuicaoComum(fila, ministrosCandidatos, ministrosImpedidos);
		
		distribuicao.executar(new Distribuidor("distribuidor", new PessoaId(1L)));
		
		MinistroId relator = distribuicao.relator();
		
		Assert.assertTrue(ministrosCandidatos.contains(relator));
		Assert.assertFalse(ministrosImpedidos.contains(relator));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaDistribuirComIntersecaoEntreListasMinistros() {
		Set<MinistroId> ministrosCandidatos = new HashSet<>(8);
		
		ministrosCandidatos.add(new MinistroId(42L));
		ministrosCandidatos.add(new MinistroId(28L));
		ministrosCandidatos.add(new MinistroId(44L));
		ministrosCandidatos.add(new MinistroId(49L));
		ministrosCandidatos.add(new MinistroId(36L));
		ministrosCandidatos.add(new MinistroId(45L));
		ministrosCandidatos.add(new MinistroId(30L));
		ministrosCandidatos.add(new MinistroId(48L));
		
		Set<MinistroId> ministrosImpedidos = new HashSet<>(3);
		
		ministrosImpedidos.add(new MinistroId(46L));
		ministrosImpedidos.add(new MinistroId(47L));
		ministrosImpedidos.add(new MinistroId(48L));
		
		FilaDistribuicao fila = filaDistribuicao();
		Distribuicao distribuicao = distribuicaoComum(fila, ministrosCandidatos, ministrosImpedidos);
		
		distribuicao.executar(new Distribuidor("distribuidor", new PessoaId(1L)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaDistribuirSemApresentarTodosMinistros() {
		Set<MinistroId> ministrosCandidatos = new HashSet<>(8);
		
		ministrosCandidatos.add(new MinistroId(42L));
		ministrosCandidatos.add(new MinistroId(28L));
		ministrosCandidatos.add(new MinistroId(44L));
		ministrosCandidatos.add(new MinistroId(49L));
		ministrosCandidatos.add(new MinistroId(36L));
		ministrosCandidatos.add(new MinistroId(45L));
		ministrosCandidatos.add(new MinistroId(30L));
		ministrosCandidatos.add(new MinistroId(48L));
		
		FilaDistribuicao fila = filaDistribuicao();
		Distribuicao distribuicao = distribuicaoComum(fila, ministrosCandidatos, Collections.emptySet());
		
		distribuicao.executar(new Distribuidor("distribuidor", new PessoaId(1L)));
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaDistribuirComMinistrosCandidatosNulo() {
		Set<MinistroId> ministrosImpedidos = new HashSet<MinistroId>();
		
		ministrosImpedidos.add(new MinistroId(46L));
		ministrosImpedidos.add(new MinistroId(47L));
		ministrosImpedidos.add(new MinistroId(48L));
		
		FilaDistribuicao fila = filaDistribuicao();
		Distribuicao distribuicao = distribuicaoComum(fila, null, ministrosImpedidos);
		
		distribuicao.executar(new Distribuidor("distribuidor", new PessoaId(1L)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaDistribuirComMinistrosCandidatosVazio() {
		Set<MinistroId> ministrosCandidatos = new HashSet<>(0);
		
		FilaDistribuicao fila = filaDistribuicao();
		Distribuicao distribuicao = distribuicaoComum(fila, ministrosCandidatos, null);
		
		distribuicao.executar(new Distribuidor("distribuidor", new PessoaId(1L)));
	}
	
	private FilaDistribuicao filaDistribuicao() {
		return new FilaDistribuicao(new DistribuicaoId(1L), new ProcessoId(1L), Status.DISTRIBUICAO);
	}
	
	private DistribuicaoComum distribuicaoComum(FilaDistribuicao filaDistribuicao, Set<MinistroId> ministrosCandidatos,
			Set<MinistroId> ministrosImpedidos) {
		return new DistribuicaoComum(filaDistribuicao, mockMinistroRepository, ministrosCandidatos, ministrosImpedidos);
	}
	
}
