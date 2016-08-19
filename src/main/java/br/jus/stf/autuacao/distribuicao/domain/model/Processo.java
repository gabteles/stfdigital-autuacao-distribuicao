package br.jus.stf.autuacao.distribuicao.domain.model;

import static javax.persistence.CascadeType.ALL;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.Validate;

import br.jus.stf.autuacao.distribuicao.domain.Visibilidade;
import br.jus.stf.autuacao.distribuicao.domain.model.documento.TipoPeca;
import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;
import br.jus.stf.core.shared.identidade.MinistroId;
import br.jus.stf.core.shared.processo.ProcessoId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 03.05.2016
 */
@Entity
@Table(name = "PROCESSO", schema = "DISTRIBUICAO")
public class Processo extends EntitySupport<Processo, ProcessoId> {

	@EmbeddedId
	private ProcessoId processoId;
	
	@Embedded
    @Column(nullable = false)
	private MinistroId relator;
	
	@OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "SEQ_PROCESSO", referencedColumnName = "SEQ_PROCESSO", nullable = false)
	@OrderBy("numeroOrdem ASC")
	private List<Peca> pecas = new LinkedList<>();
	
	@Transient
	private ControladorOrdenacaoPecas controladorOrdenacaoPecas;
	
	Processo() {
		// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	/**
	 * @param processoId
	 * @param relator
	 */
	public Processo(ProcessoId processoId, MinistroId relator) {
		Validate.notNull(processoId, "Id requerido");
		Validate.notNull(relator, "Relator requerido");
		
		this.processoId = processoId;	
		this.relator = relator;
		this.controladorOrdenacaoPecas = new ControladorOrdenacaoPecas(pecas);
	}
	
	@PostLoad
	@PostPersist
	@PostUpdate
	private void init() {
		controladorOrdenacaoPecas = new ControladorOrdenacaoPecas(pecas);
	}
	
	/**
	 * @return
	 */
	public MinistroId relator() {
		return relator;
	}
	
	/**
	 * @param peca
	 * @return
	 */
	public boolean adicionarPeca(Peca peca) {
		Validate.notNull(peca, "Peça requerida.");
	
		controladorOrdenacaoPecas.numerarPeca(peca);
		
		return pecas.add(peca);
	}
	
	/**
	 * Altera a situação da peça no processo para JUNTADA.
	 * 
	 * @param peca
	 */
	public void juntarPeca(Peca peca) {
		Validate.notNull(peca, "Peça requerida.");
		Validate.isTrue(pecas.contains(peca), "Peça não pertence ao processo.");
		
		peca.juntar();
	}

	/**
	 * Realiza a substituição de uma peça por outra,
	 * o número de ordem da peça substituta será o mesmo
	 * da peça original.
	 * 
	 * @param pecaOriginal
	 * @param pecaSubstituta
	 */
	public void substituirPeca(Peca pecaOriginal, Peca pecaSubstituta) {
		Validate.notNull(pecaOriginal, "Peça original requerida.");
		Validate.isTrue(pecas.contains(pecaOriginal), "Peça original não pertence ao processo.");
		Validate.notNull(pecaSubstituta, "Peça substituta requerida.");
		
		controladorOrdenacaoPecas.substituirPeca(pecaOriginal, pecaSubstituta);
	}
	
	/**
	 * Edita as informações de uma peça do processo.
	 * 
	 * @param pecaOriginal
	 * @param tipoPeca
	 * @param descricao
	 * @param numeroOrdem
	 * @param visibilidade
	 */
	public void editarPeca(Peca pecaOriginal, TipoPeca tipoPeca, String descricao, Integer numeroOrdem, Visibilidade visibilidade) {
		Validate.notNull(pecaOriginal, "Peça original requerida.");
		Validate.isTrue(pecas.contains(pecaOriginal), "Peça original não pertence ao processo.");
		Validate.notNull(tipoPeca, "Tipo de peça requerida.");
		Validate.notBlank(descricao, "Descrição requerida.");
		Validate.notNull(numeroOrdem, "Número de ordem requerido.");
		Validate.notNull(visibilidade, "Visibilidade requerida.");
		Validate.inclusiveBetween(controladorOrdenacaoPecas.primeiroNumeroOrdemPeca(),
				controladorOrdenacaoPecas.ultimoNumeroOrdemPeca(), numeroOrdem, "Número de ordem inválido.");
		
		if (!pecaOriginal.numeroOrdem().equals(numeroOrdem)) {
			controladorOrdenacaoPecas.reordenarPeca(pecaOriginal, numeroOrdem);
		}
		
		pecaOriginal.editar(tipoPeca, descricao, visibilidade);
	}
	
	/**
	 * Divide uma peça, substituindo-a pelas peças especificadas.
	 * 
	 * @param pecaDividida
	 * @param pecasDivisao
	 */
	public void dividirPeca(Peca pecaDividida, List<Peca> pecasDivisao) {
		Validate.notNull(pecaDividida, "Peça dividida requerida.");
		Validate.isTrue(pecas.contains(pecaDividida), "Peça dividida não pertence ao processo.");
		Validate.notEmpty(pecasDivisao, "Lista de peças resultante da divisão requerida.");
		Validate.isTrue(pecasDivisao.size() >= 2, "A divisão deve originar ao menos duas peças.");

		Integer numeroOrdem = pecaDividida.numeroOrdem();

		pecas.remove(pecaDividida);
		pecasDivisao.forEach(this::adicionarPeca);
		controladorOrdenacaoPecas.reordenarPecas(pecasDivisao, numeroOrdem);
	}
	
	/**
	 * Realiza a união da lista de peças, sendo substutuídas pela
	 * peça especificada.
	 * 
	 * @param pecasUniao
	 * @param pecaUnida
	 */
	public void unirPecas(List<Peca> pecasUniao, Peca pecaUnida) {
		Validate.notEmpty(pecasUniao, "Lista de peças unidas requerida.");
		Validate.isTrue(pecasUniao.size() >= 2, "A lista de peças unidas deve ter ao menos duas peças.");
		Validate.isTrue(pecas.containsAll(pecasUniao), "Algumas das peças unidas não pertencem ao processo.");
		Validate.notNull(pecaUnida, "Peça resultado da união requerida.");
		
		Integer menorNumeroOrdem = pecasUniao.stream().min((p1, p2) -> p1.numeroOrdem().compareTo(p2.numeroOrdem())).get().numeroOrdem();
		
		pecasUniao.forEach(pecas::remove);
		adicionarPeca(pecaUnida);
		controladorOrdenacaoPecas.reordenarPeca(pecaUnida, menorNumeroOrdem);
	}
	
	/**
	 * Marca uma peça como removida.
	 * 
	 * @param peca
	 */
	public void removerPeca(Peca peca) {
		Validate.notNull(peca, "Peça requerida.");
		Validate.isTrue(pecas.contains(peca), "Peça não pertence ao processo.");
		
		peca.excluir();
	}
	
	/**
	 * @param pecasOrganizadas
	 */
	public void organizarPecas(List<Long> pecasOrganizadas) {
		Validate.notEmpty(pecasOrganizadas, "Lista de peças requerida.");
		Validate.isTrue(pecas.size() == pecasOrganizadas.size(),
				"Organizar as peças não pode modificar o tamanho da lista.");
		Validate.isTrue(pecas.stream().allMatch(p -> pecasOrganizadas.contains(p.identity())),
				"Algumas das peças organizadas não pertencem ao processo.");
		
		pecas.forEach(p -> p.alterarOrdem(pecasOrganizadas.indexOf(p.identity()) + 1));
		controladorOrdenacaoPecas.ordenarPecas();
	}

	/**
	 * @return
	 */
	public List<Peca> pecas() {
		return Collections.unmodifiableList(pecas);
	}
	
	@Override
	public ProcessoId identity() {
		return processoId;
	}

}
