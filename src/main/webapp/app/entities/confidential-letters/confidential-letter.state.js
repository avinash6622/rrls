(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('confidential-letter', {
                parent: 'entity',
                url: '/confidential-letter?page&sort',
                data: {
                    authorities: ['Admin'],
                    pageTitle: 'Confidential Letter'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/confidential-letters/confidential-letter.html',
                        controller: 'ConfidentialLetterController',
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
            .state('confidential-letter.new', {
                parent: 'confidential-letter',
                url: '/confidential-letter/new',
                data: {
                    authorities: ['Admin']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/confidential-letters/confidential-letter-dialog.html',
                        controller: 'ConfidentialLettersDialogController',
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
