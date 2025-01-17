(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('strategy-master', {
            parent: 'entity',
            url: '/strategy-master',
            data: {
                authorities: ['User'],
                pageTitle: 'Strategy Master'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/strategy-master/strategy-masters.html',
                    controller: 'StrategyMasterController',
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
        .state('strategy-master-detail', {
            parent: 'strategy-master',
            url: '/strategy-master/{id}',
            data: {
                authorities: ['User'],
                pageTitle: 'Strategy Master'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/strategy-master/strategy-master-detail.html',
                    controller: 'StrategyMasterDetailController',
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
               /* entity: ['$stateParams', 'StrategyMaster', function($stateParams, StrategyMaster) {
                    return StrategyMaster.get({id : $stateParams.id}).$promise;
                }],*/
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort)
                    };
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'strategy-master',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('strategy-master-hyf', {
                    parent: 'strategy-master',
                    url: '/strategy-master-hyf',
                    data: {
                        authorities: ['User'],
                        pageTitle: 'Strategy Master High Yield Fund'
                    },
                    views: {
                        'content@': {
                            templateUrl: 'app/entities/strategy-master/strategy-master-hyf.html',
                            controller: 'StrategyMasterHyfController',
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
                       /* entity: ['$stateParams', 'StrategyMaster', function($stateParams, StrategyMaster) {
                            return StrategyMaster.get({id : $stateParams.id}).$promise;
                        }],*/
                        pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                            return {
                                page: PaginationUtil.parsePage($stateParams.page),
                                sort: $stateParams.sort,
                                predicate: PaginationUtil.parsePredicate($stateParams.sort),
                                ascending: PaginationUtil.parseAscending($stateParams.sort)
                            };
                        }],
                        previousState: ["$state", function ($state) {
                            var currentStateData = {
                                name: $state.current.name || 'strategy-master-hyf',
                                params: $state.params,
                                url: $state.href($state.current.name, $state.params)
                            };
                            return currentStateData;
                        }]
                    }
                })
        .state('strategy-master-detail.edit', {
            parent: 'strategy-master-detail',
            url: '/detail/edit',
            data: {
                authorities: ['User']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/strategy-master/strategy-master-dialog.html',
                    controller: 'StrategyMasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 't',
                    resolve: {
                        entity: ['StrategyMaster', function(StrategyMaster) {
                            return StrategyMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('strategy-master.new', {
            parent: 'strategy-master',
            url: '/new',
            data: {
                authorities: ['User']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/strategy-master/strategy-master-dialog.html',
                    controller: 'StrategyMasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                strategyCode: null,
                                strategyName: null,
                                sStatus: null,
                                dateActive: null,
                                createdBy: null,
                                updatedBy: null,
                                createdDate: null,
                                updatedDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('strategy-master', null, { reload: 'strategy-master' });
                }, function() {
                    $state.go('strategy-master');
                });
            }]
        })
        .state('strategy-master.edit', {
            parent: 'strategy-master',
            url: '/{id}/edit',
            data: {
                authorities: ['User']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/strategy-master/strategy-master-dialog.html',
                    controller: 'StrategyMasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StrategyMaster', function(StrategyMaster) {
                            return StrategyMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('strategy-master', null, { reload: 'strategy-master' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('strategy-master.delete', {
            parent: 'strategy-master',
            url: '/{id}/delete',
            data: {
                authorities: ['Admin']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/strategy-master/strategy-master-delete-dialog.html',
                    controller: 'StrategyMasterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['StrategyMaster', function(StrategyMaster) {
                            return StrategyMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('strategy-master', null, { reload: 'strategy-master' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
