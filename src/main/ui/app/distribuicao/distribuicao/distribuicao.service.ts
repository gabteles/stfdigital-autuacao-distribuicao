import distribuicao from "./distribuicao.module";
import IPromise = angular.IPromise;
import IHttpService = angular.IHttpService;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import Properties = app.support.constants.Properties;
import {Processo, TipoDistribuicao, API_DISTRIBUICAO} from "./commons/distribuicao-common.model";


export class DistribuicaoService {
    
    private apiDistribuicao : string;

    /** @ngInject **/
    constructor(private $http: IHttpService, properties : Properties) { 
    	this.apiDistribuicao = properties.apiUrl + API_DISTRIBUICAO;
        
    }
    
    /*
     * Retorna a lista de tipos de distribuição aplicáveis a um processo.
     * @return lista de tipos de distribuição.
    */
    public listarTiposDistribuicao(): IPromise<Array<TipoDistribuicao>> {
    	return this.$http.get(this.apiDistribuicao + '/tipo-distribuicao')
            .then((response: IHttpPromiseCallbackArg<Array<TipoDistribuicao>>) => { 
                return response.data;
            });
    }
}

distribuicao.service('app.distribuicao.DistribuicaoService', DistribuicaoService);

export default distribuicao;