import {UnirPecasController} from "distribuicao/organizacao-pecas/unir-pecas.controller";
import {Peca, Processo, Documento, UnirPecasCommand, PecaSelecionavel} from "distribuicao/organizacao-pecas/shared/pecas.model";

describe ("Teste do controlador de unir pecas", () => {

    let controller : UnirPecasController;
    let $q : ng.IQService;
    let $rootScope : ng.IRootScopeService;
    let mockState;
    let mockOrganizacaoPecasService;
    let mockPecasService;
    let mockPreviousState;
    let mockMdDialog;
    let mockPecasSelecionaveis : Array<PecaSelecionavel>;
    var mockPecas : Array<Peca>;
    let mockProcessoId : number;
    let mockMessagesService;
    let mockStateParams;
    let mockCallback : Function;
    
    beforeEach(inject((_$q_, _$rootScope_) => {
        $q = _$q_;
        $rootScope = _$rootScope_;
    }));
    
    beforeEach(() => {
        
        mockState = {
            go : () => {}
            
        };
        
        //criarMockDePecas(); 
        mockProcessoId = 9003;
        mockPecas = [];
        
        let mockPeca1 = new Peca();
        mockPeca1.descricao = "teste1";
        mockPeca1.documentoId = 17;
        mockPeca1.numeroOrdem = 2;
        mockPeca1.pecaId = 7;
        mockPeca1.situacao = "JUNTADA";
        mockPeca1.tipoPeca = 100;
        mockPeca1.visibilidade = "PUBLICA";
        let mockPeca2 = new Peca();
        mockPeca2.descricao = "teste2";
        mockPeca2.documentoId = 18;
        mockPeca2.numeroOrdem = 3;
        mockPeca2.pecaId = 8;
        mockPeca2.situacao = "JUNTADA";
        mockPeca2.tipoPeca = 99;
        mockPeca2.visibilidade = "PUBLICA";
        mockPecas.push(mockPeca1);
        mockPecas.push(mockPeca2);
        
        mockPecasSelecionaveis = [];
        
        mockPecas.forEach(mockPeca => {
            let pecaSelecionavel = new PecaSelecionavel(mockProcessoId,mockPeca);
            mockPecasSelecionaveis.push(pecaSelecionavel);
        })
        
        mockMessagesService = {
            success: () => {},
            error : () => {}
        };
        
        mockStateParams = {
            informationId: "9004",
            targets : mockPecasSelecionaveis
        };
        
        mockMdDialog = {
            cancel : () => {}
        };
        
        mockPreviousState = {
            go : () => {},
            memo : () => {}
        };
        
        mockOrganizacaoPecasService = {
            unirPecas : () => {}
        };
        
        mockPecasService = {
            consultarDocumento : () => {}      
        };
        
        spyOn(mockPecasService, 'consultarDocumento').and.callFake((documentoId) => {
            return mockCallback(documentoId);
        });
        
        
    });
    
    it ("Deveria entrar na condição de erro devido o somatório do tamanho das peças estar acima de 10Mb", () => {
        mockCallback = (documentoId) => {
            if (documentoId === 17){
                return $q.when(new Documento(17, '', 1000000, 5))
            }else if (documentoId === 18){
                return $q.when(new Documento(18, '', 1000000, 5))
            }
        };
        controller = new UnirPecasController(mockState, mockPreviousState, mockMdDialog, mockStateParams, mockMessagesService, mockOrganizacaoPecasService,
                mockPecasService, mockPecasSelecionaveis);
        $rootScope.$apply();
        spyOn(mockMessagesService, 'error').and.callThrough();
        controller.unirPecas();
        $rootScope.$apply();
        expect(mockMessagesService.error).toHaveBeenCalledWith('A união das peças está ultrapassando os 10Mb');
    });
    
    it ('Deveria executar a ação de unir as peças', () => {
        mockCallback = (documentoId) => {
            if (documentoId === 17){
                return $q.when(new Documento(17, '', 50000, 5))
            }else if (documentoId === 18){
                return $q.when(new Documento(18, '', 50000, 5))
            }
        };
        controller = new UnirPecasController(mockState, mockPreviousState, mockMdDialog, mockStateParams, mockMessagesService, mockOrganizacaoPecasService,
                mockPecasService, mockPecasSelecionaveis);
        $rootScope.$apply();
        spyOn(mockOrganizacaoPecasService, 'unirPecas').and.callFake(() => $q.when());
        spyOn(mockState, 'go').and.callThrough();
        controller.unirPecas();
        $rootScope.$apply();
        expect(mockOrganizacaoPecasService.unirPecas).toHaveBeenCalledWith(controller.cmdUnirPecas);
        expect(mockState.go).toHaveBeenCalledWith("app.tarefas.organizacao-pecas", Object({ informationId: '9004' }), Object({ reload: true })); 
        
    });
    
    
    var criarMockDePecas = function()  {

    }
    
});
