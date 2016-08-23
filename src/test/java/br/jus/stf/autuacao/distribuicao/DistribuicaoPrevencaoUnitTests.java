package br.jus.stf.autuacao.distribuicao;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import br.jus.stf.autuacao.distribuicao.domain.model.Distribuicao;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoId;
import br.jus.stf.autuacao.distribuicao.domain.model.DistribuicaoPrevencao;
import br.jus.stf.autuacao.distribuicao.domain.model.Distribuidor;
import br.jus.stf.autuacao.distribuicao.domain.model.FilaDistribuicao;
import br.jus.stf.autuacao.distribuicao.domain.model.Processo;
import br.jus.stf.autuacao.distribuicao.domain.model.Status;
import br.jus.stf.autuacao.distribuicao.domain.model.identidade.Ministro;
import br.jus.stf.core.shared.identidade.MinistroId;
import br.jus.stf.core.shared.identidade.PessoaId;
import br.jus.stf.core.shared.processo.ProcessoId;

public class DistribuicaoPrevencaoUnitTests {
	
	private Processo mockPreventoRelator1 = new Processo(new ProcessoId(1L), new Ministro(new MinistroId(1L), "Ministro 1"));
	
	private Processo mockPreventoRelator2 = new Processo(new ProcessoId(2L), new Ministro(new MinistroId(2L), "Ministro 2"));
	
	@Test
	public void realizaDistribuicaoPrevencaoValida() {
		Set<Processo> processosPreventos = new HashSet<>(1);

		processosPreventos.add(mockPreventoRelator1);

		FilaDistribuicao fila = filaDistribuicao();
		Distribuicao distribuicao = new DistribuicaoPrevencao(fila, "Assuntos correlatos.", processosPreventos);
		
		distribuicao.executar(new Distribuidor("distribuidor", new PessoaId(1L)));
		
		assertEquals(mockPreventoRelator1.relator(), distribuicao.relator());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaDistribuirPrevencaoComJustificativaBranca() {
		Set<Processo> processosPreventos = new HashSet<>(1);
		
		processosPreventos.add(mockPreventoRelator1);
		
		FilaDistribuicao fila = filaDistribuicao();
		DistribuicaoPrevencao distribuicao = new DistribuicaoPrevencao(fila, "", processosPreventos);
		
		distribuicao.executar(new Distribuidor("distribuidor", new PessoaId(1L)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaDistribuirParaPreventosComRelatoriaDiversa() {
		Set<Processo> processosPreventos = new HashSet<>(2);
		
		processosPreventos.add(mockPreventoRelator1);
		processosPreventos.add(mockPreventoRelator2);
		
		FilaDistribuicao fila = filaDistribuicao();
		Distribuicao distribuicao = new DistribuicaoPrevencao(fila, "Assuntos correlatos.", processosPreventos);
		
		distribuicao.executar(new Distribuidor("distribuidor", new PessoaId(1L)));
	}
	
	private FilaDistribuicao filaDistribuicao() {
		return new FilaDistribuicao(new DistribuicaoId(1L), new ProcessoId(1L), Status.DISTRIBUICAO);
	}
	
}