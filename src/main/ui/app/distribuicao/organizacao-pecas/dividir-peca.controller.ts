import IStateParamsService = angular.ui.IStateParamsService;
import IStateService = angular.ui.IStateService;
import {OrganizaPecasService} from "./organiza-pecas.service";
import {Peca, TipoPeca, Visibilidade, Processo, Documento, DividirPecaCommand, QuebrarPecaCommand} from "./shared/pecas.model"
import pecas from "./organiza-pecas.module";


/**
 * @author rodrigo.barreiros
 */
export class DividirPecaController {
    
    public cmdDividirPeca : DividirPecaCommand;
    public cmdPecaParticionada : QuebrarPecaCommand;
    public documentoRecuperado : Documento;
    private situacaoPeca : string;
    private visibilidadePeca : string;

    static $inject = ['$state', '$previousState', '$mdDialog', '$stateParams', 'messagesService', 
                      'app.distribuicao.organizacao-pecas.OrganizaPecasService', 'peca', 'processoId', 'tipoPecas'];
    
    constructor(private $state: IStateService, private $previousState, private $mdDialog, private $stateParams: IStateParamsService,
        private messagesService: app.support.messaging.MessagesService, private organizaPecasService: OrganizaPecasService,
        public peca: Peca, private processoId: number, public tipoPecas : Array<TipoPeca>) {
            $previousState.memo('modalInvoker');
            this.cmdDividirPeca = new DividirPecaCommand(peca.pecaId, processoId);
            this.situacaoPeca = peca.situacao;
            this.visibilidadePeca = peca.visibilidade;
            this.cmdPecaParticionada = new QuebrarPecaCommand(peca.situacao);
            organizaPecasService.consultarDocumento(peca.documentoId).then((documento : Documento) => {
                this.documentoRecuperado = documento;
            });
    }
    
    public adicionarPecaParticionada() : void {
        if (this.cmdPecaParticionada.paginaFinal > this.documentoRecuperado.quantidadePaginas){
            this.messagesService.error('Número final da página é maior que o numero total de páginas do documento.');
            return;
        }
        this.cmdPecaParticionada.visibilidade = this.visibilidadePeca;
        this.cmdDividirPeca.pecas.push(this.cmdPecaParticionada);
        this.cmdPecaParticionada = new QuebrarPecaCommand(this.situacaoPeca);
    }
    
    public removerIntervalo (indice : number) : void {
        this.cmdDividirPeca.pecas.splice(indice, 1)
    }
    
    public confirmar() : void {
        let ultimoIndice = this.cmdDividirPeca.pecas.length;
        
        if (this.cmdDividirPeca.pecas[ultimoIndice - 1].paginaFinal < this.documentoRecuperado.quantidadePaginas){
            this.messagesService.error('As todas as páginas do texto original devem ser contempladas na divisão.');
            return;
        }
        
        this.organizaPecasService.dividirPeca(this.cmdDividirPeca)
            .then(() => {
                this.messagesService.success('Peça dividida com sucesso.');
                this.$state.go('app.tarefas.organizacao-pecas', 
                        {informationId: this.$stateParams['informationId']},
                        {reload: true});
                this.$mdDialog.cancel();
            }, () => {
                this.messagesService.error('Erro ao dividir a peça.');
        });
    }
    
    public sair() : void {
        this.$previousState.go('modalInvoker');
        this.$mdDialog.cancel();
    }
}

pecas.controller('app.distribuicao.organizacao-pecas.DividirPecaController', DividirPecaController);

export default pecas;