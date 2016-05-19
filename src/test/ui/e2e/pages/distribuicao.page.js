"use strict";
var DistribuicaoProcessoPage = (function () {
    function DistribuicaoProcessoPage() {
    }
    DistribuicaoProcessoPage.prototype.selecionarTipoDistribuicao = function (tipo) {
        element(by.id("cboTipoDistribuicao")).click();
        element.all(by.repeater("tipoDistribuicao in vm.tiposDistribuicao")).filter(function (elemento, indice) {
            return elemento.getText().then(function (texto) {
                return texto.trim().toUpperCase() === tipo.trim().toUpperCase();
            });
        }).then(function (elementosFiltrados) {
            elementosFiltrados[0].click();
        });
    };
    DistribuicaoProcessoPage.prototype.selecionarMinistroPrevento = function (nome) {
        element.all(by.options("ministro.nome for ministro in vm.ministrosCandidatos")).filter(function (elemento, indice) {
            return elemento.getText().then(function (texto) {
                return texto.trim().toUpperCase() === nome.trim().toUpperCase();
            });
        }).then(function (elementosFiltrados) {
            elementosFiltrados[0].click();
        });
        element(by.id("btnAdicionarMinistro")).click();
    };
    DistribuicaoProcessoPage.prototype.informarProcessoPrevento = function (numeroProcesso) {
        element(by.id("txtNumProcessoPrevencao")).sendKeys(numeroProcesso, protractor.Key.ENTER);
    };
    DistribuicaoProcessoPage.prototype.informarJustificativa = function (descricao) {
        element(by.id("txtJustificativa")).sendKeys(descricao);
    };
    DistribuicaoProcessoPage.prototype.distribuirProcesso = function () {
        element(by.id("btnDistribuirProcesso")).click();
    };
    return DistribuicaoProcessoPage;
}());
exports.DistribuicaoProcessoPage = DistribuicaoProcessoPage;
