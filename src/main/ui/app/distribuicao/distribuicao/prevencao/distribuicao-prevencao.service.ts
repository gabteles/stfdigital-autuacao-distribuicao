import distribuicao from "../distribuicao.module";
import IPromise = angular.IPromise;
import IHttpService = angular.IHttpService;
import IHttpPromiseCallbackArg = angular.IHttpPromiseCallbackArg;
import Properties = app.support.constants.Properties;
import cmd = app.support.command;
import {ProcessoIndexado, API_DISTRIBUICAO, DistribuirProcessoPrevencaoCommand} from "../commons/distribuicao-common.model";


export class DistribuicaoPrevencaoService {
    
    private apiDistribuicao : string;

    /** @ngInject **/
    constructor(private $http: IHttpService, private properties : Properties, commandService: cmd.CommandService) { 
        this.apiDistribuicao = properties.apiUrl + API_DISTRIBUICAO + "/prevencao";
        commandService.addValidator("distribuir-processo-prevencao", new ValidadorDistribuicaoPrevencao());
    }
    
    public pesquisarProcessoPelaParte(nomeParte : String) : IPromise<ProcessoIndexado[]>{
        let data = {parte: nomeParte};
        return this.$http.post(this.properties.apiUrl + '/services/api/processos/pesquisa-avancada', data)
            .then((response: IHttpPromiseCallbackArg<ProcessoIndexado[]>) => { 
                return response.data;
            });
    }
    
    /*
     * Realiza a distribuição de um processo para um ministro.
     */
    public distribuirPorPrevencao( cmdDistribuir : DistribuirProcessoPrevencaoCommand): IPromise<any> {
         return this.$http.post(this.apiDistribuicao, cmdDistribuir);
    }     
     
}

class ValidadorDistribuicaoPrevencao implements cmd.CommandValidator {
    
    public isValid(command: DistribuirProcessoPrevencaoCommand): boolean {
        return command.justificativa != "";
    }
    
}   

distribuicao.service('app.distribuicao.DistribuicaoPrevencaoService', DistribuicaoPrevencaoService);

export default distribuicao;