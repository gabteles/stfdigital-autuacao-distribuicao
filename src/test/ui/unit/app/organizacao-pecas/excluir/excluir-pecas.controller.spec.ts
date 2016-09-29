import {ExcluirPecasController} from "distribuicao/organizacao-pecas/excluir-pecas.controller";
import {OrganizaPecasService} from "distribuicao/organizacao-pecas/organiza-pecas.service";
import {Peca, TipoPeca, Visibilidade, Processo, Documento, ExcluirPecasCommand, PecaSelecionavel} from "distribuicao/organizacao-pecas/shared/pecas.model";

describe ("Teste do controlador de dividir pecas", () => {

    let controller : ExcluirPecasController;
    let $q : ng.IQService;
    let $rootScope : ng.IRootScopeService;
    let mockState;
    let mockOrganizacaoPecasService;
    let mockPreviousState;
    let mockMdDialog;
    let mockPecaSelecionavel : PecaSelecionavel;
    let mockPeca : Peca;
    let mockProcessoId : number;
    let mockTipoPecas : Array<TipoPeca> = [];
    let mockMessagesService;
    let mockStateParams;
    let mockQuebraPecaCommand = new QuebrarPecaCommand('JUNTUDA');
    
    beforeEach(inject((_$q_, _$rootScope_) => {
        $q = _$q_;
        $rootScope = _$rootScope_;
    }));
    
    beforeEach(() => {
        
        mockState = {
            go : () => {}
            
        };
        
        mockTipoPecas.push(new TipoPeca(98,"Movimento Processual"));
        mockTipoPecas.push(new TipoPeca(100, "Informação"));
        
        mockPeca = new Peca();
        mockPeca.descricao = "teste";
        mockPeca.documentoId = 17;
        mockPeca.numeroOrdem = 2;
        mockPeca.pecaId = 7;
        mockPeca.situacao = "JUNTADA";
        mockPeca.tipoPeca = 100;
        mockPeca.visibilidade = "PUBLICA";
        
        mockProcessoId = 9003;
        
        mockPecaSelecionavel = new PecaSelecionavel(mockProcessoId,mockPeca);
        
        mockQuebraPecaCommand.paginaFinal = 2;
        
        mockMessagesService = {
            success: () => {}
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
                dividirPeca : () => {},
                consultarDocumento : () => {
                }
        };
        
        spyOn(mockOrganizacaoPecasService, 'consultarDocumento').and.callFake(() => $q.when(new Documento(1, '', 100, 5)));
        
        controller = new DividirPecaController(mockState, mockPreviousState, mockMdDialog, mockStateParams, mockMessagesService, mockOrganizacaoPecasService, 
                mockPeca);
        
        $rootScope.$apply();
    });
    
    it ("Deveria adicionar uma peça", () => {
        controller.cmdDividirPeca.pecas = [];
        controller.cmdPecaParticionada.descricao = 'TESTE';
        controller.cmdPecaParticionada.paginaFinal = 2;
        controller.cmdPecaParticionada.paginaInicial = 1;
        controller.cmdPecaParticionada.tipoPecaId = 99;
        controller.adicionarPecaParticionada(); 
        expect(controller.cmdDividirPeca.pecas.length).toEqual(1, 'A lista de peças divididas deveria estar com uma peça adicionada.');
    });
    
    it ("Deveria remover uma peça", () => {
       controller.cmdDividirPeca.pecas.push(mockQuebraPecaCommand);
       controller.removerIntervalo(0);
       expect(controller.cmdDividirPeca.pecas.length).toEqual(0, 'A lista de peças deveria estar vazia.');
    });
    
    it ("Deveria dividir a peça", () => {
        //Adicionando duas peças ao commando
        controller.cmdDividirPeca.pecas.push(mockQuebraPecaCommand);
        let mockQuebraPecaCommand2 = new QuebrarPecaCommand('JUNTADA');
        mockQuebraPecaCommand2.paginaFinal = 5;
        controller.cmdDividirPeca.pecas.push(mockQuebraPecaCommand2);
        spyOn(mockOrganizacaoPecasService, 'dividirPeca').and.callFake(() => $q.when());
        spyOn(mockState, 'go').and.callThrough();
        controller.confirmar();
        $rootScope.$apply();
        expect(mockOrganizacaoPecasService.dividirPeca).toHaveBeenCalledWith(controller.cmdDividirPeca);
        expect(mockState.go).toHaveBeenCalledWith("app.tarefas.organizacao-pecas", Object({ informationId: '9004' }), Object({ reload: true })); 
    });
    
});
