package br.jus.stf.autuacao.distribuicao.infra.configuration;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

import br.jus.stf.core.framework.stream.StreamConfigurerSupport;
import br.jus.stf.core.shared.eventos.AutuacaoFinalizada;
import br.jus.stf.core.shared.eventos.ProcessoDistribuido;

/**
 * Configuração do mecanismo que será usado pelo serviço para 
 * publicação e/ou recebimento de eventos de domínio.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 24.08.2016
 */
@EnableBinding(StreamConfigurer.Channels.class)
public class StreamConfigurer extends StreamConfigurerSupport {

	@Override
	protected String serviceSchema() {
		return "distribuicao";
	}
	
	/**
	 * Declaração dos canais mencionados acima.
	 * 
	 * @author Rodrigo Barreiros
	 * 
	 * @since 1.0.0
	 * @since 24.08.2016
	 */
	public interface Channels {

		/**
		 * O canal que será usado para receber eventos do tipo {@link AutuacaoFinalizada}.
		 * 
		 * @return o canal para as autuações finalizadas
		 */
		@Input(AutuacaoFinalizada.EVENT_KEY)
		SubscribableChannel autuacaoFinalizada();
		
		
		/**
		 * O canal que será usado para publicação de eventos do tipo {@link ProcessoDistribuido}.
		 * 
		 * @return o canal para os processos distribuídos
		 */
		@Output(ProcessoDistribuido.EVENT_KEY)
		MessageChannel processoDistribuido();

	}

}
