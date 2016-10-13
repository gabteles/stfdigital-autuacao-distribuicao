import IPromise = angular.IPromise;
import IHttpService = angular.IHttpService;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import Properties = app.support.constants.Properties;
import cmd = app.support.command;
import pecas from "./organiza-pecas.module";
import {API_PECAS, OrganizaPecasCommand, ExcluirPecasCommand, UnirPecasCommand, 
    JuntarPecaCommand, DividirPecaCommand, EditarPecaCommand, InserirPecaCommand} from "./shared/pecas.model";
            
export class OrganizaPecasService {
    
    private apiPeca : string;

    /** @ngInject **/
    constructor(private $http: IHttpService, private properties : Properties, commandService: cmd.CommandService) { 
        this.apiPeca = properties.apiUrl + API_PECAS;
        commandService.addValidator('organizar-pecas', new ValidadorOrganizaPecas());
    }
    
    public excluirPecas(excluirCmd : ExcluirPecasCommand) : IPromise<any> {
        return this.$http.post(this.apiPeca + '/excluir', excluirCmd);
    }
    
    public unirPecas(unirCmd : UnirPecasCommand) : IPromise<any> {
        return this.$http.post(this.apiPeca + '/unir', unirCmd);
    }
    
    public juntarPecas(juntarCmd : JuntarPecaCommand) : IPromise<any> {
        return this.$http.post(this.apiPeca + '/juntar', juntarCmd);
    }
    
    public editarPeca (editarCmd : EditarPecaCommand) : IPromise<any> {
        return this.$http.post(this.apiPeca + '/editar', editarCmd);
    }
    
    public dividirPeca (dividirCmd : DividirPecaCommand) : IPromise<any> {
        return this.$http.post(this.apiPeca + '/dividir', dividirCmd);
    }
    
    public inserirPecas (inserirCmd : InserirPecaCommand) : IPromise<any> {
        return this.$http.post(this.apiPeca + '/inserir', inserirCmd);
    }
    
    public finalizarOrganizacaoPecas (finalizarCmd : OrganizaPecasCommand) : IPromise<any> {
        return this.$http.post(this.apiPeca + '/organizar' , finalizarCmd)
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