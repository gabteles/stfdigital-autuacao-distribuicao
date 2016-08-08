import IStateParamsService = angular.ui.IStateParamsService;
import IStateService = angular.ui.IStateService;
import {DistribuicaoComumService} from "./distribuicao-comum.service";
import {DistribuirProcessoComumCommand, Processo, Ministro} from "../commons/distribuicao-common.model"
import distribuicao from "../distribuicao.module";

export class DistribuicaoComumController {
   // public tiposDistribuicao: Array<TipoDistribuicao>;
    public ministrosSelecionados: { singleSelect: any, multipleSelect: Array<Ministro> };
    public ministrosAdicionados: { singleSelect: any, multipleSelect: Array<Ministro> };
    public ministrosImpedidos: Array<Ministro>;
    public justificativa: string;
    public numProcessoPrevencao: string;
    public distribuicaoId: number;
    
    public cmdDistribuir : DistribuirProcessoComumCommand = new DistribuirProcessoComumCommand(); 
    
    static $inject = ['$state', '$stateParams', 'messagesService', "app.distribuicao.DistribuicaoComumService", "ministrosCandidatos"];
    
    /** @ngInject **/
    constructor(private $state: IStateService, private $stateParams: IStateParamsService, private messagesService: app.support.messaging.MessagesService,
    		private distribuicaoComumService: DistribuicaoComumService, public ministrosCandidatos) {
    	this.cmdDistribuir.distribuicaoId = $stateParams['informationId'];
    	this.cmdDistribuir.tipoDistribuicao = 'COMUM';
    	this.ministrosCandidatos.forEach(ministro => this.cmdDistribuir.ministrosCandidatos.push(ministro.id));
        this.ministrosImpedidos = new Array<Ministro>();
        this.ministrosAdicionados = {singleSelect: null, multipleSelect: []};
        this.ministrosSelecionados = {singleSelect: null, multipleSelect: []};
    }
    
    public adicionarMinistroImpedido(): void {
        let ministros = new Array<Ministro>();
        let indice = 0;
        
        for(let i = 0; i < this.ministrosSelecionados.multipleSelect.length; i++){
            ministros.push(this.ministrosSelecionados.multipleSelect[i]);
            this.ministrosImpedidos.push(this.ministrosSelecionados.multipleSelect[i]);
            this.cmdDistribuir.ministrosImpedidos.push(this.ministrosSelecionados.multipleSelect[i].id);
        }
        
        for(let i = 0; i < ministros.length; i++){
            indice = this.ministrosCandidatos.indexOf(ministros[i]);
            this.ministrosCandidatos.splice(indice, 1);
            this.cmdDistribuir.ministrosCandidatos.splice(indice,1);
        }
    }
    
    public adicionarTodosMinistrosImpedidos(): void {
        let todosMinistros = new Array<Ministro>();
        this.ministrosCandidatos.forEach(ministro => todosMinistros.push(ministro));
        todosMinistros.forEach(ministro => this.ministrosImpedidos.push(ministro));
        this.ministrosCandidatos.splice(0);
        this.cmdDistribuir.ministrosCandidatos.splice(0);
        this.ministrosImpedidos.forEach(ministro => this.cmdDistribuir.ministrosImpedidos.push(ministro.id));
    }
    
    public removerMinistroImpedido(): void {
        let ministros = new Array<Ministro>();
        let indice = 0;
        
        for(let i = 0; i < this.ministrosAdicionados.multipleSelect.length; i++){
            ministros.push(this.ministrosAdicionados.multipleSelect[i]);
            this.ministrosCandidatos.push(this.ministrosAdicionados.multipleSelect[i]);
            this.cmdDistribuir.ministrosCandidatos.push(this.ministrosAdicionados.multipleSelect[i].id);
        }
        
        for(let i = 0; i < ministros.length; i++){
            indice = this.ministrosImpedidos.indexOf(ministros[i]);
            this.ministrosImpedidos.splice(indice, 1);
            this.cmdDistribuir.ministrosImpedidos.splice(indice,1);
        }
        
    }
    
    public removerTodosMinistrosImpedidos(): void {
        let todosMinistros = new Array<Ministro>();
        this.ministrosImpedidos.forEach(ministro => todosMinistros.push(ministro));
        todosMinistros.forEach(ministro => this.ministrosCandidatos.push(ministro));
        this.ministrosImpedidos.splice(0);
        this.cmdDistribuir.ministrosImpedidos.splice(0);
        this.ministrosCandidatos.forEach(ministro => this.cmdDistribuir.ministrosCandidatos.push(ministro.id));
    }
    
    public isFormValido(): boolean {
        return (this.cmdDistribuir.justificativa != "");
    }
    
    public distribuirProcesso(): void {
        this.distribuicaoComumService.enviarProcessoParaDistribuicao(this.cmdDistribuir).then(() => {
            this.$state.go('app.tarefas.minhas-tarefas');
            this.messagesService.success('Processo distribuído com sucesso.');
        }, () => {
            this.messagesService.error('Erro ao distribuir o processo.');
        });
    }
    
    
}

distribuicao.controller('app.distribuicao.DistribuicaoComumController', DistribuicaoComumController);

export default distribuicao;