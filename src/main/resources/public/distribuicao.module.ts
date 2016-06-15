/*
 * Modulo de configuração do serviço de distribuição de processos.
 * autor: anderson.araujo
 * since: 12/05/2016.
*/

import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;

/** @ngInject **/
function config($translatePartialLoaderProvider: ITranslatePartialLoaderProvider,
                $stateProvider: IStateProvider,
                msNavigationServiceProvider: any,
                properties) {

    //mecanismo de globalização da aplicação.
    $translatePartialLoaderProvider.addPart(properties.apiUrl + "/i18n");

    /*
     * properties.apiUrl: retorna o endereço do contexto da aplicação acessado via gateway.
     * No caso do serviço de distribuição, o endereço é protocolo://docker:porta/distribuicao.
     * Assim, a localização dos dos arquivos do projeto se dá a partir desse endereço.
    */
    $stateProvider.state('app.novo-processo.distribuicao', {
        url : '',
        views : {
            'content@app.autenticado' : {
                templateUrl : properties.apiUrl + "/distribuicao/distribuicao.tpl.html",
                controller : 'app.novo-processo.distribuicao.DistribuicaoController',
                controllerAs: 'vm'
            }
        }
    });
    
    console.log(properties.apiUrl);
    
    msNavigationServiceProvider.saveItem('distribuicao', {
        title : 'Distribuição de Processos',
        icon : 'icon-magnify',
        state : 'app.novo-processo.distribuicao',
        translation : 'DISTRIBUICAO.NOVA',
        weight : 1
    });
}

let distribuicao: IModule = angular.module('app.novo-processo.distribuicao', ['app.novo-processo', 'app.support', 'ngCookies']);
distribuicao.config(config);

export default distribuicao;