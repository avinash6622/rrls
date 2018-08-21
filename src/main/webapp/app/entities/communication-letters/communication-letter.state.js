(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('communication-letter', {
                parent: 'entity',
                url: '/communication-letter?page&sort',
                data: {
                    authorities: ['User'],
                    pageTitle: 'Communication Letter'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/communication-letters/communication-letter.html',
                        controller: 'CommunicationLetterController',
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
           .state('communication-letter.new', {
            parent: 'communication-letter',
            url: '/communication-letter/new',
            data: {
                authorities: ['User']
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/communication-letters/communication-letter-dialog.html',
                    controller: 'CommunicationLettersDialogController',
                    controllerAs: 'vm'
                }
            }            ,
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
