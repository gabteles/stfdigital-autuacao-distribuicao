import pecas from "./organiza-pecas.module";
import IPromise = angular.IPromise;
import IHttpService = angular.IHttpService;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import Properties = app.support.constants.Properties;
import {Processo, API_PECAS} from "./shared/pecas.model";


export class OrganizaPecasService {
    
    private apiPeca : string;

    /** @ngInject **/
    constructor(private $http: IHttpService, properties : Properties) { 
        this.apiPeca = properties.apiUrl + API_PECAS;
    }
    
    /*
     * Retorna a lista de tipos de distribuição aplicáveis a um processo.
     * @return lista de tipos de distribuição.
    */
    public consultarProcesso(distribuicaoId : number): IPromise<Array<Processo>> {
        return this.$http.get(this.apiPeca + "/" + distribuicaoId + "/processo")
            .then((response: IHttpPromiseCallbackArg<Array<Processo>>) => { 
                return response.data;
            });
    }
}

pecas.service('app.distribuicao.OrganizaPecasService', OrganizaPecasService);

export default pecas;