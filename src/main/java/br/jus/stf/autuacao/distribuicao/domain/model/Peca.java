package br.jus.stf.autuacao.distribuicao.domain.model;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.autuacao.distribuicao.domain.Situacao;
import br.jus.stf.autuacao.distribuicao.domain.Visibilidade;
import br.jus.stf.autuacao.distribuicao.domain.model.documento.TipoPeca;
import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;
import br.jus.stf.core.shared.documento.DocumentoId;

/**
 * @author Rafael Alencar
 * 
 * @since 03.08.2016
 */
/**
 * @author rafael.alencar
 *
 */
@Entity
@Table(name = "PECA", schema = "DISTRIBUICAO")
public class Peca extends EntitySupport<Peca, Long> {
	
	@Id
	@Column(name = "SEQ_PECA")
	private Long id;
	
	@Embedded
	@Column(nullable = false)
	private DocumentoId documento;
	
	@ManyToOne
	@JoinColumn(name = "SEQ_TIPO_PECA", nullable = false)
	private TipoPeca tipo;
	
	@Column(name = "DSC_PECA", nullable = false)
	private String descricao;
	
	@Column(name = "NUM_ORDEM", nullable = false)
	private Integer numeroOrdem;
	
	@Column(name = "TIP_VISIBILIDADE", nullable = false)
	@Enumerated(EnumType.STRING)
	private Visibilidade visibilidade = Visibilidade.PUBLICO;
	
	@Column(name = "TIP_SITUACAO", nullable = false)
	@Enumerated(EnumType.STRING)
	private Situacao situacao = Situacao.JUNTADA;
	
	Peca() {
		// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	/**
	 * @param id
	 * @param documento
	 * @param tipo
	 * @param descricao
	 * @param visibilidade
	 * @param situacao
	 */
	public Peca(Long id, DocumentoId documento, TipoPeca tipo, String descricao, Visibilidade visibilidade, Situacao situacao) {
		Validate.notNull(id, "Identificador requerido.");
		Validate.notNull(documento, "Documento requerido.");
		Validate.notNull(tipo, "Tipo de peça requerido.");
		Validate.notBlank(descricao, "Descrição requerida.");
		
		this.id = id;
		this.documento = documento;
		this.tipo = tipo;
		this.descricao = descricao;
		this.visibilidade = Optional.ofNullable(visibilidade).orElse(Visibilidade.PUBLICO);
		this.situacao = Optional.ofNullable(situacao).orElse(Situacao.JUNTADA);
	}
	
	/**
	 * @return
	 */
	public DocumentoId documento() {
		return documento;
	}
	
	/**
	 * @return
	 */
	public TipoPeca tipo() {
		return tipo;
	}
	
	/**
	 * @return
	 */
	public String descricao() {
		return descricao;
	}
	
	/**
	 * @return
	 */
	public Visibilidade visibilidade() {
		return visibilidade;
	}
	
	/**
	 * @return
	 */
	public Situacao situacao() {
		return situacao;
	}
	
	/**
	 * @return
	 */
	public Integer numeroOrdem() {
		return numeroOrdem;
	}
	
	/**
	 * @param numeroOrdem
	 */
	public void alterarOrdem(Integer numeroOrdem) {
		Validate.notNull(numeroOrdem, "Número de ordem é requerido.");
		Validate.isTrue(numeroOrdem > 0, "Número de ordem deve ser maior que zero.");
		
		this.numeroOrdem = numeroOrdem;
	}
	
	/**
	 * Altera situação da peça para excluída.
	 */
	public void excluir() {
		Validate.isTrue(situacao != Situacao.EXCLUIDA, "A peça informada já foi excluída.");
		
		situacao = Situacao.EXCLUIDA;
	}
	
	/**
	 * Altera situação da peça para juntada.
	 */
	public void juntar() {
		Validate.isTrue(situacao == Situacao.PENDENTE_JUNTADA, "A peça informada não está pendente de juntada.");
		
		situacao = Situacao.JUNTADA;
	}
	
	/**
	 * @param tipo
	 * @param descricao
	 * @param visibilidade
	 */
	public void editar(TipoPeca tipo, String descricao, Visibilidade visibilidade) {
		Validate.notNull(tipo, "Tipo de peça requerido.");
		Validate.notBlank(descricao, "Descrição requerida.");
		Validate.notNull(visibilidade, "Visibilidade requerida.");
		
		this.tipo = tipo;
		this.descricao = descricao;
		this.visibilidade = visibilidade;
	}
	
	@Override
	public Long identity() {
		return id;
	}

}