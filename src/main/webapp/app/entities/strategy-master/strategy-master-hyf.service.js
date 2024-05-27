(function() {
    'use strict';
    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('StrategyMasterHyf', StrategyMasterHyf)
        .factory('CompanyService', CompanyService); // Added CompanyService

    StrategyMasterHyf.$inject = ['$resource', 'DateUtils'];
    CompanyService.$inject = ['$resource']; // Injected dependencies for CompanyService

    function StrategyMasterHyf ($resource, DateUtils) {
        var resourceUrl =  'api/hyf-uploads';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    return angular.toJson(copy);
                }
            }
        });
    }

    function CompanyService($resource) {
        var resourceUrl = 'api/companies'; // Assuming this is your API endpoint for fetching company names

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true }
        });
    }
})();
