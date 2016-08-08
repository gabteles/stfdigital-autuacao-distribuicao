import IStateParamsService = angular.ui.IStateParamsService;
import IStateService = angular.ui.IStateService;
import {DistribuicaoService} from "./distribuicao.service";
import {DistribuirCommand} from "./commons/distribuicao-common.model"
import distribuicao from "./distribuicao.module";

export class DistribuicaoController {
    public justificativa: string;
    public numProcessoPrevencao: string;
    public distribuicaoId: number;
    
    public cmdDistribuir : DistribuirCommand = new DistribuirCommand(); 
    
    static $inject = ['$state', '$stateParams', 'messagesService', "app.distribuicao.DistribuicaoService", "tiposDistribuicao"];
    
    /** @ngInject **/
    constructor(private $state: IStateService, private $stateParams: IStateParamsService, private messagesService: app.support.messaging.MessagesService,
    		private distribuicaoService: DistribuicaoService,  public tiposDistribuicao) {
        
    	this.cmdDistribuir.distribuicaoId = $stateParams['informationId'];
    }
    
    public carregarViewTipoDistribuicao() : void {
    	if (this.cmdDistribuir.tipoDistribuicao == 'COMUM'){
    		this.$state.go('app.tarefas.distribuicao-comum');
    	}else{
    		this.$state.go('app.tarefas.distribuicao-prevencao');
    	}
    }
    
 
}

distribuicao.controller('app.distribuicao.DistribuicaoController', DistribuicaoController);

export default distribuicao;