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
        });
    }

})();
