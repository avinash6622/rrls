(function() {
    'use strict';
    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('RoleMaster', RoleMaster);

    RoleMaster.$inject = ['$resource'];

    function RoleMaster ($resource) {
        var resourceUrl =  'api/role-masters/:id';

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
            'update': { method:'PUT' }
        });
    }
})();
