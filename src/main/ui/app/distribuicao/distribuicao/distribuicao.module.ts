import ITranslatePartialLoaderService = angular.translate.ITranslatePartialLoaderService;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;
import Properties = app.support.constants.Properties;

/*
 * Modulo de configuração do serviço de distribuição de processos.
 * autor: anderson.araujo
 * since: 12/05/2016.
*/

/** @ngInject **/
function config($stateProvider: IStateProvider,
                msNavigationServiceProvider: any,
                properties: Properties) {
	
    /*
     * properties.apiUrl: retorna o endereço do contexto da aplicação acessado via gateway.
     * No caso do serviço de distribuição, o endereço é protocolo://docker:porta/distribuicao.
     * Assim, a localização dos dos arquivos do projeto se dá a partir desse endereço.
    */
    $stateProvider.state('app.novo-processo.distribuicao', {
        url : '/distribuicao',
        views : {
            'content@app.autenticado' : {
                templateUrl : "distribuicao.tpl.html",
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

/** @ngInject **/
function run($translatePartialLoader: ITranslatePartialLoaderService, properties: Properties) {
    $translatePartialLoader.addPart(properties.apiUrl + '/distribuicao/distribuicao');
}

let distribuicao: IModule = angular.module('app.novo-processo.distribuicao', ['app.novo-processo', 'app.support', 'ngCookies']);
distribuicao.config(config);
distribuicao.run(run);

export default distribuicao;