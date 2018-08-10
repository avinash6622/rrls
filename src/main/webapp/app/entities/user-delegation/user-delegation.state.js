(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('opportunity-delegation', {
                parent: 'entity',
                url: '/user-delegation',
                data: {
                    authorities: ['User'],
                    pageTitle: 'User Delegation'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/user-delegation/user-delegation.html',
                        controller: 'UserDelegationController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: function () {
                  /*      return {
                            oppName: null,
                            sectorType: null,
                            securityCode: null,
                            segment: null,
                            id: null
                        };*/
                    }
                }
            })
              .state('opportunity-admin-delegation', {
                parent: 'entity',
                url: '/user-admin',
                data: {
                    authorities: ['User'],
                    pageTitle: 'User Delegation'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/user-delegation/user-delegation-admin.html',
                        controller: 'UserDelegationAdminController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: function () {
                  /*      return {
                            oppName: null,
                            sectorType: null,
                            securityCode: null,
                            segment: null,
                            id: null
                        };*/
                    }
                }
            });
    }

})();
