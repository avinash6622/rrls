(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('decimal-configuration', {
            parent: 'entity',
            url: '/decimal-configuration',
            data: {
                authorities: ['Research'],
                pageTitle: 'Decimal Configuration'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/decimal-config/decimal-configuration.html',
                    controller: 'DecimalConfigurationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: function () {
                    return {
                    	user: null,
                    	decimal: null,
                    	rupee: null,                       
                        id: null
                    };
                }
            }
        });
    }

})();
