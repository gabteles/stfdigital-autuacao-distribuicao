"use strict";
var PrincipalPage = (function () {
    function PrincipalPage() {
        this.linkIniciarProcesso = element.all(by.css('a[ui-sref="app.novo-processo"]')).get(0);
        this.linkNovaDistribuicao = element(by.css('div[ui-sref="app.novo-processo.distribuicao"]'));
    }
    PrincipalPage.prototype.iniciarProcesso = function () {
        this.linkIniciarProcesso.click();
    };
    PrincipalPage.prototype.iniciarDistribuicaoProcesso = function () {
        this.linkNovaDistribuicao.click();
    };
    return PrincipalPage;
}());
exports.PrincipalPage = PrincipalPage;
