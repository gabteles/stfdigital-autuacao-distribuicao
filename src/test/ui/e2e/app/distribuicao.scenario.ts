/*
 * Teste E2E da funcionalidade de distribuição de processos.
 * @autor: anderson.araujo
 * @since: 19/05/2016.
*/

import {LoginPage} from "./pages/login.page";
import {PrincipalPage} from "./pages/principal.page";
import {DistribuicaoProcessoPage} from "./pages/distribuicao.page";

describe("Distribuição de Processos", () => {
    let paginaLogin: LoginPage = new LoginPage();
    let paginaPrincipal: PrincipalPage = new PrincipalPage();
    let paginaDistribuicaoProcesso: DistribuicaoProcessoPage = new DistribuicaoProcessoPage();
    
    it("Deveria logar na aplicação.", () => {
       paginaLogin.open();
       paginaLogin.setCredenciais("distribuidor", "123"); 
    });
    
    xit("Deveria acessar a pagina de distribuição de processos.", () => {
        paginaPrincipal.iniciarProcesso();
        paginaPrincipal.iniciarDistribuicaoProcesso();
        browser.sleep(3000);
    });
    
    xit("Deveria preencher os dados para uma distribuição do tipo COMUM.", () => {
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
    /* O teste abaixo está funcional. Ele deve ser descomentado quando o mecanismo de ações for implementado.
     * Lembrando que, até o momento, o código do processo bem como o id da distrinuição estão fixos no código.
     */
    /*
    it("Deveria acessar a pagina de distribuição de processos novamente.", () => {
        paginaPrincipal.iniciarProcesso();
        paginaPrincipal.iniciarDistribuicaoProcesso();
        browser.sleep(3000);
    });
    
    it("Deveria preencher os dados para uma distribuição do tipo PREVENCAO.", () => {        
        paginaDistribuicaoProcesso.selecionarTipoDistribuicao("Prevenção Relator/Sucessor");
        browser.sleep(1500);
        paginaDistribuicaoProcesso.informarProcessoPrevento("ADI 123/2016");
        browser.sleep(2000);
        paginaDistribuicaoProcesso.informarJustificativa("Teste da funcionalidade distribuição de processo do tipo prevenção.");
        browser.sleep(1500);
        paginaDistribuicaoProcesso.distribuirProcesso();
        browser.sleep(3000);
        expect(browser.getCurrentUrl()).toMatch(/\/minhas-tarefas/);
    });*/
});