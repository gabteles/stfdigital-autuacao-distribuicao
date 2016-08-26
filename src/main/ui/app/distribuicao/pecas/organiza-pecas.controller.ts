import IStateParamsService = angular.ui.IStateParamsService;
import IStateService = angular.ui.IStateService;
import SortableOptions = angular.ui.SortableOptions;
import {OrganizaPecasService} from "./organiza-pecas.service";
import {Peca, PecasCommand} from "./shared/pecas.model"
import pecas from "./organiza-pecas.module";

class SelectedObject {
    
    constructor(public peca: Peca, public processoId: number) { }
}

export class OrganizaPecasController {
    public pecasId: number;
    
    public cmdPecas : PecasCommand = new PecasCommand();

    public checkToggle : boolean;

    public habilitado : boolean;

    public dtOptions: any;

    public resultados: Array<Peca> = [];
    
    public selecao: Array<SelectedObject>;
    
    static $inject = ['$state', '$stateParams', 'messagesService', "FileUploader", "DTOptionsBuilder", "app.distribuicao.OrganizaPecasService", "processo"];
    
    /** @ngInject **/
    constructor(private $state: IStateService, private $stateParams: IStateParamsService, private messagesService: app.support.messaging.MessagesService,
    		FileUploader: any, DTOptionsBuilder: any, private organizaPecasService: OrganizaPecasService, public processo) {
        
        //this.cmdPecas.distribuicaoId = $stateParams['informationId'];
        this.dtOptions = DTOptionsBuilder.newOptions().withDOM('ptr');
    }
    
    public atualizaEstado() : void{
        if (this.habilitado){
            this.sortableOptions.disabled = false;
        }else{
            this.sortableOptions.disabled = true;
        }
    };
    
    
    public buildSelectedObject (item : Peca) {
        //TODO: trazer o id do processo
        return new SelectedObject(item, this.processo.processoId);
    };
    
    public toggleCheck() {
        if (this.checkToggle) {
            this.selecao = this.resultados.map((item) => {
                return this.buildSelectedObject(item);
            });
        } else {
            this.selecao = [];
        }
    }; 
    
    public sortableOptions : SortableOptions<Object> = {
        helper : (e, ui) => {
            ui.children().each(function() {
                $(this).width($(this).width());
            });
            return ui;
        },
        disabled: false
    };  
    
/*    public carregarViewTipoDistribuicao() : void {
        if (this.cmdDistribuir.tipoDistribuicao == 'COMUM'){
            this.$state.go('app.tarefas.distribuicao-comum');
        }else{
            this.$state.go('app.tarefas.distribuicao-prevencao');
        }
    }
*/    
 
}

pecas.controller('app.distribuicao.OrganizaPecasController', OrganizaPecasController);

export default pecas;