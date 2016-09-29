import {ExcluirPecasController} from "distribuicao/organizacao-pecas/excluir-pecas.controller";
import {OrganizaPecasService} from "distribuicao/organizacao-pecas/organiza-pecas.service";
import {Peca, Processo, ExcluirPecasCommand, PecaSelecionavel} from "distribuicao/organizacao-pecas/shared/pecas.model";

describe ("Teste do controlador de excluir pecas", () => {

    let controller : ExcluirPecasController;
    let $q : ng.IQService;
    let $rootScope : ng.IRootScopeService;
    let mockState;
    let mockOrganizacaoPecasService;
    let mockPreviousState;
    let mockMdDialog;
    let mockPecaSelecionavel : Array<PecaSelecionavel>;
    let mockPeca : Peca;
    let mockProcessoId : number;
    let mockMessagesService;
    let mockStateParams;
    
    beforeEach(inject((_$q_, _$rootScope_) => {
        $q = _$q_;
        $rootScope = _$rootScope_;
    }));
    
    beforeEach(() => {
        
        mockState = {
            go : () => {}
            
        };
        
        mockProcessoId = 9003;
        
        mockPeca = new Peca();
        mockPeca.descricao = "teste";
        mockPeca.documentoId = 17;
        mockPeca.numeroOrdem = 2;
        mockPeca.pecaId = 7;
        mockPeca.situacao = "JUNTADA";
        mockPeca.tipoPeca = 100;
        mockPeca.visibilidade = "PUBLICA";
        
        mockPecaSelecionavel = [];
        
        let pecaSelecionavel = new PecaSelecionavel(mockProcessoId,mockPeca);
        
        mockPecaSelecionavel.push(pecaSelecionavel);
        
        mockMessagesService = {
            success: () => {},
            error : () => {}
        };
        
        mockStateParams = {
                informationId: "9004",
                target : mockPecaSelecionavel
        };
        
        mockMdDialog = {
                cancel : () => {}
        };
        
        mockPreviousState = {
                go : () => {},
                memo : () => {}
        };
        
        mockOrganizacaoPecasService = {
                excluirPecas : () => {}
        };
        
        controller = new ExcluirPecasController(mockState, mockPreviousState, mockMdDialog, mockStateParams, mockMessagesService, mockOrganizacaoPecasService, 
                mockPecaSelecionavel);
    });
    
    it ("Deveria excluir a(s) peça(s)", () => {
        spyOn(mockOrganizacaoPecasService, 'excluirPecas').and.callFake(() => $q.when());
        spyOn(mockState, 'go').and.callThrough();
        controller.excluirPecas();
        $rootScope.$apply();
        expect(mockOrganizacaoPecasService.excluirPecas).toHaveBeenCalledWith(controller.cmdExcluirPecas);
        expect(mockState.go).toHaveBeenCalledWith("app.tarefas.organizacao-pecas", Object({ informationId: '9004' }), Object({ reload: true })); 
    });
    
});
