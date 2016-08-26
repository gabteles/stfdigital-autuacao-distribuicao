import ITranslatePartialLoaderService = angular.translate.ITranslatePartialLoaderService;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;
import Properties = app.support.constants.Properties;
import IStateParamsService = angular.ui.IStateParamsService;
import {OrganizaPecasService} from "./organiza-pecas.service";
import {} from "./shared/pecas.service";

/*
 * Modulo de configuração do serviço de oorganização de peças
 * viniciusk
 * since: 17/08/2016.
*/

/** @ngInject **/
function config($stateProvider: IStateProvider,
                properties: Properties) {
    
    $stateProvider.state('app.tarefas.distribuicao', {
        url : '/pecas',
        views : {
            'content@app.autenticado' : {
                templateUrl : "organiza-pecas.tpl.html",
                controller : 'app.distribuicao.OrganizaPecasController',
                controllerAs: 'vm'
            }
        },
        resolve : { 
            processo: ['app.distribuicao.PecasService', '$stateParams', (organizaPecasService: OrganizaPecasService,  $stateParams : IStateParamsService) => {
            	let distribuicaoId = $stateParams['informationId'];
                return organizaPecasService.consultarProcesso(distribuicaoId);
            }]
        },
        params : {
            informationId : undefined
        }
    });
}

/** @ngInject **/
function run($translatePartialLoader: ITranslatePartialLoaderService, properties: Properties) {
    $translatePartialLoader.addPart(properties.apiUrl + '/distribuicao/pecas');
}

let pecas: IModule = angular.module('app.distribuicao.pecas', ['app.support', 'ui.sortable', 'ngCookies']);
pecas.config(config);
pecas.run(run);

export default pecas;