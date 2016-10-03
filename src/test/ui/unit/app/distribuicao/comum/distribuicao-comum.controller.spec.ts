import {DistribuicaoComumController} from "distribuicao/distribuicao/comum/distribuicao-comum.controller";
import {DistribuicaoComumService} from "distribuicao/distribuicao/comum/distribuicao-comum.service";
import {DistribuirProcessoComumCommand, Ministro} from "distribuicao/distribuicao/commons/distribuicao-common.model";

describe('Teste do controlador da distribuicao comum', () => {
    
    let controller : DistribuicaoComumController;
    let $q : ng.IQService;
    let $rootScope : ng.IRootScopeService;
    let mockState;
    let mockDistribuicaoComumService;
    let mockMinistrosCandidatos;
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
        mockDistribuicaoComumService = {
        		enviarProcessoParaDistribuicao : () =>{}
        };
        mockMinistrosCandidatos = [
            {id: 41, nome: 'MIN. RICARDO LEWANDOWSKI'}, 
            {id:42, nome: 'MIN. CÁRMEN LÚCIA'}, 
            {id: 43, nome: 'MIN. DIAS TOFFOLI'}
        ];
        mockMessagesService = {
            success: () => {}
        };
        mockStateParams = {
                informationId: 1
        };
       
        controller = new DistribuicaoComumController(mockState, mockStateParams , mockMessagesService, mockDistribuicaoComumService, mockMinistrosCandidatos);
    });
    
    it('Deveria adicionar todos os ministros como impedidos', () => {
        controller.adicionarTodosMinistrosImpedidos();
        expect(controller.ministrosCandidatos.length).toEqual(0, 'A lista de ministros candidatos deveria estar zerada');
        expect(controller.ministrosImpedidos.length).toEqual(3, 'A lista de ministros impedidos deveria estar totalmente preenchidas');
    });
    
   it('Deveria remover todos os ministros impedidos', () => {
       controller.ministrosImpedidos.push(new Ministro(41, 'MIN. RICARDO LEWANDOWSKI'));
       controller.ministrosImpedidos.push(new Ministro(42, 'MIN. CÁRMEN LÚCIA'));
       controller.removerTodosMinistrosImpedidos();
       expect(controller.ministrosImpedidos.length).toEqual(0, 'A lista de ministros impedidos deveria estar zerada');
    });
   
   it('Deveria adicionar um ministro impedido', () => {
	   controller.ministrosImpedidos = [];
	   controller.ministrosSelecionados.multipleSelect.push(new Ministro(43, 'MIN. DIAS TOFFOLI'));
       controller.adicionarMinistroImpedido();
       expect(controller.ministrosImpedidos.length).toEqual(1, 'A lista de ministros impedidos deveria estar com um ministro adicionado');
    });
   
   it('Deveria remover um ministro da lista de impedido', () => {
	   controller.ministrosImpedidos = [];
	   controller.ministrosImpedidos.push(new Ministro(41, 'MIN. RICARDO LEWANDOWSKI'));
       controller.ministrosAdicionados.multipleSelect.push(new Ministro(41, 'MIN. RICARDO LEWANDOWSKI'));
       controller.removerMinistroImpedido();
       expect(controller.ministrosImpedidos.length).toEqual(0, 'A lista de ministros impedidos deveria estar zerada');
    });    
   
    it('Deveria realizar a ação de distribuir o processo pelo tipo de distribuicao comum', () => {
        spyOn(mockDistribuicaoComumService, 'enviarProcessoParaDistribuicao').and.callFake(() => $q.when());
        spyOn(mockState, 'go').and.callThrough();
        controller.distribuirProcesso();
        $rootScope.$apply();
        expect(mockDistribuicaoComumService.enviarProcessoParaDistribuicao).toHaveBeenCalledWith(controller.cmdDistribuir);
        expect(mockState.go).toHaveBeenCalledWith("app.tarefas.minhas-tarefas");
    });
    
});
