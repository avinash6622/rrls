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
                authorities: ['Research','Master','RM'],
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
                    	decimalValue: null,
                    	rupee: null,                       
                        id: null
                    };
                }
            }
        })
         .state('decimal-configuration.get', {
            parent: 'opportunity-master',
            url: '/decimal-configuration/{id}',
            data: {
                authorities: ['Research','Master','RM'],
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
                entity: ['$stateParams', 'DecimalConfiguration', function($stateParams, DecimalConfiguration) {
                    return DecimalConfiguration.get({id : $stateParams.id}).$promise;
                }],
              /*  previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'decimal-configuration',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]*/
            }
        })
    }

})();
