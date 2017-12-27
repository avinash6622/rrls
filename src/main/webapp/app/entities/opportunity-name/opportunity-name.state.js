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
                authorities: ['ROLE_USER'],
                pageTitle: 'Opportunity Name'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/opportunity-name/opportunity-masters.html',
                    controller: 'OpportunityNameController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        });
    }

})();
