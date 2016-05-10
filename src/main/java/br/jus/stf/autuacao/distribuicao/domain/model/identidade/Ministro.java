package br.jus.stf.autuacao.distribuicao.domain.model.identidade;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.elasticsearch.common.lang3.Validate;

import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;
import br.jus.stf.core.shared.identidade.MinistroId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 02.05.2016
 */
@Entity
@Table(name = "MINISTRO", schema = "DISTRIBUICAO")
public class Ministro extends EntitySupport<Ministro, MinistroId> {
	
	@EmbeddedId
	private MinistroId id;
	
	@Column(name = "NOM_MINISTRO")
	private String nome;
	
	public Ministro() {
		// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	public Ministro(MinistroId id, String nome) {
		Validate.notNull(id, "Id requerido.");
		Validate.notBlank(nome, "Nome requerida.");
		
		this.id = id;
		this.nome = nome;
	}
	
	public String nome() {
		return nome;
	}
	
	@Override
	public String toString() {
		return String.format("%s - %s", id.toString(), nome);
	}
	
	@Override
	public MinistroId identity() {
		return id;
	}

}
