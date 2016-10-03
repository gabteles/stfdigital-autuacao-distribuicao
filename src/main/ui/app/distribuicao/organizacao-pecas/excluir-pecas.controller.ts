import IStateParamsService = angular.ui.IStateParamsService;
import IStateService = angular.ui.IStateService;
import {OrganizaPecasService} from "./organiza-pecas.service";
import {Peca, Processo, ExcluirPecasCommand, PecaSelecionavel} from "./shared/pecas.model"
import pecas from "./organiza-pecas.module";


/**
 * @author rodrigo.barreiros
 */
export class ExcluirPecasController {
    
    public cmdExcluirPecas : ExcluirPecasCommand = new ExcluirPecasCommand();
   
    static $inject = ['$state', '$previousState', '$mdDialog', '$stateParams', 'messagesService', 'app.distribuicao.organizacao-pecas.OrganizaPecasService', 'pecas'];
    
    constructor(private $state: IStateService, private $previousState, private $mdDialog, private $stateParams: IStateParamsService,
        private messagesService: app.support.messaging.MessagesService, private organizaPecasService: OrganizaPecasService,
        public pecas: Array<PecaSelecionavel>) {
            $previousState.memo('modalInvoker');
            pecas.forEach(pecaSelecionavel => {
                this.cmdExcluirPecas.pecas.push(pecaSelecionavel.peca.pecaId);
                this.cmdExcluirPecas.processoId = pecaSelecionavel.processoId;  
            });
    }
    
    public excluirPecas() : void {
        let pecaExcluida : boolean = false;
        this.pecas.forEach(peca => { 
            pecaExcluida = peca.peca.situacao === "EXCLUIDA" ? true : false;
        });
        
        if (pecaExcluida){
            this.messagesService.error('Esta peça já foi excluída');
            return;
        }
        
        this.organizaPecasService.excluirPecas(this.cmdExcluirPecas)
            .then(() => {
                this.messagesService.success('Peça excluída com sucesso.');
                this.$state.go('app.tarefas.organizacao-pecas', 
                        {informationId: this.$stateParams['informationId']},
                        {reload: true});
                this.$mdDialog.cancel();
            }, () => {
                this.messagesService.error('Erro ao excluir a peça.');
        });
    }
    
    public sair() : void {
        this.$previousState.go('modalInvoker');
        this.$mdDialog.cancel();
    }
}

pecas.controller('app.distribuicao.organizacao-pecas.ExcluirPecasController', ExcluirPecasController);

export default pecas;