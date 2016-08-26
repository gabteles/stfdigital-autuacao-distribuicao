import ElementFinder = protractor.ElementFinder;

import mdHelpers = require('../shared/helpers/md-helpers');

export class DistribuicaoProcessoPage {
    
    public selecionarComboTipoDistribuicao(tipo: string): void {
        mdHelpers.mdSelect.selectOptionWithText(element(by.id('cboTipoDistribuicao')), tipo);
    }
    
    public selecionarMinistroPrevento(nome:string): void {
        element.all(by.options("ministro.nome for ministro in vm.ministrosCandidatos")).filter(function(elemento, indice) {
           return elemento.getText().then(function(texto){
               return texto.trim().toUpperCase() === nome.trim().toUpperCase();
           });
        }).then(function(elementosFiltrados){
            elementosFiltrados[0].click();
        });
        
        element(by.id("btnAdicionarMinistro")).click();
    }
    
    public selecionarProcessoPrevento(numeroProcesso: string): void {
        element(by.id("txtNumProcessoPrevencao")).sendKeys(numeroProcesso, protractor.Key.ENTER);
    }
    
    public informarJustificativa(descricao: string): void {
        element(by.id("txtJustificativa")).sendKeys(descricao);
    }
    
    public distribuirProcesso(): void {
        element(by.id("distribuir-processo")).click();
    }
}