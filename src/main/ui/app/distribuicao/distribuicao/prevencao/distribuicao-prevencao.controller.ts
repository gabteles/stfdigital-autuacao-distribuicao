import IStateParamsService = angular.ui.IStateParamsService;
import IStateService = angular.ui.IStateService;
import {DistribuicaoPrevencaoService} from "./distribuicao-prevencao.service";
import {DistribuirProcessoPrevencaoCommand, Processo, Ministro} from "../commons/distribuicao-common.model"
import distribuicao from "../distribuicao.module";

export class DistribuicaoPrevencaoController {
    public ministrosSelecionados: { singleSelect: any, multipleSelect: Array<Ministro> };
    public ministrosAdicionados: { singleSelect: any, multipleSelect: Array<Ministro> };
    public ministrosImpedidos: Array<Ministro>;
    public justificativa: string;
    public numProcessoPrevencao: string;
    public distribuicaoId: number;
    
    public cmdDistribuir : DistribuirProcessoPrevencaoCommand = new DistribuirProcessoPrevencaoCommand(); 
    
    static $inject = ['$state', '$stateParams', 'messagesService', "app.distribuicao.DistribuicaoService"];
    
    /** @ngInject **/
    constructor(private $state: IStateService, private $stateParams: IStateParamsService, private messagesService: app.support.messaging.MessagesService,
    		private distribuicaoPrevencaoService: DistribuicaoPrevencaoService) {
        
    	this.cmdDistribuir.distribuicaoId = $stateParams['informationId'];
        
    }
    
    
    public isFormValido(): boolean {
        return (this.cmdDistribuir.justificativa != "");
    }
 /*   
    public adicionarProcessoPrevento(processo: Processo): void {
        if (this.processosPreventos.length === 0){
            this.processosPreventos.push(processo);
        } else {
            let existe: boolean = false;
            for (let i = 0; i > this.processosPreventos.length; i++) {
                if (this.processosPreventos[i].id == processo.id) {
                   existe = true;
                   break; 
                }
            }
                        
            if (!existe) {
                this.processosPreventos.push(processo);
            }
        }
        
        this.numProcessoPrevencao = "";
    }
    
    public removerProcessoPrevento(processo: Processo): void {
        let indice = this.processosPreventos.indexOf(processo);
        this.processosPreventos.splice(indice, 1);        
    }
    
    public consultarProcessoPrevento(): void {
        let processo = this.distribuicaoService.consultarProcessoPrevencao(this.numProcessoPrevencao.toUpperCase());
        
        if (processo.id > 0){
            this.adicionarProcessoPrevento(processo);
        } else {
            this.exibirMensagem("Processo não encontrado.", "Distribuição de Processos");
        }
    }
    
    public distribuirProcesso(): void {
        this.distribuicaoService.enviarProcessoParaDistribuicao(this.processo.id, this.distribuicaoId, this.tipoDistribuicao, 
            this.justificativa, this.ministrosCandidatos, this.ministrosImpedidos, this.processosPreventos).then(() => {
                this.$state.go('app.tarefas.minhas-tarefas', {}, { reload: true });    
            });
    }
   */ 
}

distribuicao.controller('app.distribuicao.DistribuicaoPrevencaoController', DistribuicaoPrevencaoController);

export default distribuicao;