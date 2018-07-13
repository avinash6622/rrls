/*(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
           .state('consolidated-learnings', {
                parent: 'entity',
                url: '/consolidated-learnings?page&sort',
                data: {
                    authorities: ['User'],
                    pageTitle: 'Consolidated Learning'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/opportunity-learning/opportunity-learning-consolidated.html',
                        controller: 'LearningConsolidatedController',
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
        .state('fixed-learning', {
            parent: 'entity',
            url: '/fixed-learning?page&sort',
            data: {
                authorities: ['User'],
                pageTitle: 'Fixed Learning'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/opportunity-learning/opportunity-learning-consolidated.html',
                    controller: 'LearningConsolidatedController',
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
        });
    }

})();
*/