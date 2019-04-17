(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('due-diligence', {
                parent: 'entity',
                url: '/due-diligence?page&sort',
                data: {
                    authorities: ['Admin'],
                    pageTitle: 'Due-Diligence'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/due-diligence/due-diligence.html',
                        controller: 'DueDiligenceController',
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
            .state('due-diligence.new', {
                parent: 'due-diligence',
                url: '/due-diligence/new',
                data: {
                    authorities: ['Admin']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/due-diligence/due-diligence-dialog.html',
                        controller: 'DueDiligenceDialogController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            subject: null,
                            fileName: null,
                            fileData: null,
                            filetype: null,
                            opportunityMasterId: null,
                            createdBy: null,
                            updatedBy: null,
                            createdDate: null,
                            updatedDate: null,
                            id: null
                        };
                    }
                }
            })
    }

})();
