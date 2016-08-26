import {LoginPage} from "./shared/pages/login.page";
import {PrincipalPage}  from "./shared/pages/principal.page";
import {DistribuicaoProcessoPage} from "./pages/distribuicao.page";

describe("Distribuição de Processos", () => {
    let loginPage: LoginPage = new LoginPage();
    let principalPage: PrincipalPage = new PrincipalPage();
    let distribuicaoPage: DistribuicaoProcessoPage = new DistribuicaoProcessoPage();
    
    it ('Deveria logar no sistema', () => {
        loginPage.open();
        loginPage.login('distribuidor', '123');
    });

    it ('Deveria acessar a tarefa de distribuir processo', () => {
        principalPage.acessarTarefa('Distribuir Processo', 9000);
    });
    
    
    it("Deveria selecionar o tipo de distribuição comum", () => {
        distribuicaoPage.selecionarComboTipoDistribuicao('COMUM')
    });
    
    xit("Deveria selecionar os ministros canditados e impedidos" , () => {
        distribuicaoPage.selecionarMinistroPrevento("MINISTRO PRESIDENTE");
        browser.sleep(1500);
        distribuicaoPage.informarJustificativa("Teste da funcionalidade distribuição de processo do tipo comum.");
        browser.sleep(1500);
        distribuicaoPage.distribuirProcesso();
        expect(browser.getCurrentUrl()).toMatch(/\/minhas-tarefas/);
    });
});