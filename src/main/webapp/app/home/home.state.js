(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('home', {
            parent: 'app',
            url: '/home',
            data: {
                authorities: [],
                pageTitle: 'Research Repository Learning System | Unifi'
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/home.html',
                    controller: 'HomeController',
                    controllerAs: 'vm'
                }

            }
        });
    }
})();

//Opportunity master list
/*
(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('home', {
            parent: 'app',
            url: '/',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Opportunity Master'
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/home.html',
                    controller: 'OpportunityMasterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
       ;
    }

})();*/
