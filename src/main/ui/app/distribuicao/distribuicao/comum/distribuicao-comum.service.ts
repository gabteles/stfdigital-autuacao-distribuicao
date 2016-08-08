import distribuicao from "../distribuicao.module";
import IPromise = angular.IPromise;
import IHttpService = angular.IHttpService;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import Properties = app.support.constants.Properties;
import {DistribuirProcessoComumCommand, API_DISTRIBUICAO} from "../commons/distribuicao-common.model";


export class DistribuicaoComumService {
    
	private apiDistribuicao : string;

    /** @ngInject **/
    constructor(private $http: IHttpService, properties : Properties) { 
    	this.apiDistribuicao = properties.apiUrl + API_DISTRIBUICAO;
    }
    
    /*
     * Realiza a distribuição de um processo para um ministro.
     */
    public enviarProcessoParaDistribuicao( cmdDistribuir : DistribuirProcessoComumCommand): IPromise<any> {
         return this.$http.post(this.apiDistribuicao, cmdDistribuir);
    }      
}

distribuicao.service('app.distribuicao.DistribuicaoComumService', DistribuicaoComumService);

export default distribuicao;