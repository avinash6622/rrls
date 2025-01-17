(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('login', {
            parent: 'app',
            url: '/',
            data: {
                authorities: [],
                pageTitle: 'Research Repository & Learning System'
            },
            views: {
                'content@': {
                    templateUrl: 'app/components/login/login.html',
                    controller: 'LoginController',
                    controllerAs: 'vm'
                }

            },
            resolve: {
                isAuthenticate: function(Principal) {
                    return Principal.isAuthenticated();
                }

            }
        });
    }
})();
