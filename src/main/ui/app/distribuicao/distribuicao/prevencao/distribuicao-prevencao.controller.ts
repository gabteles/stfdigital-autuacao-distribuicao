import IStateParamsService = angular.ui.IStateParamsService;
import IStateService = angular.ui.IStateService;
import {DistribuicaoPrevencaoService} from "./distribuicao-prevencao.service";
import {DistribuirProcessoPrevencaoCommand, Processo, ProcessoDistribuido, DistribuicaoIndexada} from "../commons/distribuicao-common.model"
import distribuicao from "../distribuicao.module";

export class PartePrevencao {
    
    public processos: Array<ProcessoDistribuido> = [];
    
    constructor(public nome: string) { }
}

export class DistribuicaoPrevencaoController {
    public justificativa: string;
    public numProcessoPrevencao: string;
    public distribuicaoId: number;
    public partes : Array<PartePrevencao> = [];
    public processosPreventosAdicionados : Array<ProcessoDistribuido> = [];
    
    public cmdDistribuir : DistribuirProcessoPrevencaoCommand = new DistribuirProcessoPrevencaoCommand(); 
    
    static $inject = ['$state', '$stateParams', 'messagesService', 'app.distribuicao.DistribuicaoPrevencaoService', 'processo'];
    
    /** @ngInject **/
    constructor(private $state: IStateService, private $stateParams: IStateParamsService, private messagesService: app.support.messaging.MessagesService,
    		public distribuicaoPrevencaoService: DistribuicaoPrevencaoService, public processo : Processo) {
        
    	this.cmdDistribuir.distribuicaoId = $stateParams['informationId'];
    	processo.partes.forEach(parte => this.partes.push(new PartePrevencao(parte.apresentacao)));
    }
       
    public isFormValido(): boolean {
        return (this.cmdDistribuir.justificativa != "");
    }
    
    public consultarProcesso (parte: String, indice: number) : void {
   //     this.partes[indice].processos = [<ProcessoIndexado>{processoId: '5521', classe: "HC", numero: 123, distribuicoes : <Array<DistribuicaoIndexada>>[{relatorId: 44, relator: 'Marco Aurélio', data: new Date()}]}]; //mock para exibir um processo prevento
        this.distribuicaoPrevencaoService.pesquisarProcessoPelaParte(parte).then(processos => {
            this.partes[indice].processos = processos;
        }, () => {
            this.messagesService.error('Ocorreu um erro e a pesquisa de processsos da parte não pode ser realizada!');
        }); 
    };
    
    public adicionarProcessosPreventos (processoPrevento : ProcessoDistribuido) : void {
        for (let i = 0; i < this.processosPreventosAdicionados.length; i++){
            if (this.processosPreventosAdicionados[i].processoId === processoPrevento.processoId){
                this.messagesService.error('O processo já foi adicionado!');
                return;
            }
            if (this.processosPreventosAdicionados[i].relatorId != processoPrevento.relatorId){
                this.messagesService.error('Este processo possui um relator diferente do processo já adicionado!');
                return;
            }
        }
        
        this.processosPreventosAdicionados.push(processoPrevento);
    };
    
    public removerProcessoPrevento(indice : number) : void {
        this.processosPreventosAdicionados.splice(indice,1);
    }
    
    public distribuirProcesso(): void {
        this.processosPreventosAdicionados.forEach(processo => {
            this.cmdDistribuir.processosPreventos.push(Number(processo.processoId));
            
        });
        
        this.distribuicaoPrevencaoService.distribuirPorPrevencao(this.cmdDistribuir).then(() => {
            this.$state.go('app.tarefas.minhas-tarefas');
            this.messagesService.success('Processo distribuído com sucesso.');
        }, () => {
            this.messagesService.error('Erro ao distribuir o processo.');
        });
    }
}

distribuicao.controller('app.distribuicao.DistribuicaoPrevencaoController', DistribuicaoPrevencaoController);

export default distribuicao;