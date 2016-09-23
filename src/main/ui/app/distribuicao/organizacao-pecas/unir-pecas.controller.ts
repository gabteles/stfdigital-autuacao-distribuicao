import IStateParamsService = angular.ui.IStateParamsService;
import IStateService = angular.ui.IStateService;
import {OrganizaPecasService} from "./organiza-pecas.service";
import {Peca, Processo, UnirPecasCommand, PecaSelecionavel, Documento} from "./shared/pecas.model"
import pecas from "./organiza-pecas.module";


/**
 * @author rodrigo.barreiros
 */
export class UnirPecasController {
    
    public cmdUnirPecas : UnirPecasCommand = new UnirPecasCommand();
    public documentosRecuperados : Array<Documento> = [];
   
    static $inject = ['$state', '$previousState', '$mdDialog', '$stateParams', 'messagesService', 'app.distribuicao.organizacao-pecas.OrganizaPecasService', 'pecas'];
    
    constructor(private $state: IStateService, private $previousState, private $mdDialog, private $stateParams: IStateParamsService,
        private messagesService: app.support.messaging.MessagesService, private organizaPecasService: OrganizaPecasService,
        public pecas: Array<PecaSelecionavel>) {
            $previousState.memo('modalInvoker');
            pecas.forEach(pecaSelecionavel => {
                this.cmdUnirPecas.pecas.push(pecaSelecionavel.peca.pecaId);
                organizaPecasService.consultarDocumento(pecaSelecionavel.peca.documentoId).then((documento : Documento) => {
                    this.documentosRecuperados.push(documento);
                });
                
                this.cmdUnirPecas.processoId = pecaSelecionavel.processoId;  
            });
            
    }
    
    public unirPecas() : void {
        let tamanhoTotal : number;
        this.documentosRecuperados.forEach(documento => {
            tamanhoTotal = tamanhoTotal + documento.tamanho;
        });
        
        if (tamanhoTotal > 1000000){
            this.messagesService.error('A união das peças está ultrapassando os 10Mb');
            return;
        }
        
        this.organizaPecasService.unirPecas(this.cmdUnirPecas)
            .then(() => {
                this.messagesService.success('Peça juntadas com sucesso.');
                this.$state.go('app.tarefas.organizacao-pecas', 
                        {informationId: this.$stateParams['informationId']},
                        {reload: true});
                this.$mdDialog.cancel();
            }, () => {
                this.messagesService.error('Erro ao unir as peças.');
        });
    }
    
    public sair() : void {
        this.$previousState.go('modalInvoker');
        this.$mdDialog.cancel();
    }
}

pecas.controller('app.distribuicao.organizacao-pecas.UnirPecasController', UnirPecasController);

export default pecas;