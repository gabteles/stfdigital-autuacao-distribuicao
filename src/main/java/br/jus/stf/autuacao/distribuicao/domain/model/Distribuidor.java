package br.jus.stf.autuacao.distribuicao.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import org.apache.commons.lang3.Validate;

import br.jus.stf.core.framework.domaindrivendesign.ValueObjectSupport;
import br.jus.stf.core.shared.identidade.PessoaId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 02.05.2016
 */
@Embeddable
public class Distribuidor extends ValueObjectSupport<Distribuidor> {
	
	@Column(name = "SIG_DISTRIBUIDOR")
	private String login;
	
	@Transient
	private PessoaId pessoa;
	
	public Distribuidor() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	/**
	 * @param login
	 * @param pessoa
	 */
	public Distribuidor(String login, PessoaId pessoa) {
		Validate.notBlank(login, "Login requerido.");
		Validate.notNull(pessoa, "Pessoa requerida.");
		
		this.login = login;
		this.pessoa = pessoa;
	}
	
	/**
	 * @return
	 */
	public String login() {
		return login;
	}
	
	/**
	 * @return
	 */
	public PessoaId pessoa() {
		return pessoa;
	}
	
	@Override
	public String toString() {
		return String.format("%s", login);
	}

}
