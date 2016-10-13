import {OrganizaPecasService} from "distribuicao/organizacao-pecas/organiza-pecas.service";
import {OrganizaPecasCommand, ExcluirPecasCommand, UnirPecasCommand, JuntarPecaCommand, EditarPecaCommand,
    DividirPecaCommand, InserirPecaCommand, Peca} from "distribuicao/organizacao-pecas/shared/pecas.model";
import "distribuicao/organizacao-pecas/organiza-pecas.service";
import "distribuicao/organizacao-pecas/organiza-pecas.module";

describe("Teste do serviço de organizar peças", () => {

    let $httpBackend : ng.IHttpBackendService;
    let organizaPecasService : OrganizaPecasService;
    let properties;
    let handler;

    beforeEach(angular.mock.module('app.core', 'app.distribuicao.organizacao-pecas'));

    beforeEach(inject(['$httpBackend', 'properties', 'app.distribuicao.organizacao-pecas.OrganizaPecasService', (_$httpBackend_ : ng.IHttpBackendService, _properties_, 
            _organizaPecasService_ : OrganizaPecasService) => {
        $httpBackend = _$httpBackend_;
        properties = _properties_;
        organizaPecasService = _organizaPecasService_;
    }]));
    
    beforeEach(() => {
        handler = {
            success: () => {},
            error: () => {}
        }; 
        spyOn(handler, 'success').and.callThrough();
        spyOn(handler, 'error').and.callThrough(); 
    });

    it("deveria chamar o método de finalizar a organização de peças", () => {
        let cmdOrganizaPecas : OrganizaPecasCommand = new OrganizaPecasCommand();
        let pecasOrganizadas = [41,42];
        cmdOrganizaPecas.distribuicaoId = 1;
        cmdOrganizaPecas.pecas = pecasOrganizadas;
        cmdOrganizaPecas.finalizarTarefa = true;
        $httpBackend.expectPOST(properties.apiUrl + '/distribuicao/api/organizacao-pecas/organizar', cmdOrganizaPecas).respond(200,"");
        organizaPecasService.finalizarOrganizacaoPecas(cmdOrganizaPecas).then(handler.success, handler.error);
        $httpBackend.flush();
        expect(handler.success).toHaveBeenCalled();
        expect(handler.error).not.toHaveBeenCalled();
    });
    
    it("deveria chamar o método de excluir peças", () => {
        let excluirPecasCommand : ExcluirPecasCommand = new ExcluirPecasCommand();
        let pecasOrganizadas = [41,42];
        excluirPecasCommand.processoId = 1;
        excluirPecasCommand.pecas = pecasOrganizadas;
        $httpBackend.expectPOST(properties.apiUrl + '/distribuicao/api/organizacao-pecas/excluir', excluirPecasCommand).respond(200,"");
        organizaPecasService.excluirPecas(excluirPecasCommand).then(handler.success, handler.error);
        $httpBackend.flush();
        expect(handler.success).toHaveBeenCalled();
        expect(handler.error).not.toHaveBeenCalled();
    });
    
    it("deveria chamar o método de unir peças", () => {
        let unirPecasCommand : UnirPecasCommand = new UnirPecasCommand();
        let pecasOrganizadas = [41,42];
        unirPecasCommand.processoId = 1;
        unirPecasCommand.pecas = pecasOrganizadas;
        $httpBackend.expectPOST(properties.apiUrl + '/distribuicao/api/organizacao-pecas/unir', unirPecasCommand).respond(200,"");
        organizaPecasService.unirPecas(unirPecasCommand).then(handler.success, handler.error);
        $httpBackend.flush();
        expect(handler.success).toHaveBeenCalled();
        expect(handler.error).not.toHaveBeenCalled();
    });
    
    it("deveria chamar o método de juntar peças", () => {
        let juntarPecaCommand : JuntarPecaCommand = new JuntarPecaCommand();
        let pecasOrganizadas = [41,42];
        juntarPecaCommand.processoId = 1;
        juntarPecaCommand.pecas = pecasOrganizadas;
        $httpBackend.expectPOST(properties.apiUrl + '/distribuicao/api/organizacao-pecas/juntar', juntarPecaCommand).respond(200,"");
        organizaPecasService.juntarPecas(juntarPecaCommand).then(handler.success, handler.error);
        $httpBackend.flush();
        expect(handler.success).toHaveBeenCalled();
        expect(handler.error).not.toHaveBeenCalled();
    });
    
    it("deveria chamar o método de editar uma peça", () => {
        let peca : Peca = new Peca();
        let processoId : number = 1;
        let editarPecaCommand : EditarPecaCommand = new EditarPecaCommand(peca, processoId);
        $httpBackend.expectPOST(properties.apiUrl + '/distribuicao/api/organizacao-pecas/editar', editarPecaCommand).respond(200,"");
        organizaPecasService.editarPeca(editarPecaCommand).then(handler.success, handler.error);
        $httpBackend.flush();
        expect(handler.success).toHaveBeenCalled();
        expect(handler.error).not.toHaveBeenCalled();
    });
    
    it("deveria chamar o método de dividir uma peça", () => {
        let pecaOriginalId : number = 1;
        let processoId : number = 1;
        let dividirPecaCommand : DividirPecaCommand = new DividirPecaCommand(pecaOriginalId, processoId);
        $httpBackend.expectPOST(properties.apiUrl + '/distribuicao/api/organizacao-pecas/dividir', dividirPecaCommand).respond(200,"");
        organizaPecasService.dividirPeca(dividirPecaCommand).then(handler.success, handler.error);
        $httpBackend.flush();
        expect(handler.success).toHaveBeenCalled();
        expect(handler.error).not.toHaveBeenCalled();
    });
    
    it("deveria chamar o método de inserir uma peça", () => {
        let processoId : number = 1;
        let inserirPecaCommand : InserirPecaCommand = new InserirPecaCommand(processoId);
        $httpBackend.expectPOST(properties.apiUrl + '/distribuicao/api/organizacao-pecas/inserir', inserirPecaCommand).respond(200,"");
        organizaPecasService.inserirPecas(inserirPecaCommand).then(handler.success, handler.error);
        $httpBackend.flush();
        expect(handler.success).toHaveBeenCalled();
        expect(handler.error).not.toHaveBeenCalled();
    });
    
});