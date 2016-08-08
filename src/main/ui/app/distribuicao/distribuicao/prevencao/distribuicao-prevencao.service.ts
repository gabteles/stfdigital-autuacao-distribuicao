import distribuicao from "../distribuicao.module";
import IPromise = angular.IPromise;
import IHttpService = angular.IHttpService;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import Properties = app.support.constants.Properties;


export class DistribuicaoPrevencaoService {
    

    //Endereço do serviço para salvar um documento temporário.
    //private static urlServicoMinistro: string = "/distribuicao/api/ministros";
    //private static urlServicoPartes: string = "/autuacao/parte/";

    /** @ngInject **/
    constructor(private $http: IHttpService, properties : Properties) { 
        
    }
    

    
    /*
     * Consulta os dados de um processo para distribuição.
     * @param numero: nº do processo (id).
     * @return Dados do processo.
     */
/*    public consultarProcessoParaDistribuicao(numero: number): Processo {        
        let partes = new Array<string>();
        partes.push("FULANO KAMEHAMEHA");
        partes.push("ISAMU MINAMI");
        
        return new Processo(1, "ADI 999/2016", partes);
    }
 */   
    /*
     * Realiza a distribuição de um processo para um ministro.
     * @param processoId Id do processo.
     * @param distribuicaoId: Id da distribuição gerado no ato de autuação do processo.
     * @param tipo Tipo de distribuição.
     * @param justificativa Justificativa da distribuição.
     * @param minCandidatos Lista de ministros que podem ser o relator do processo.
     * @param minImpedidos Lista de ministros que não podem ser o relator do processo.
     * @param procPreventos Lista de processos usados para a prevenção.
     */
/*    public enviarProcessoParaDistribuicao(processoId: number, distribuicaoId: number, tipo: string, justificativa: string, minCandidatos: Array<Ministro>,
        minImpedidos: Array<Ministro>, procPreventos: Array<Processo>): IPromise<any> {
        let ministrosCandidatos = [];
        let ministrosImpedidos = [];
        let processosPreventos = [];
        
        for (let i = 0; i < minCandidatos.length; i++){
            ministrosCandidatos.push(minCandidatos[i].id);
        }
                
        for (let j = 0; j < minImpedidos.length; j++){
            ministrosImpedidos.push(minImpedidos[j].id);    
        }
        
        for (let k = 0; k < procPreventos.length; k++) {
            processosPreventos.push(procPreventos[k].id);
        }
                
        let distribuirProcessoCommand = new DistribuirProcessoCommand(distribuicaoId, processoId, tipo, justificativa, ministrosCandidatos, 
            ministrosImpedidos, processosPreventos);
        
        return this.$http.post(this.properties.url + ":" + this.properties.port + DistribuicaoService.urlServicoDistribuicao, distribuirProcessoCommand);
    }
*/    
}

distribuicao.service('app.distribuicao.DistribuicaoPrevencaoService', DistribuicaoPrevencaoService);

export default distribuicao;