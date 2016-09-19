import ITranslatePartialLoaderService = angular.translate.ITranslatePartialLoaderService;
import IStateProvider = angular.ui.IStateProvider;
import IModule = angular.IModule;
import Properties = app.support.constants.Properties;
import IStateParamsService = angular.ui.IStateParamsService;
import {OrganizaPecasService} from "./organiza-pecas.service";

/*
 * Modulo de configuração do serviço de oorganização de peças
 * viniciusk
 * since: 17/08/2016.
*/

/** @ngInject **/
function config($stateProvider: IStateProvider,
                properties: Properties) {
    
    $stateProvider.state('app.tarefas.organizacao-pecas', {
        url : '/distribuicao/organizacao-pecas/:informationId',
        views : {
            'content@app.autenticado' : {
                templateUrl : "organiza-pecas.tpl.html",
                controller : 'app.distribuicao.organizacao-pecas.OrganizaPecasController',
                controllerAs: 'organiza'
            }
        },
        resolve : { 
            processo: ['app.distribuicao.organizacao-pecas.OrganizaPecasService', '$stateParams', (organizaPecasService: OrganizaPecasService,  $stateParams : IStateParamsService) => {
            	let distribuicaoId = $stateParams['informationId'];
                return organizaPecasService.consultarProcesso(distribuicaoId);
            }]
        },
        params : {
            informationId : undefined
        }
        
    }).state('app.tarefas.organizacao-pecas.excluir-pecas', {
        url: '/excluir-pecas',
        views: {
            '@app.tarefas.organizacao-pecas': {
                controller: /** @ngInject **/($mdDialog, $stateParams) => {
                    $mdDialog.show({
                        controller: 'app.distribuicao.organizacao-pecas.ExcluirPecasController',
                        controllerAs: 'vm',
                        templateUrl: 'excluir-pecas.tpl.html',
                        parent: angular.element(document.body),
                        clickOutsideToClose: false,
                        locals: {
                            pecas : $stateParams['targets']
                        }
                    });
                }
            }
        },
        params : {
            informationId : undefined,
            targets: []
        }
    }).state('app.tarefas.organizacao-pecas.juntar-pecas', {
        url: '/juntar-pecas',
        views: {
            '@app.tarefas.organizacao-pecas': {
                controller: /** @ngInject **/($mdDialog, $stateParams) => {
                    $mdDialog.show({
                        controller: 'app.distribuicao.organizacao-pecas.JuntarPecasController',
                        controllerAs: 'vm',
                        templateUrl: 'juntar-pecas.tpl.html',
                        parent: angular.element(document.body),
                        clickOutsideToClose: false,
                        locals: {
                            pecas : $stateParams['targets']
                        }
                    });
                }
            }
        },
        params : {
            informationId : undefined,
            targets: []
        }
    }).state('app.tarefas.organizacao-pecas.editar-peca', {
        url: '/editar-peca',
        views: {
            '@app.tarefas.organizacao-pecas': {
                controller: /** @ngInject **/($mdDialog, $stateParams, $mdMedia, tipoPecas, visibilidades) => {
                    var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'))  && false;
                    $mdDialog.show({
                        controller: 'app.distribuicao.organizacao-pecas.EditarPecaController',
                        controllerAs: 'vm',
                        templateUrl: 'editar-peca.tpl.html',
                        parent: angular.element(document.body),
                        clickOutsideToClose: false,
                        fullscreen: false,
                        locals: {
                            peca : $stateParams['target'].peca,
                            processoId : $stateParams['target'].processoId,
                            tipoPecas: tipoPecas,
                            visibilidades : visibilidades
                        }
                    });
                }
            }
        },
        resolve : {
            tipoPecas : ['app.distribuicao.organizacao-pecas.OrganizaPecasService', (organizaPecasService: OrganizaPecasService) => {
                return organizaPecasService.listarTipoPecas();
            }],
            visibilidades : ['app.distribuicao.organizacao-pecas.OrganizaPecasService', (organizaPecasService: OrganizaPecasService) => {
                return organizaPecasService.listarVisibilidade();
            }]
        },
        params : {
            informationId : undefined,
            target: undefined
        }
    }).state('app.tarefas.organizacao-pecas.dividir-peca', {
        url: '/dividir-peca',
        views: {
            '@app.tarefas.organizacao-pecas': {
                controller: /** @ngInject **/($mdDialog, $stateParams, $mdMedia, tipoPecas, visibilidades) => {
                    var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'))  && false;
                    $mdDialog.show({
                        controller: 'app.distribuicao.organizacao-pecas.DividirPecaController',
                        controllerAs: 'vm',
                        templateUrl: 'dividir-peca.tpl.html',
                        parent: angular.element(document.body),
                        clickOutsideToClose: false,
                        fullscreen: false,
                        locals: {
                            peca : $stateParams['target'].peca,
                            processoId : $stateParams['target'].processoId,
                            tipoPecas: tipoPecas,
                            visibilidades : visibilidades
                        }
                    });
                }
            }
        },
        resolve : {
            tipoPecas : ['app.distribuicao.organizacao-pecas.OrganizaPecasService', (organizaPecasService: OrganizaPecasService) => {
                return organizaPecasService.listarTipoPecas();
            }],
            visibilidades : ['app.distribuicao.organizacao-pecas.OrganizaPecasService', (organizaPecasService: OrganizaPecasService) => {
                return organizaPecasService.listarVisibilidade();
            }]
        },
        params : {
            informationId : undefined,
            target: undefined
        }
    }).state('app.tarefas.organizacao-pecas.inserir-pecas', {
        url: '/inserir-pecas',
        views: {
            '@app.tarefas.organizacao-pecas': {
                controller: /** @ngInject **/($mdDialog, $stateParams, $mdMedia, tipoPecas, visibilidades, processo) => {
                    var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'))  && false;
                    $mdDialog.show({
                        controller: 'app.distribuicao.organizacao-pecas.InserirPecasController',
                        controllerAs: 'vm',
                        templateUrl: 'inserir-pecas.tpl.html',
                        parent: angular.element(document.body),
                        clickOutsideToClose: false,
                        fullscreen: false,
                        locals: {
                        //    peca : $stateParams['target'].peca,
                        //    processoId : $stateParams['target'].processoId,
                            tipoPecas: tipoPecas,
                            visibilidades : visibilidades,
                            processo : processo
                        }
                    });
                }
            }
        },
        resolve : {
            tipoPecas : ['app.distribuicao.organizacao-pecas.OrganizaPecasService', (organizaPecasService: OrganizaPecasService) => {
                return organizaPecasService.listarTipoPecas();
            }],
            visibilidades : ['app.distribuicao.organizacao-pecas.OrganizaPecasService', (organizaPecasService: OrganizaPecasService) => {
                return organizaPecasService.listarVisibilidade();
            }],
            processo: ['app.distribuicao.organizacao-pecas.OrganizaPecasService', '$stateParams', (organizaPecasService: OrganizaPecasService,  $stateParams : IStateParamsService) => {
                let distribuicaoId = $stateParams['informationId'];
                return organizaPecasService.consultarProcesso(distribuicaoId);
            }]
        },
        params : {
            informationId : undefined,
            target: undefined
        }
    });

}

/** @ngInject **/
function run($translatePartialLoader: ITranslatePartialLoaderService, properties: Properties) {
    $translatePartialLoader.addPart(properties.apiUrl + '/distribuicao/organizacao-pecas');
}

let pecas: IModule = angular.module('app.distribuicao.organizacao-pecas', ['app.support', 'ui.sortable', 'angularFileUpload', 'ngCookies', 'checklist-model']);
pecas.config(config);
pecas.run(run);

export default pecas;