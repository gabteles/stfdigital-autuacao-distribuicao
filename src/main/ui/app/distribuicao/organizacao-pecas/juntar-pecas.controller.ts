import IStateParamsService = angular.ui.IStateParamsService;
import IStateService = angular.ui.IStateService;
import {OrganizaPecasService} from "./organiza-pecas.service";
import {Peca, Processo, JuntarPecaCommand, PecaSelecionavel} from "./shared/pecas.model"
import pecas from "./organiza-pecas.module";


/**
 * @author rodrigo.barreiros
 */
export class JuntarPecasController {
    
    public cmdJuntarPecas: JuntarPecaCommand = new JuntarPecaCommand();
   
    static $inject = ['$state', '$previousState', '$mdDialog', '$stateParams', 'messagesService', 'app.distribuicao.organizacao-pecas.OrganizaPecasService', 'pecas'];
    
    constructor(private $state: IStateService, private $previousState, private $mdDialog, private $stateParams: IStateParamsService,
        private messagesService: app.support.messaging.MessagesService, private organizaPecasService: OrganizaPecasService,
        public pecas: Array<PecaSelecionavel>) {
            $previousState.memo('modalInvoker');
            pecas.forEach(pecaSelecionavel => {
                this.cmdJuntarPecas.pecas.push(pecaSelecionavel.peca.pecaId);
                this.cmdJuntarPecas.processoId = pecaSelecionavel.processoId;  
            });
    }
    
    public juntar() : void {
        let pecaJuntada : boolean = false;
        this.pecas.forEach(peca => { 
            pecaJuntada = peca.peca.situacao === "Juntada" ? true : false;
        });
        
        if (pecaJuntada){
            this.messagesService.error('Esta peça já possui a situação juntada.');
            return;
        }
        
        this.organizaPecasService.juntarPecas(this.cmdJuntarPecas)
            .then(() => {
                this.messagesService.success('Peça(s) juntada(s) com sucesso.');
                this.$state.go('app.tarefas.organizacao-pecas', 
                        {informationId: this.$stateParams['informationId']},
                        {reload: true});
                this.$mdDialog.cancel();
            }, () => {
                this.messagesService.error('Erro ao juntar a peça.');
        });
    }
    
    public sair() : void {
        this.$previousState.go('modalInvoker');
        this.$mdDialog.cancel();
    }
}

pecas.controller('app.distribuicao.organizacao-pecas.JuntarPecasController', JuntarPecasController);

export default pecas;