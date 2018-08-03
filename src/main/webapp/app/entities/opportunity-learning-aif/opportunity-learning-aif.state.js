(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('learning-aif', {
                parent: 'entity',
                url: '/learning-aif?page&sort',
                data: {
                    authorities: ['User'],
                    pageTitle: 'Learning AIF'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/opportunity-learning-aif/opportunity-learning-aif.html',
                        controller: 'LearningAIFController',
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
             .state('learning-aif.new', {
            parent: 'learning-aif',
            url: '/new',
            data: {
                authorities: ['User']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/opportunity-learning-aif/opportunity-learning-aif-dialog.html',
                    controller: 'LearningAIFDialogController',
                    controllerAs: '$ctrl',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                            	subject:null,
                                createdBy: null,
                                updatedBy: null,
                                createdDate: null,
                                updatedDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('learning-aif', null, { reload: 'learning-aif' });
                }, function() {
                    $state.go('learning-aif');
                });
            }]
        });
    }

})();
