import IStateParamsService = angular.ui.IStateParamsService;
import IStateService = angular.ui.IStateService;
import IDialogService = angular.material.IDialogService;
import ICookiesService = angular.cookies.ICookiesService;
import IWindowService = angular.IWindowService;
import {OrganizaPecasService} from "./organiza-pecas.service";
import {PecasService} from "./shared/pecas.service";
import {Peca, TipoPeca, Visibilidade, Processo, Documento,  DocumentoTemporario, DocumentoTemporarioDto, TipoAnexo, 
            PecaSelecionavel, InserirPecaCommand, CadastrarPecaCommand} from "./shared/pecas.model"
import pecas from "./organiza-pecas.module";


/**
 * @author rodrigo.barreiros'
 */
export class InserirPecasController {
    
    public cmdInserirPeca : InserirPecaCommand;
    public cmdPecaInserida : CadastrarPecaCommand;
    public documentoRecuperado : Documento;
    private situacaoPeca : string;
    public documentosTemporario: Array<DocumentoTemporario> = new Array<DocumentoTemporario>();
    public pecasInseridas : Array<CadastrarPecaCommand> = [];
    private uploader: any;
    private configurarSelect2: any;

    static $inject = ['$state', '$previousState', '$mdDialog', '$stateParams', 'messagesService', '$window', '$cookies', 'FileUploader', 'properties',
                      'app.distribuicao.organizacao-pecas.OrganizaPecasService',  'app.distribuicao.organizacao-pecas.PecasService', 'tipoPecas', 'processo'];
    
    constructor(private $state: IStateService, private $previousState, private $mdDialog, private $stateParams: IStateParamsService,
        private messagesService: app.support.messaging.MessagesService,  private $window: IWindowService, private $cookies: ICookiesService, 
        FileUploader: any, private properties, private organizaPecasService: OrganizaPecasService, private pecasService : PecasService,
        public tipoPecas : Array<TipoPeca>, public processo : Processo) {
        
            $previousState.memo('modalInvoker');
            this.cmdInserirPeca = new InserirPecaCommand(processo.processoId);
            
            this.uploader = new FileUploader({
                url: properties.url + ":" + properties.port + "/documents/api/documentos/upload/assinado",
                headers : {"X-XSRF-TOKEN": $cookies.get('access_token'), "Authorization":  'Bearer ' + $cookies.get('access_token')},
                formData: [{name: "file"}],
                filters: [{
                    name: "arquivos-pdf",
                    fn: (file) => {
                        if (file.type === "application/pdf") {
                            return true;
                        } else {
                            this.exibirMensagem("Não foi possível anexar o arquivo " + file.name + ". <br />Apenas documentos *.pdf são aceitos.");
                            return false;
                        }
                    }
                }, {
                    name: "tamanho-maximo",
                    fn: (file) => {
                        if (file.size / 1024 / 1024 > 10) {
                            this.exibirMensagem("Não foi possível anexar o arquivo " + file.name + ". <br />O tamanho do arquivo excede 10mb.");
                            return false;
                        }
                        return true;
                    }
                }] 
            });
            
            this.uploader.onAfterAddingFile = (arquivo) => {
                //console.error("onAfterAddingFile: " + JSON.stringify(arquivo));
                let documentoTemporario = new DocumentoTemporario(arquivo, null, null, false);
                arquivo.anexo = documentoTemporario;
                this.documentosTemporario.push(documentoTemporario);
                arquivo.upload();
            };
            
            this.uploader.onSuccessItem = (arquivo, response, status) => {
                //console.error("onSucessItem-arquivo: " + JSON.stringify(arquivo));
                console.error("onSucessItem-response: " + JSON.stringify(response));
                console.error("onSucessItem-status: " + JSON.stringify(status));
                arquivo.anexo.documentoTemporario = response;
                arquivo.anexo.isExcluirServidor = true;
                arquivo.anexo.isUploadConcluido = true;
                this.anexosMudaram();
            };
            
            this.uploader.onErrorItem = (arquivo, response, status) => {
                //console.error("onErrorItem-arquivo: ", JSON.stringify(arquivo));
                console.error("onErrorItem-response: ", JSON.stringify(response));
                console.error("onErrorItem-status: ", JSON.stringify(status));
                /* O status 0 provavelmente foi porque a conexão foi resetada por ultrapassar o tamanho máximo de 10 MB no backend. */
                if (status === 0) {
                    this.exibirMensagem('Não foi possível anexar o arquivo "' + arquivo.file.name + '". O tamanho do arquivo excede 10mb.');
                } else {
                    if (status === 500){
                        this.exibirMensagem("Não foi possível anexar o arquivo " + arquivo.file.name + ".");
                    } else {
                        this.exibirMensagem(response.errors[0].message);
                    }
                }
                
                this.removerAnexo(arquivo);
            };
            
            this.uploader.filters.push({
                name: 'customFilter',
                fn: function(item /*{File|FileLikeObject}*/, options) {
                    return this.queue.length < 10;
                }
            });
            
            this.configurarSelect2 = (idx) => {
                return {
                    dropdownCssClass: 'select2-resultado-tipo-peca-' + idx,
                    containerCss: {
                        'min-width': '100%'
                    },
                    formatSelection: (item) => {
                        var originalText = item.text;
                        return "<div data-original-title='" + originalText + "'>" + originalText + "</div>";
                    },
                    formatResult: function(item) {
                        return item.text;
                    }
                };
            };
    }
    
    private exibirMensagem(mensagem: string) {
        this.messagesService.error(mensagem);
    }
    
    //remove uma peças da petição.
    private removerAnexo(documentoTemporario: any): void {
        if (documentoTemporario.isExcluirServidor){
            this.pecasService.excluirDocumentoTemporarioAssinado([documentoTemporario.documentoTemporario]);
        }
        
        let indice = this.documentosTemporario.indexOf(documentoTemporario);
        this.documentosTemporario.splice(indice, 1);
        this.anexosMudaram();
    }
    
    private limparAnexos() : void {
        let arquivosTemporarios = [];
        let numeroArquivos = this.documentosTemporario.length;
        
        for(let i = 0; i < this.documentosTemporario.length; i++){
            arquivosTemporarios.push(this.documentosTemporario[i].documentoTemporario);
        }
        
        this.pecasService.excluirDocumentoTemporarioAssinado(arquivosTemporarios);
        
        this.documentosTemporario.splice(0);
        this.uploader.clearQueue();
        this.uploader.progress = 0;
    }    
    
    
    private anexosMudaram(): void {
        console.error("entrou em anexos mudaram");
        let documentosTemporarios: Array<DocumentoTemporarioDto> = new Array<DocumentoTemporarioDto>();
        let quantidadePecas = this.processo.pecas.length;
        //console.error("antes do primeiro map: " + JSON.stringify(this.documentosTemporario));
        documentosTemporarios = this.documentosTemporario.map<DocumentoTemporarioDto>((documento) => {
            console.error("dentro do primeiro map-documentoTemporario: " + documento.documentoTemporario);
            console.error("dentro do primeiro map-tipo: " + documento.tipo);
            return new DocumentoTemporarioDto(documento.tipo ? documento.tipo.tipoId : null, documento.documentoTemporario);
        });
        //console.error("depois do primeiro map: " + JSON.stringify(documentosTemporarios));
        this.pecasInseridas = documentosTemporarios.map<CadastrarPecaCommand>(doc => {
            console.error("dentro do segundo map-documentoId: " + doc.documentoId);
            console.error("dentro do segundo map-tipoDocumentoId: " + doc.tipoDocumentoId);
            return new CadastrarPecaCommand(doc.documentoId.toString(), doc.tipoDocumentoId, ++quantidadePecas);
        });
        //console.error("depois do segundo map: " + JSON.stringify(this.pecasInseridas));
        this.cmdInserirPeca.pecas = this.pecasInseridas;
        console.error('anexos mudaram: ' + JSON.stringify(this.cmdInserirPeca));
    }
    
    public confirmar() : void {
        let ultimoIndice = this.cmdInserirPeca.pecas.length;
        console.error('vai inserir peças com command: ' + JSON.stringify(this.cmdInserirPeca));
        this.organizaPecasService.inserirPecas(this.cmdInserirPeca)
            .then(() => {
                this.$state.go('app.tarefas.organizacao-pecas', 
                        {informationId: this.$stateParams['informationId']},
                        {reload: true}).then(() => {
                            this.messagesService.success('Peça(s) inserida(s) com sucesso.');
                        });
                this.$mdDialog.cancel();
            }, () => {
                this.messagesService.error('Erro ao inserir a peça.');
        });
    }
    
    public sair() : void {
        this.$previousState.go('modalInvoker');
        this.$mdDialog.cancel();
    }
}

pecas.controller('app.distribuicao.organizacao-pecas.InserirPecasController', InserirPecasController);

export default pecas;