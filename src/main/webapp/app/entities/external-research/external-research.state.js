(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('external-research', {
            parent: 'entity',
            url: '/external-research?page&sort',
            data: {
                authorities: ['User'],
                pageTitle: 'External Research'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/external-research/external-research.html',
                    controller: 'ExternalResearchAnalystController',
                    controllerAs: 'vm'
                }
            },

            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                }
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort)
                    };
                }]
            }
        })
        .state('external-research-detail', {
            parent: 'external-research',
            url: '/{id}',
            data: {
                authorities: ['User'],
                pageTitle: 'External Research'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/external-research/external-research-detail.html',
                    controller: 'ExternalResearchDetailController',
                    controllerAs: 'vm'
                }
            }        
         
        })
        .state('external-research-detail.edit', {
            parent: 'external-research-detail',
            url: '/detail/edit',
            data: {
                authorities: ['User']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/external-research/external-research-dialog.html',
                    controller: 'ExternalResearchDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 't',
                    resolve: {
                        entity: ['ExternalResearchAnalyst', function(ExternalResearchAnalyst) {
                            return ExternalResearchAnalyst.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('external-research.new', {
            parent: 'external-research',
            url: '/external-research/new',
            data: {
                authorities: ['User']
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/external-research/external-research-dialog.html',
                    controller: 'ExternalResearchDialogController',
                    controllerAs: 'vm'
                }
            }            ,
            resolve: {
                entity: function () {
                    return {
                        name: null,
                        sectorType: null,
                        createdBy: null,
                        updatedBy: null,
                        createdDate: null,
                        updatedDate: null,
                        id: null
                    };
                }
            }
        })
        .state('external-research.edit', {
            parent: 'external-research',
            url: '/{id}/edit',
            data: {
                authorities: ['User']
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/external-research/external-research-dialog.html',
                    controller: 'ExternalResearchDialogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ExternalResearchAnalyst', function($stateParams, ExternalResearchAnalyst) {
                    return ExternalResearchAnalyst.get({id : $stateParams.id}).$promise;
                }]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                var vm = this;
                vm.readOnly = true;
            }]
        })
       
    }

})();
