package br.jus.stf.autuacao.distribuicao.interfaces;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.autuacao.distribuicao.domain.ProcessoAdapter;
import br.jus.stf.autuacao.distribuicao.interfaces.dto.ProcessoConsultaDto;

/**
 * @author viniciusk
 *
 */
@RequestMapping("/api/processos")
@RestController
public class ProcessoRestResource {

    @Autowired
    private ProcessoAdapter processoAdapter;
	
    /**
     * @param parte
     * @return
     */
    @RequestMapping(value = "/distribuidos", params = "parte", method = RequestMethod.GET)
    public List<ProcessoConsultaDto> consultarPartesProcessoDistribuicao(
    		@NotBlank(message = "Nome da parte inválida!") @RequestParam("parte") String parte) {
    	
    	return processoAdapter.consultarPrevencaoPorParte(parte);
    	
    }
	
}
