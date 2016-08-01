import distribuicao from "./distribuicao.module";
import IDialogService = angular.material.IDialogService;
import IStateParamsService = angular.ui.IStateParamsService;
import IStateService = angular.ui.IStateService;
import {DistribuicaoService} from "./distribuicao.service";
import {DistribuirProcessoCommand, Processo, Ministro} from "../shared/distribuicao.model"

export class DistribuicaoController {
   // public tiposDistribuicao: Array<TipoDistribuicao>;
    public ministrosSelecionados: { singleSelect: any, multipleSelect: Array<Ministro> };
    public ministrosAdicionados: { singleSelect: any, multipleSelect: Array<Ministro> };
    public justificativa: string;
    public numProcessoPrevencao: string;
    public distribuicaoId: number;
    
    public cmdDistribuir : DistribuirProcessoCommand = new DistribuirProcessoCommand(); 
    
    static $inject = ['$state', '$stateParams', 'messagesService', "app.novo-processo.distribuicao.DistribuicaoService", "tipoDistribuicao", "ministrosCandidatos"];
    
    /** @ngInject **/
    constructor(private $state: IStateService, private $stateParams: IStateParamsService, private messagesService: app.support.messaging.MessagesService,
    		private distribuicaoService: DistribuicaoService,  public tipoDistribuicao, public ministrosCandidatos) {
        
    	this.cmdDistribuir.distribuicaoId = $stateParams['informationId'];
    	//this.tiposDistribuicao = distribuicaoService.listarTiposDistribuicao();
        //distribuicaoService.listarMinistros().then((ministros: Ministro[]) => {
        //    this.ministrosCandidatos = ministros;
        //});
        //this.ministrosImpedidos = new Array<Ministro>();
        this.ministrosAdicionados = {singleSelect: null, multipleSelect: []};
        this.ministrosSelecionados = {singleSelect: null, multipleSelect: []};
        //this.justificativa = "";
        //this.processosPreventos = [];
        //TO DO: Passar o nº do processo e da distribuição oriundos do mecanismo de ações.
        //this.processo = this.distribuicaoService.consultarProcessoParaDistribuicao(1);
        //this.distribuicaoId = 1;
    }
    
    public adicionarMinistroImpedido(): void {
        let ministros = new Array<Ministro>();
        let indice = 0;
        
        for(let i = 0; i < this.ministrosSelecionados.multipleSelect.length; i++){
            ministros.push(this.ministrosSelecionados.multipleSelect[i]);
            this.ministrosImpedidos.push(this.ministrosSelecionados.multipleSelect[i]);
        }
        
        for(let i = 0; i < ministros.length; i++){
            indice = this.ministrosCandidatos.indexOf(ministros[i]);
            this.ministrosCandidatos.splice(indice, 1);
        }
    }
    
    public adicionarTodosMinistrosImpedidos(): void {
        let todosMinistros = new Array<Ministro>();
        this.ministrosCandidatos.forEach(ministro => todosMinistros.push(ministro));
        todosMinistros.forEach(ministro => this.ministrosImpedidos.push(ministro));
        this.ministrosCandidatos.splice(0);
    }
    
    public removerMinistroImpedido(): void {
        let ministros = new Array<Ministro>();
        let indice = 0;
        
        for(let i = 0; i < this.ministrosAdicionados.multipleSelect.length; i++){
            ministros.push(this.ministrosAdicionados.multipleSelect[i]);
            this.ministrosCandidatos.push(this.ministrosAdicionados.multipleSelect[i]);
        }
        
        for(let i = 0; i < ministros.length; i++){
            indice = this.ministrosImpedidos.indexOf(ministros[i]);
            this.ministrosImpedidos.splice(indice, 1);
        }
    }
    
    public removerTodosMinistrosImpedidos(): void {
        let todosMinistros = new Array<Ministro>();
        this.ministrosImpedidos.forEach(ministro => todosMinistros.push(ministro));
        todosMinistros.forEach(ministro => this.ministrosCandidatos.push(ministro));
        this.ministrosImpedidos.splice(0);
    }
    
    
    public isFormValido(): boolean {
        return (this.tipoDistribuicao != "" && this.justificativa != "");
    }
    
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
}

distribuicao.controller('app.novo-processo.distribuicao.DistribuicaoController', DistribuicaoController);

export default distribuicao;