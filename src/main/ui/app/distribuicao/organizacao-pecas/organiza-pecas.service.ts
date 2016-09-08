import IPromise = angular.IPromise;
import IHttpService = angular.IHttpService;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import Properties = app.support.constants.Properties;
import cmd = app.support.command;
import pecas from "./organiza-pecas.module";
import {Processo, API_PECAS, OrganizaPecasCommand, ExcluirPecasCommand, JuntarPecaCommand} from "./shared/pecas.model";

export class OrganizaPecasService {
    
    private apiPeca : string;

    /** @ngInject **/
    constructor(private $http: IHttpService, properties : Properties, commandService: cmd.CommandService) { 
        this.apiPeca = properties.apiUrl + API_PECAS;
        commandService.addValidator('organizar-pecas', new ValidadorOrganizaPecas());
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
    
    public excluirPecas(excluirCmd : ExcluirPecasCommand) : IPromise<any> {
        return this.$http.post(this.apiPeca + '/excluir', excluirCmd);
    }
    
    public juntarPecas(juntarCmd : JuntarPecaCommand) : IPromise<any> {
        return this.$http.post(this.apiPeca + '/juntar', juntarCmd);
    }
}

class ValidadorOrganizaPecas implements cmd.CommandValidator {
    
    constructor() {}
    
    public isValid(command: OrganizaPecasCommand): boolean {
        return true;
    }
}

pecas.service('app.distribuicao.organizacao-pecas.OrganizaPecasService', OrganizaPecasService);

export default pecas;