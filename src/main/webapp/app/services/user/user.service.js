(function () {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('User', User);

    User.$inject = ['$resource'];

    function User ($resource) {
        var service = $resource('api/users/:login', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    console.log("Data:",data);
                    return data;
                }
            },
            'save': { method:'POST' },
            'update': { method:'PUT' },
            'delete':{ method:'DELETE'},
            'userDelagation':{
            	url: 'api/users-All',
            	method: 'GET', isArray: true
            }
        });

        return service;
    }



})();
