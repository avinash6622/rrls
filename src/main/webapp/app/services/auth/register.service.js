(function () {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
