(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('policiesFileUpload', {
                parent: 'entity',
                url: '/policiesFileUpload?page&sort',
                data: {
                    authorities: ['User'],
                    pageTitle: 'Policies FileUpload'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/policy/policy.html',
                        controller: 'PoliciesFileUploadController',
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
            .state('policiesFileUpload-detail', {
                parent: 'policiesFileUpload',
                url: '/{id}',
                data: {
                    authorities: ['User'],
                    pageTitle: 'Policies FileUpload'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/policy/policy-detail.html',
                        controller: 'PoliciesFileUploadDetailController',
                        controllerAs: 'vm'
                    }
                }

            })
            .state('policiesFileUpload-detail.edit', {
                parent: 'policiesFileUpload-detail',
                url: '/detail/edit',
                data: {
                    authorities: ['User']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/policy/policy-dialog.html',
                        controller: 'PoliciesFileUploadDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 't',
                        resolve: {
                            entity: ['PoliciesFileUpload', function(PoliciesFileUpload) {
                                return PoliciesFileUpload.get({id : $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('^', {}, { reload: false });
                    }, function() {
                        $state.go('^');
                    });
                }]
            })
            .state('policiesFileUpload.new', {
                parent: 'policiesFileUpload',
                url: '/policiesFileUpload/new',
                data: {
                    authorities: ['User']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/policy/policy-dialog.html',
                        controller: 'PoliciesFileUploadDialogController',
                        controllerAs: 'vm'
                    }
                }            ,
                resolve: {
                    entity: function () {
                        return {
                            policyName: null,
                            fileName: null,
                            fileData: null,
                            filetype: null,
                            createdBy: null,
                            updatedBy: null,
                            createdDate: null,
                            updatedDate: null,
                            id: null
                        };
                    }
                }
            })
            .state('policiesFileUpload.edit', {
                parent: 'policiesFileUpload',
                url: '/{id}/edit',
                data: {
                    authorities: ['User']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/policy/policy-dialog.html',
                        controller: 'PoliciesFileUploadDialogController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'PoliciesFileUpload', function($stateParams, PoliciesFileUpload) {
                        return PoliciesFileUpload.get({id : $stateParams.id}).$promise;
                    }]
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    var vm = this;
                    vm.readOnly = true;
                }]
            })

    }

})();
