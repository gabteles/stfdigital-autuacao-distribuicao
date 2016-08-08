package br.jus.stf.autuacao.distribuicao.domain;

import java.util.List;

import org.apache.commons.lang3.Range;
import org.springframework.stereotype.Component;

import br.jus.stf.core.shared.documento.DocumentoId;
import br.jus.stf.core.shared.documento.DocumentoTemporarioId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 04.08.2016
 */
@Component
public interface PecaAdapter {

	/**
	 * Salva os documentos e recupera os ids na ordem de envio
	 * 
	 * @param documentosTemporarios Lista de documentos temporários.
	 * @return a lista de ids dos documentos.
	 */
	List<DocumentoId> salvar(List<DocumentoTemporarioId> documentosTemporarios);
	
	/**
	 * @param documento
	 * @param intervalosDivisao
	 * @return
	 */
	List<DocumentoId> dividir(DocumentoId documento, List<Range<Integer>> intervalosDivisao);
	
	/**
	 * @param documentos
	 * @return
	 */
	DocumentoId unir(List<DocumentoId> documentos);
}
