import IStateParamsService = angular.ui.IStateParamsService;
import IStateService = angular.ui.IStateService;
import SortableOptions = angular.ui.SortableOptions;
import {OrganizaPecasService} from "./organiza-pecas.service";
import {Peca, Processo, OrganizaPecasCommand, PecaSelecionavel} from "./shared/pecas.model"
import pecas from "./organiza-pecas.module";


/**
 * @author rodrigo.barreiros
 * 
 */
export class OrganizaPecasController {
    
    public pecasId: number;
    
    public cmdPecas : OrganizaPecasCommand = new OrganizaPecasCommand();

    public checkToggle : boolean = false;

    public desabilitado : boolean = false;

    public dtOptions: any;
    
    public selecao: Array<PecaSelecionavel> = new Array<PecaSelecionavel>();
    
    public pecas: Array<PecaSelecionavel> = new Array<PecaSelecionavel>();
    
    static $inject = ['$state', '$stateParams', 'messagesService', 'DTOptionsBuilder', 'app.distribuicao.organizacao-pecas.OrganizaPecasService', 'processo'];
    
    /** @ngInject **/
    constructor(private $state: IStateService, private $stateParams: IStateParamsService, private messagesService: app.support.messaging.MessagesService,
            DTOptionsBuilder: any, private organizaPecasService: OrganizaPecasService, private processo: Processo) {
        
        this.cmdPecas.distribuicaoId = $stateParams['informationId'];
        this.dtOptions = DTOptionsBuilder.newOptions().withDOM('ptr');
        processo.pecas.forEach(peca => this.pecas.push(new PecaSelecionavel(processo.processoId, peca)));
    }
    
    public atualizaEstado() : void{
        if (this.desabilitado){
            this.sortableOptions.disabled = true;
        }else{
            this.sortableOptions.disabled = false;
        }
    };
    
    public toggleCheck() {
        if (this.checkToggle) {
            this.selecao = angular.copy(this.pecas);
        } else {
            this.selecao = new Array<PecaSelecionavel>();
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
    
    public finalizar() : void {
        this.pecas.forEach(peca => {
            this.cmdPecas.pecas.push(peca.peca.pecaId);  
        });
        
        this.organizaPecasService.finalizarOrganizacaoPecas(this.cmdPecas)
        .then(() => {
            this.$state.go('app.tarefas.minhas-tarefas');
            this.messagesService.success('Peça(s) organizada(s) com sucesso.');
        }, () => {
            this.messagesService.error('Erro ao organizar a(s) peça(s).');
        });
    }
        
 
}

pecas.controller('app.distribuicao.organizacao-pecas.OrganizaPecasController', OrganizaPecasController);

export default pecas;