import distribuicao from "../distribuicao.module";
import IPromise = angular.IPromise;
import IHttpService = angular.IHttpService;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import Properties = app.support.constants.Properties;
import {DistribuirCommand, Ministro, Processo, TipoDistribuicao, Parte, API_DISTRIBUICAO, API_MINISTROS} from "../commons/distribuicao-common.model";


export class DistribuicaoCommonService {
    
    private apiMinistro : string;
    private apiDistribuicao : string;

    //Endereço do serviço para salvar um documento temporário.
    //private static urlServicoMinistro: string = "/distribuicao/api/ministros";
    //private static urlServicoPartes: string = "/autuacao/parte/";

    /** @ngInject **/
    constructor(private $http: IHttpService, properties : Properties) { 
        this.apiMinistro = properties.apiUrl + API_MINISTROS;
        this.apiDistribuicao = properties.apiUrl + API_DISTRIBUICAO;
        
    }
    
    /*
     * Retorna a lista de ministros.
     * @return lista de ministros.
    */
    public listarMinistros(): IPromise<Ministro[]> {
        return this.$http.get(this.apiMinistro + "/")
            .then((response: IHttpPromiseCallbackArg<Ministro[]>) => { 
                return response.data;
        });
    }
    
    public consultarProcessoPelaDistribuicao(distrbuicaoId : number) : IPromise<Processo> {
        return this.$http.get(this.apiDistribuicao + "/" + distrbuicaoId + "/processo")
                .then((response: IHttpPromiseCallbackArg<Processo>) => { 
                    return response.data; 
                });
    }

}

distribuicao.service('app.distribuicao.DistribuicaoCommonService', DistribuicaoCommonService);

export default distribuicao;