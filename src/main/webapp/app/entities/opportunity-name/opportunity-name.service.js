(function() {
    'use strict';
    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('OpportunityName', OpportunityName);

    OpportunityName.$inject = ['$resource', 'DateUtils'];

    function OpportunityName ($resource, DateUtils) {
        var resourceUrl =  'api/opportunity-names/:id/:inputData';

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
            }
        });
    }
})();
