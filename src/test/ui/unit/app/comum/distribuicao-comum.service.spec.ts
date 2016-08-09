import {DistribuicaoComumService} from "distribuicao/distribuicao/comum/distribuicao-comum.service";
import {DistribuirProcessoComumCommand, Ministro} from "distribuicao/distribuicao/commons/distribuicao-common.model";
import "distribuicao/distribuicao/comum/distribuicao-comum.service";
import "distribuicao/distribuicao/distribuicao.module";

describe("Teste do serviço de autuacao recursal", () => {

    let $httpBackend : ng.IHttpBackendService;
    let distribuicaoComumService : DistribuicaoComumService;
    let properties;
    let handler;

    beforeEach(angular.mock.module('app.core' ,'app.support', 'app.distribuicao.distribuicao'));

    beforeEach(inject(['$httpBackend', 'properties', 'app.distribuicao.DistribuicaoComumService', (_$httpBackend_ : ng.IHttpBackendService, _properties_, _distribuicaComumService_ : DistribuicaoComumService) => {
        $httpBackend = _$httpBackend_;
        properties = _properties_;
        distribuicaoComumService = _distribuicaComumService_;
    }]));
    
    beforeEach(() => {
        handler = {
            success: () => {},
            error: () => {}
        }; 
        spyOn(handler, 'success').and.callThrough();
        spyOn(handler, 'error').and.callThrough(); 
    });

    it("deveria chamar o método de distribuir o processo do tipo comum", () => {
        let cmdDistribuir : DistribuirProcessoComumCommand = new DistribuirProcessoComumCommand();
        let ministrosCandidatos = [41,42];
        let ministrosImpedidos = [44, 45];
        cmdDistribuir.tipoDistribuicao = 'COMUM';
        cmdDistribuir.justificativa = 'TESTE JUSTIFICATIVA';
        cmdDistribuir.ministrosCandidatos = ministrosCandidatos;
        cmdDistribuir.ministrosImpedidos = ministrosImpedidos;
        cmdDistribuir.distribuicaoId = 1;
        $httpBackend.expectPOST(properties.apiUrl + '/distribuicao/api/distribuicao/comum', cmdDistribuir).respond(200,"");
        distribuicaoComumService.enviarProcessoParaDistribuicao(cmdDistribuir).then(handler.success, handler.error);
        $httpBackend.flush();
        expect(handler.success).toHaveBeenCalled();
        expect(handler.error).not.toHaveBeenCalled();
    });
    
});