/*
 * @autor: anderson.araujo
 * @since: 19/05/2016
*/

import ElementFinder = protractor.ElementFinder;

export class PrincipalPage {
    
    private linkIniciarProcesso: ElementFinder = element.all(by.css('a[ui-sref="app.novo-processo"]')).get(0);
	private linkNovaDistribuicao: ElementFinder = element(by.css('div[ui-sref="app.novo-processo.distribuicao"]'));
    
    public iniciarProcesso() : void {
        this.linkIniciarProcesso.click();
    }
    
    public iniciarDistribuicaoProcesso() : void {
    	this.linkNovaDistribuicao.click();
    }
}