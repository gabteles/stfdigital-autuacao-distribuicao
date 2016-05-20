package br.jus.stf.autuacao.distribuicao.domain.model;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import br.jus.stf.core.framework.domaindrivendesign.ValueObjectSupport;
import br.jus.stf.core.shared.identidade.MinistroId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 19.05.2016
 */
public class ParametroDistribuicao extends ValueObjectSupport<ParametroDistribuicao> {

	private FilaDistribuicao fila;
	private TipoDistribuicao tipoDistribuicao;
	private String justificativa;
	
	private Set<MinistroId> ministrosCandidatos;
	private Set<MinistroId> ministrosImpedidos;
	private Set<Processo> processosPreventos;

	public ParametroDistribuicao(FilaDistribuicao fila, TipoDistribuicao tipoDistribuicao, String justificativa,
			Set<MinistroId> ministrosCandidatos, Set<MinistroId> ministrosImpedidos, Set<Processo> processosPreventos) {
		this.fila = fila;
		this.tipoDistribuicao = tipoDistribuicao;
		this.justificativa = justificativa;

		this.ministrosCandidatos = ministrosCandidatos;
		this.ministrosImpedidos = ministrosImpedidos;

		this.processosPreventos = processosPreventos;
	}
	
	public FilaDistribuicao fila() {
		return fila;
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
