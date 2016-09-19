import IStateParamsService = angular.ui.IStateParamsService;
import IStateService = angular.ui.IStateService;
import {OrganizaPecasService} from "./organiza-pecas.service";
import {Peca, TipoPeca, Visibilidade, Processo, EditarPecaCommand} from "./shared/pecas.model"
import pecas from "./organiza-pecas.module";


/**
 * @author rodrigo.barreiros
 */
export class EditarPecaController {
    
    public cmdEditarPeca : EditarPecaCommand;

    static $inject = ['$state', '$previousState', '$mdDialog', '$stateParams', 'messagesService', 
                      'app.distribuicao.organizacao-pecas.OrganizaPecasService', 'peca', 'processoId', 'tipoPecas', 'visibilidades'];
    
    constructor(private $state: IStateService, private $previousState, private $mdDialog, private $stateParams: IStateParamsService,
        private messagesService: app.support.messaging.MessagesService, private organizaPecasService: OrganizaPecasService,
        public peca: Peca, private processoId: number, public tipoPecas : Array<TipoPeca>, public visibilidades : Array<Visibilidade>) {
            $previousState.memo('modalInvoker');
            this.cmdEditarPeca = new EditarPecaCommand(peca, processoId);
    }
    
    public editar() : void {
        this.organizaPecasService.editarPeca(this.cmdEditarPeca)
            .then(() => {
                this.messagesService.success('Peça editada com sucesso.');
                this.$state.go('app.tarefas.organizacao-pecas', 
                        {informationId: this.$stateParams['informationId']},
                        {reload: true});
                this.$mdDialog.cancel();
            }, () => {
                this.messagesService.error('Erro ao editar a peça.');
        });
    }
    
    public sair() : void {
        this.$previousState.go('modalInvoker');
        this.$mdDialog.cancel();
    }
}

pecas.controller('app.distribuicao.organizacao-pecas.EditarPecaController', EditarPecaController);

export default pecas;