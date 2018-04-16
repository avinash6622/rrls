(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('role-master', {
            parent: 'entity',
            url: '/role-master',
            data: {
                authorities: ['User'],
                pageTitle: 'RoleMasters'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/role-master/role-masters.html',
                    controller: 'RoleMasterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('role-master-detail', {
            parent: 'role-master',
            url: '/role-master/{id}',
            data: {
                authorities: ['User'],
                pageTitle: 'RoleMaster'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/role-master/role-master-detail.html',
                    controller: 'RoleMasterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'RoleMaster', function($stateParams, RoleMaster) {
                    return RoleMaster.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'role-master',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('role-master-detail.edit', {
            parent: 'role-master-detail',
            url: '/detail/edit',
            data: {
                authorities: ['User']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/role-master/role-master-dialog.html',
                    controller: 'RoleMasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RoleMaster', function(RoleMaster) {
                            return RoleMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('role-master.new', {
            parent: 'role-master',
            url: '/new',
            data: {
                authorities: ['User']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/role-master/role-master-dialog.html',
                    controller: 'RoleMasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                roleCode: null,
                                roleName: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('role-master', null, { reload: 'role-master' });
                }, function() {
                    $state.go('role-master');
                });
            }]
        })
        .state('role-master.edit', {
            parent: 'role-master',
            url: '/{id}/edit',
            data: {
                authorities: ['User']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/role-master/role-master-dialog.html',
                    controller: 'RoleMasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RoleMaster', function(RoleMaster) {
                            return RoleMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('role-master', null, { reload: 'role-master' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('role-master.delete', {
            parent: 'role-master',
            url: '/{id}/delete',
            data: {
                authorities: ['User']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/role-master/role-master-delete-dialog.html',
                    controller: 'RoleMasterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RoleMaster', function(RoleMaster) {
                            return RoleMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('role-master', null, { reload: 'role-master' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
