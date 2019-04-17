(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('opportunity-name', {
            parent: 'entity',
            url: '/opportunity-name',
            data: {
                authorities: ['User'],
                pageTitle: 'Opportunity Name'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/opportunity-name/opportunity-name.html',
                    controller: 'OpportunityNameController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: function () {
                    return {
                        oppName: null,
                        sectorType: null,
                        securityCode: null,
                        segment: null,
                        id: null
                    };
                }
            }
        })
            .state('opportunity-name-list', {
            parent: 'opportunity-name',
            url: '/opportunity-name-list?page&sort&type',
            data: {
                authorities: ['User'],
                pageTitle: 'Opportunity Names'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/opportunity-name/opportunity-name-list.html',
                    controller: 'OpportunityNameListController',
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
                    },
                    type: {
                        value: 'manual',
                        squash: true
                    }
                },
                resolve: {
                    pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                        return {
                            page: PaginationUtil.parsePage($stateParams.page),
                            sort: $stateParams.sort,
                            type: $stateParams.type,
                            predicate: PaginationUtil.parsePredicate($stateParams.sort),
                            ascending: PaginationUtil.parseAscending($stateParams.sort)
                        };
                    }]
                }
        });
    }

})();
