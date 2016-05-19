"use strict";
var login_page_1 = require("./pages/login.page");
var principal_page_1 = require("./pages/principal.page");
var distribuicao_page_1 = require("./pages/distribuicao.page");
describe("Distribuição de Processos", function () {
    var paginaLogin = new login_page_1.LoginPage();
    var paginaPrincipal = new principal_page_1.PrincipalPage();
    var paginaDistribuicaoProcesso = new distribuicao_page_1.DistribuicaoProcessoPage();
    it("Deveria logar na aplicação.", function () {
        paginaLogin.open();
        paginaLogin.setCredenciais("kakaroto", "kamehameha");
    });
    it("Deveria acessar a pagina de distribuição de processos.", function () {
        paginaPrincipal.iniciarProcesso();
        paginaPrincipal.iniciarDistribuicaoProcesso();
        browser.sleep(3000);
    });
    it("Deveria preencher os dados para uma distribuição do tipo COMUM.", function () {
        paginaDistribuicaoProcesso.selecionarTipoDistribuicao("Comum");
        browser.sleep(1500);
        paginaDistribuicaoProcesso.selecionarMinistroPrevento("MINISTRO PRESIDENTE");
        browser.sleep(1500);
        paginaDistribuicaoProcesso.informarJustificativa("Teste da funcionalidade distribuição de processo do tipo comum.");
        browser.sleep(1500);
        paginaDistribuicaoProcesso.distribuirProcesso();
        browser.sleep(3000);
        expect(browser.getCurrentUrl()).toMatch(/\/minhas-tarefas/);
    });
});
