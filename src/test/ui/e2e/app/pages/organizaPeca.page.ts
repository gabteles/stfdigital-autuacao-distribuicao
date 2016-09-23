import ElementFinder = protractor.ElementFinder;

import path = require("path");
import mdHelpers = require('../shared/helpers/md-helpers');
import support = require('../shared/helpers/support');

export class OrganizaPecasPage {
    
    private caminhoArquivo = browser.params.filesDirPath ? browser.params.filesDirPath : (__dirname + '/../../files');
    
    public selecionarAcao(acao : string) : void {
        
        let botaoAcoes = element(by.css("commands[target-type='Peca']")).element(by.css('button[aria-label="Comandos"]'));
        botaoAcoes.click();
        browser.wait(() => {
            return botaoAcoes.getAttribute('aria-owns').then((selectContainerId) => {
                return element(by.id(selectContainerId)).isDisplayed();
            });
        });
        
        botaoAcoes.getAttribute('aria-owns').then((selectContainerId) => {
            element(by.id(selectContainerId)).all(by.css('button[aria-label="'+acao+'"]')).get(0).click();
        });
        
    }
    
    public aguardarModal(nomeModal : string) : void {
        let painel = element(by.css('md-dialog[aria-label="'+nomeModal+'"]'));
        browser.wait(() => {
            return painel.isDisplayed();
        });
    }
    
    public scrollToBottom(): void {
        support.scrollToElement(element(by.css('button[command]')));
    }
    
    public uploadAnexo(): void {
      //  this.scrollToBottom();
        let nomeArquivo = 'pdf-de-teste-assinado-02.pdf';
        let caminhoAbsoluto =  path.resolve(this.caminhoArquivo, nomeArquivo);
        let fileUpload = element(by.css('input[type="file"]'));

        fileUpload.sendKeys(caminhoAbsoluto);
        browser.waitForAngular();
    }
    
    public aguardarUploadConcluido(index: number, timeout: number = 3000) {
        let uploadedRow = element.all(by.css('.linha-anexo')).get(index);
        browser.wait(uploadedRow.element(by.css('td.coluna-progresso.upload-finished')).isPresent(), timeout);
    };

    public selecionarTipoPeca(indice: number, descricao: string) {
        let autocomplete = element(by.css(".coluna-tipo-anexo-" + indice)).element(by.css('md-autocomplete'));
        mdHelpers.mdAutocomplete.selectFirstOptionWithText(autocomplete, descricao);
    };
    
    public excluirAnexos(): void {
        let btnExcluirAnexos = element(by.id("btnRemoverAnexos")).click();
    };

    public selecionarUltimaPeca() : void {
        element.all(by.repeater('pecaSelecionavel in organiza.pecas')).count().then((count) => {
            this.selecionarPeca(count - 1);
        });
    };
    
    public alterarTipoPeca(tipo : string) : void {
        mdHelpers.mdSelect.selectOptionWithText(element(by.id('tipoPecaIdSelect')), tipo);
    }
    
    public selecionarPeca (indice : number) : void {
        let peca = element(by.repeater('pecaSelecionavel in organiza.pecas').row(indice));
        peca.element(by.css('input')).click();
        browser.waitForAngular();
    };
    
    public preencherDescricao(descricao : string) {
        element(by.id('txtDescricao')).sendKeys(descricao);
    };
    
    public preencherNumeroPagina(id : string, numeroPagina : number) : void {
        element(by.id(id)).sendKeys(numeroPagina.toString());
    }

    public adicionarPecaParticionada() : void {
        element(by.id('inserir-peca')).click();
    };
    
    public inserir() : void {
        element(by.id('btnInserir')).click();
    };
    
    public juntar() : void {
        element(by.id('btnJuntar')).click();
    };
    
    public editar() : void {
        element(by.id('btnEditar')).click();
    };
    
    public dividir() : void {
        element(by.id('btnDividir')).click();
    };
    
    public excluir() : void {
        element(by.id('btnExcluir')).click();
    };
    
    public unir() : void {
        element(by.id('btnUnir')).click();
    };
}