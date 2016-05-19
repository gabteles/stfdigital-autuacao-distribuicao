/*
 * @autor: anderson.araujo
 * @since: 19/05/2016
*/
import ElementFinder = protractor.ElementFinder;

export class DistribuicaoProcessoPage {
    
    public selecionarTipoDistribuicao(tipo: string): void {
        element(by.id("cboTipoDistribuicao")).click();        
        element.all(by.repeater("tipoDistribuicao in vm.tiposDistribuicao")).filter(function(elemento, indice) {
           return elemento.getText().then(function(texto){
               return texto.trim().toUpperCase() === tipo.trim().toUpperCase();
           });
        }).then(function(elementosFiltrados){
            elementosFiltrados[0].click();
        });
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
    
    public informarProcessoPrevento(numeroProcesso: string): void {
        element(by.id("txtNumProcessoPrevencao")).sendKeys(numeroProcesso, protractor.Key.ENTER);
    }
    
    public informarJustificativa(descricao: string): void {
        element(by.id("txtJustificativa")).sendKeys(descricao);
    }
    
    public distribuirProcesso(): void {
        element(by.id("btnDistribuirProcesso")).click();
    }
}