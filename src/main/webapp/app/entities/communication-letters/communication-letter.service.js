(function() {
    'use strict';
    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('CommunicationLetters', CommunicationLetters);

    CommunicationLetters.$inject = ['$resource', 'DateUtils'];

    function CommunicationLetters ($resource, DateUtils) {
        var resourceUrl =  'api/communication-letter';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
