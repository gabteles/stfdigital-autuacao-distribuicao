package br.jus.stf.autuacao.distribuicao.infra.configuration;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

import br.jus.stf.core.shared.eventos.AutuacaoFinalizada;
import br.jus.stf.core.shared.eventos.PeticaoRegistrada;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 24.08.2016
 */
@EnableBinding(StreamConfigurer.Channels.class)
public class StreamConfigurer {
	
	/**
	 * Configuração dos canais que serão usados pelo serviço de peticionamento
	 * para publicação de eventos de domínio.
	 * 
	 * @author Rodrigo Barreiros
	 * 
	 * @since 1.0.0
	 * @since 24.08.2016
	 */
	public interface Channels {

		/**
		 * O canal que será usado para publicação de eventos do tipo {@link PeticaoRegistrada}.
		 * 
		 * @return o canal para as petições registradas
		 */
		@Input(AutuacaoFinalizada.EVENT_KEY)
		SubscribableChannel autuacaoFinalizada();

	}

}
