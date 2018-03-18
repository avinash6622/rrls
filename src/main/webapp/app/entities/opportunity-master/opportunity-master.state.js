(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('opportunity-master', {
            parent: 'entity',
            url: '/opportunity-master',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Opportunity'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/opportunity-master/opportunity-masters.html',
                    controller: 'OpportunityMasterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('opportunity-master-detail', {
            parent: 'opportunity-master',
            url: '/opportunity-master/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Opportunity Master'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/opportunity-master/opportunity-master-detail.html',
                    controller: 'OpportunityMasterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'OpportunityMaster', function($stateParams, OpportunityMaster) {
                    return OpportunityMaster.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'opportunity-master',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('opportunity-master-detail.edit', {
            parent: 'opportunity-master-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/opportunity-master/opportunity-master-dialog.html',
                    controller: 'OpportunityMasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OpportunityMaster', function(OpportunityMaster) {
                            return OpportunityMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('opportunity-master.new', {
            parent: 'opportunity-master',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/opportunity-master/opportunity-master-dialog.html',
                    controller: 'OpportunityMasterDialogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: function () {
                    return {
                        oppDescription: null,
                        createdBy: null,
                        updatedBy: null,
                        createdDate: null,
                        updatedDate: null,
                        id: null
                    };
                }
            }
            /*onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/opportunity-master/opportunity-master-dialog.html',
                    controller: 'OpportunityMasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                oppCode: null,
                                strategyId: null,
                                oppName: null,
                                oppDescription: null,
                                createdBy: null,
                                updatedBy: null,
                                createdDate: null,
                                updatedDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('opportunity-master', null, { reload: 'opportunity-master' });
                }, function() {
                    $state.go('opportunity-master');
                });
            }]*/
        })
        .state('opportunity-master.edit', {
            parent: 'opportunity-master',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/opportunity-master/opportunity-master-dialog.html',
                    controller: 'OpportunityMasterDialogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'OpportunityMaster', function($stateParams, OpportunityMaster) {
                    return OpportunityMaster.get({id : $stateParams.id}).$promise;
                }]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                var vm = this;
                vm.readOnly = true;
            }]
            /*onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/opportunity-master/opportunity-master-dialog.html',
                    controller: 'OpportunityMasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OpportunityMaster', function(OpportunityMaster) {
                            return OpportunityMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('opportunity-master', null, { reload: 'opportunity-master' });
                }, function() {
                    $state.go('^');
                });
            }]*/
        })
        .state('opportunity-master.delete', {
            parent: 'opportunity-master',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/opportunity-master/opportunity-master-delete-dialog.html',
                    controller: 'OpportunityMasterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['OpportunityMaster', function(OpportunityMaster) {
                            return OpportunityMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('opportunity-master', null, { reload: 'opportunity-master' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
