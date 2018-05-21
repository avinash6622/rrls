(function() {
    'use strict';
    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('DecimalConfiguration', DecimalConfiguration);

    DecimalConfiguration.$inject = ['$resource', 'DateUtils'];

    function DecimalConfiguration ($resource, DateUtils) {
        var resourceUrl =  'api/decimal-config/:id';

        return $resource(resourceUrl, {}, {
           
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
                    var copy = angular.copy(data);                 
                    return angular.toJson(copy);
                }
            },
            'save': {
            	url:'api/decimal-config',
                method: 'POST',
                transformRequest: function (data) {                	

                          return angular.toJson(data);
                }
            }
        });
    }
})();
