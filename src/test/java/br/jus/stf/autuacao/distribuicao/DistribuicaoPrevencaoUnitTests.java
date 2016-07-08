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
import br.jus.stf.autuacao.distribuicao.domain.model.ParametroDistribuicao;
import br.jus.stf.autuacao.distribuicao.domain.model.ProcessoDistribuido;
import br.jus.stf.autuacao.distribuicao.domain.model.Status;
import br.jus.stf.autuacao.distribuicao.domain.model.TipoDistribuicao;
import br.jus.stf.core.shared.identidade.MinistroId;
import br.jus.stf.core.shared.identidade.PessoaId;
import br.jus.stf.core.shared.processo.ProcessoId;

public class DistribuicaoPrevencaoUnitTests {
	
	private ProcessoDistribuido mockPreventoRelator1 = new ProcessoDistribuido(new ProcessoId(1L), new MinistroId(1L));
	
	private ProcessoDistribuido mockPreventoRelator2 = new ProcessoDistribuido(new ProcessoId(2L), new MinistroId(2L));
	
	@Test
	public void realizaDistribuicaoPrevencaoValida() {
		Set<ProcessoDistribuido> processosPreventos = new HashSet<>(1);

		processosPreventos.add(mockPreventoRelator1);

		FilaDistribuicao fila = filaDistribuicao();
		ParametroDistribuicao parametros = parametroDistribuicao(fila, processosPreventos, "Assuntos correlatos.");
		Distribuicao distribuicao = new DistribuicaoPrevencao(fila.identity(), fila.processo(), fila.status(),
				parametros.processosPreventos());
		
		distribuicao.executar(parametros, new Distribuidor("distribuidor", new PessoaId(1L)), Status.DISTRIBUIDO);
		
		assertEquals(mockPreventoRelator1.relator(), distribuicao.relator());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaDistribuirPrevencaoComJustificativaBranca() {
		Set<ProcessoDistribuido> processosPreventos = new HashSet<>(1);
		
		processosPreventos.add(mockPreventoRelator1);
		
		FilaDistribuicao fila = filaDistribuicao();
		ParametroDistribuicao parametros = parametroDistribuicao(fila, processosPreventos, "");
		DistribuicaoPrevencao distribuicao = new DistribuicaoPrevencao(fila.identity(), fila.processo(), fila.status(), parametros.processosPreventos());
		
		distribuicao.executar(parametros, new Distribuidor("distribuidor", new PessoaId(1L)), Status.DISTRIBUIDO);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaDistribuirParaPreventosComRelatoriaDiversa() {
		Set<ProcessoDistribuido> processosPreventos = new HashSet<>(2);
		
		processosPreventos.add(mockPreventoRelator1);
		processosPreventos.add(mockPreventoRelator2);
		
		FilaDistribuicao fila = filaDistribuicao();
		ParametroDistribuicao parametros = parametroDistribuicao(fila, processosPreventos, "Assuntos correlatos.");
		Distribuicao distribuicao = new DistribuicaoPrevencao(fila.identity(), fila.processo(), fila.status(),
				parametros.processosPreventos());
		
		distribuicao.executar(parametros, new Distribuidor("distribuidor", new PessoaId(1L)), Status.DISTRIBUIDO);
	}
	
	private FilaDistribuicao filaDistribuicao() {
		return new FilaDistribuicao(new DistribuicaoId(1L), new ProcessoId(1L), Status.DISTRIBUICAO);
	}
	
	private ParametroDistribuicao parametroDistribuicao(FilaDistribuicao filaDistribuicao, Set<ProcessoDistribuido> processosPreventos, String justificativa) {
		return new ParametroDistribuicao(filaDistribuicao, TipoDistribuicao.PREVENCAO,
				justificativa, null, null, processosPreventos);
	}
	
}