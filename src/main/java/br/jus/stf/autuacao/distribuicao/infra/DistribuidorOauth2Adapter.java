package br.jus.stf.autuacao.distribuicao.infra;

import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.distribuicao.domain.DistribuidorAdapter;
import br.jus.stf.autuacao.distribuicao.domain.model.Distribuidor;
import br.jus.stf.core.shared.identidade.PessoaId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 07.07.2016
 */
@Component
public class DistribuidorOauth2Adapter implements DistribuidorAdapter {

	@Override
	@SuppressWarnings("unchecked")
	public Distribuidor distribuidor() {
		OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
		
		Map<String, Object> principal = (Map<String, Object>) authentication.getUserAuthentication().getDetails();
		
		Long pessoaId = Long.valueOf(principal.get("pessoaId").toString());
		
		String login = principal.get("login").toString();
		
		return new Distribuidor(login, new PessoaId(pessoaId));
	}

}
