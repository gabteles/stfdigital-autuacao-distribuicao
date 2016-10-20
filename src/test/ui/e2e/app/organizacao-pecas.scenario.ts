import {LoginPage} from "./shared/pages/login.page";
import {PrincipalPage}  from "./shared/pages/principal.page";
import {OrganizaPecasPage} from "./pages/organizaPeca.page";

describe("Organizar Peças", () => {
    let loginPage: LoginPage = new LoginPage();
    let principalPage: PrincipalPage = new PrincipalPage();
    let organizaPage: OrganizaPecasPage = new OrganizaPecasPage();
    
    it ('Deveria logar no sistema', () => {
        loginPage.open();
        loginPage.login('organizador-pecas', '123');
    });

    it ('Deveria acessar a tarefa de organizar peças', () => {
        principalPage.acessarTarefa('Organizar Peças', 9004);
    });
    
    it ('Deveria acessar a tarefa de inserir peca', () => {
        organizaPage.selecionarAcao('Inserir peças');
        organizaPage.aguardarModal('Inserir Peca');
    });
    
    it ('Deveria fazer o upload de dois arquivos', () => {
        organizaPage.uploadAnexo();
        organizaPage.uploadAnexo();
        organizaPage.aguardarUploadConcluido(0);
        organizaPage.aguardarUploadConcluido(1);
        organizaPage.selecionarTipoPeca(0, "Movimento Processual");
        organizaPage.selecionarTipoPeca(1, "Manifestação da AGU");
    });
    
    it ('Deveria excluir todos os anexos', () => {
        organizaPage.excluirAnexos();
    });
    
    it ('Deveria fazer upload de um novo arquivo', () => {
        organizaPage.uploadAnexo();
        organizaPage.aguardarUploadConcluido(0);
        organizaPage.selecionarTipoPeca(0, "Movimento Processual");
    });

    it('Deveria executar a ação de inserir uma peça', () => {
        let quantidadeInicialPromise = organizaPage.recuperaTotalDePecas();
        quantidadeInicialPromise.then((quantidade) => {
            browser.sleep(10000);
            organizaPage.inserir();
            browser.sleep(10000);
            let quantidadeFinalPromise = organizaPage.recuperaTotalDePecas();
            expect(quantidadeFinalPromise).toBeGreaterThan(quantidade, 'A quantitidade depois da inserção deveria ser maior que a quantidade inicial');
        });
        
    });
        

    it ("Deveria juntar a peça", () => {
        organizaPage.selecionarUltimaPeca();
        organizaPage.selecionarAcao("Juntar peças");
        organizaPage.juntar();
        organizaPage.recuperaTotalDePecas().then((quantidade) =>{
            expect(organizaPage.recuperarSituacaoPeca(quantidade - 1)).toEqual("JUNTADA", "A última peça deveria estar com a situação de JUNTADA");
        });
    });
    
    it ("Deveria editar as informações da peça", () => {
        organizaPage.selecionarPeca(1);
        organizaPage.selecionarAcao("Editar peça");
        organizaPage.alterarTipoPeca("Informação");
        organizaPage.editar();
    });
    
    it ("Deveria dividir uma peça" , () => {
        let quantidadeInicialPromise = organizaPage.recuperaTotalDePecas();
        organizaPage.selecionarUltimaPeca();
        organizaPage.selecionarAcao("Dividir peça");
        organizaPage.alterarTipoPeca("Informação");
        organizaPage.preencherDescricao("Teste1");
        organizaPage.preencherNumeroPagina("txtPaginaInicial", 1);
        organizaPage.preencherNumeroPagina("txtPaginaFinal", 2);
        organizaPage.adicionarPecaParticionada();
        organizaPage.alterarTipoPeca("Petição inicial");
        organizaPage.preencherDescricao("Teste2");
        organizaPage.preencherNumeroPagina("txtPaginaInicial", 3);
        organizaPage.preencherNumeroPagina("txtPaginaFinal", 5);
        organizaPage.adicionarPecaParticionada();
        organizaPage.dividir();
        let quantidadeFinalPromise = organizaPage.recuperaTotalDePecas();
        quantidadeInicialPromise.then((quantidadeInicial) =>{
            expect(quantidadeFinalPromise).toBeGreaterThan(quantidadeInicial, 'A quantitidade depois da divisão deveria ser maior que a quantidade inicial');
        })
    });
    
    it("Deveria unir peças", () => {
       browser.sleep(3000);
       organizaPage.selecionarPeca(2);
       organizaPage.selecionarUltimaPeca();
       organizaPage.selecionarAcao("Unir peças");
       organizaPage.unir();
    });
    
    it ("Deveria excluir a peça", () => {
        organizaPage.selecionarUltimaPeca();
        organizaPage.selecionarAcao("Excluir peças");
        organizaPage.excluir();
        organizaPage.recuperaTotalDePecas().then((quantidade) => {
            expect(organizaPage.recuperarSituacaoPeca(quantidade - 1)).toEqual("EXCLUIDA", "A última peça deveria estar com o status de excluída");
        });
    });
    
    
    
});