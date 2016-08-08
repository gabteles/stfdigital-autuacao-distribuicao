import ITranslatePartialLoaderService = angular.translate.ITranslatePartialLoaderService;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;
import Properties = app.support.constants.Properties;
import IStateParamsService = angular.ui.IStateParamsService;
import {DistribuicaoService} from "./distribuicao.service";
import {DistribuicaoCommonService} from "./commons/distribuicao-common.service";

/*
 * Modulo de configuração do serviço de distribuição de processos.
 * autor: anderson.araujo
 * since: 12/05/2016.
*/

/** @ngInject **/
function config($stateProvider: IStateProvider,
                msNavigationServiceProvider: any,
                properties: Properties) {
	
    $stateProvider.state('app.tarefas.distribuicao', {
        url : '/distribuicao',
        views : {
            'content@app.autenticado' : {
                templateUrl : "distribuicao.tpl.html",
                controller : 'app.distribuicao.DistribuicaoController',
                controllerAs: 'vm'
            }
        },
        resolve : { 
            tiposDistribuicao: ['app.distribuicao.DistribuicaoService', (distribuicaoService: DistribuicaoService) => {
                return distribuicaoService.listarTiposDistribuicao();
            }]
        },
        params : {
            informationId : undefined
        }
    }).state('app.tarefas.distribuicao-comum', {
        parent: 'app.tarefas.distribuicao',
        url: '/comum',
        views: {
            '@app.tarefas.distribuicao': {
                templateUrl: 'comum/distribuicao-comum.tpl.html',
                controller: 'app.distribuicao.DistribuicaoComumController',
                controllerAs: 'vm'	
            }
        },
        resolve: {
        	 ministrosCandidatos: ['app.distribuicao.DistribuicaoCommonService', (distribuicaoCommonService: DistribuicaoCommonService) => {
                 return distribuicaoCommonService.listarMinistros();
             }]
        }
    }).state('app.tarefas.distribuicao-prevencao', {
        parent: 'app.tarefas.distribuicao',
        url: '/prevencao',
        views: {
            '@app.tarefas.distribuicao': {
                templateUrl: 'prevencao/distribuicao-prevencao.tpl.html',
                controller: 'app.distribuicao.DistribuicaoPrevencaoController',
                controllerAs: 'vm'
            }
        },
/*        resolve: {
            processo: ['app.distribuicao.DistribuicaoCommonService', '$stateParams', (distribuicaoCommonService: DistribuicaoCommonService, $stateParams : IStateParamsService) => {
            	let distribuicaoId = $stateParams['informationId'];
                return distribuicaoCommonService.consultarProcessoPelaDistribuicao(distribuicaoId);
            }]
       }, */ 
       params : {
           informationId : undefined
       }
    });
    
    console.log(properties.apiUrl);
    
    msNavigationServiceProvider.saveItem('distribuicao', {
        title : 'Distribuição de Processos',
        icon : 'icon-magnify',
        state : 'app.distribuicao',
        translation : 'DISTRIBUICAO.NOVA',
        weight : 1
    });
}

/** @ngInject **/
function run($translatePartialLoader: ITranslatePartialLoaderService, properties: Properties) {
    $translatePartialLoader.addPart(properties.apiUrl + '/distribuicao/distribuicao');
}

let distribuicao: IModule = angular.module('app.distribuicao.distribuicao', ['app.support', 'ngCookies']);
distribuicao.config(config);
distribuicao.run(run);

export default distribuicao;