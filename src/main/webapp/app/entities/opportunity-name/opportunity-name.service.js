(function () {
    'use strict';
    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('OpportunityName', OpportunityName);

    OpportunityName.$inject = ['$resource', 'DateUtils', '$window'];

    function OpportunityName($resource, DateUtils, $window) {
        var resourceUrl = 'api/opportunity-names/:id/:inputData';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }

                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    /*     var copy = angular.copy(data);
                         copy.createdDate = DateUtils.convertLocalDateToServer(copy.createdDate);*/
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {

                    /* var copy = angular.copy(data);
                     copy.createdDate = DateUtils.convertLocalDateToServer(copy.createdDate);*/

                    return angular.toJson(data);
                }
            },
            'createdByNull':{url: 'api/opportunity-names-createdbyisnull',
                                            method: 'GET', isArray: true},
            'createdByNotNull':{url: 'api/opportunity-names-createdbyisnotnull',
                                               method: 'GET', isArray: true},

        });
    }
})();
