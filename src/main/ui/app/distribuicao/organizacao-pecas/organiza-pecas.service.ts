import IPromise = angular.IPromise;
import IHttpService = angular.IHttpService;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import Properties = app.support.constants.Properties;
import cmd = app.support.command;
import pecas from "./organiza-pecas.module";
import {Processo, TipoPeca, Visibilidade, Documento, API_PECAS, 
            OrganizaPecasCommand, ExcluirPecasCommand, UnirPecasCommand, JuntarPecaCommand, DividirPecaCommand,
            EditarPecaCommand, InserirPecaCommand} from "./shared/pecas.model";
            
export class OrganizaPecasService {
    
    private apiPeca : string;

    //Endereço do serviço para salvar um documento temporário.
    private static urlExcluiDocTemporario: string = "/documents/api/documentos/temporarios/delete";

    /** @ngInject **/
    constructor(private $http: IHttpService, private properties : Properties, commandService: cmd.CommandService) { 
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
    
    public listarTipoPecas() : IPromise<TipoPeca[]> {
        return this.$http.get(this.apiPeca + "/tipopeca")
            .then((response: IHttpPromiseCallbackArg<TipoPeca[]>) => { 
            return response.data; 
        });
    }
    
    public listarVisibilidade() : IPromise<Visibilidade[]> {
        return this.$http.get(this.apiPeca + "/visibilidade")
            .then((response: IHttpPromiseCallbackArg<Visibilidade[]>) => { 
            return response.data; 
        });
    }
    
    public consultarDocumento(documentoId : number) : IPromise<Documento> {
        return this.$http.get(this.apiPeca + "/pecas/documentos/" + documentoId)
        .then((response: IHttpPromiseCallbackArg<Documento>) => { 
            return response.data;
        });
    }
    
    public excluirDocumentoTemporarioAssinado(arquivosTemporarios: Array<string>): void {
        let cmd: DeleteTemporarioCommand = new DeleteTemporarioCommand(arquivosTemporarios);
        this.$http.post(this.properties.apiUrl + OrganizaPecasService.urlExcluiDocTemporario, cmd);
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
    
    public finalizaOrganizacaoPecas (finalizarCmd : OrganizaPecasCommand) : IPromise<any> {
        return this.$http.post(this.apiPeca + '/organizar' , finalizarCmd)
    }
}


export class DeleteTemporarioCommand {
    public files: Array<string>;

    constructor(files: Array<string>) {
        this.files = files;
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