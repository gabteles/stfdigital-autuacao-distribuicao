package br.jus.stf.autuacao.distribuicao.domain.model;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import br.jus.stf.core.framework.domaindrivendesign.ValueObjectSupport;
import br.jus.stf.core.shared.identidade.MinistroId;
import br.jus.stf.core.shared.processo.ProcessoId;

public class ParametroDistribuicao extends ValueObjectSupport<ParametroDistribuicao> {

	private ProcessoId processoId;
	private TipoDistribuicao tipoDistribuicao;
	private String justificativa;
	
	private Set<MinistroId> ministrosCandidatos;
	private Set<MinistroId> ministrosImpedidos;
	private Set<Processo> processosPreventos;

	public ParametroDistribuicao(ProcessoId processoId, TipoDistribuicao tipoDistribuicao, String justificativa,
			Set<MinistroId> ministrosCandidatos, Set<MinistroId> ministrosImpedidos, Set<Processo> processosPreventos) {
		this.processoId = processoId;
		this.tipoDistribuicao = tipoDistribuicao;
		this.justificativa = justificativa;

		this.ministrosCandidatos = ministrosCandidatos;
		this.ministrosImpedidos = ministrosImpedidos;

		this.processosPreventos = processosPreventos;
	}
	
	public ProcessoId processoId() {
		return processoId;
	}

	public TipoDistribuicao tipoDistribuicao() {
		return tipoDistribuicao;
	}

	public String justificativa() {
		return justificativa;
	}

	public Set<MinistroId> ministrosCandidatos() {
		return Optional.ofNullable(ministrosCandidatos).isPresent() ? Collections.unmodifiableSet(ministrosCandidatos)
				: null;
	}

	public Set<MinistroId> ministrosImpedidos() {
		return Optional.ofNullable(ministrosImpedidos).isPresent() ? Collections.unmodifiableSet(ministrosImpedidos)
				: null;
	}

	public Set<Processo> processosPreventos() {
		return Optional.ofNullable(processosPreventos).isPresent() ? Collections.unmodifiableSet(processosPreventos)
				: null;
	}

}
