(function() {
    'use strict';
    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('ConfidentialLetters', ConfidentialLetters);

    ConfidentialLetters.$inject = ['$resource', 'DateUtils'];

    function ConfidentialLetters ($resource, DateUtils) {
        var resourceUrl =  'api/confidenctial-letters';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'getSearchOpportunity': {
                url: 'api/confidenctial-letters/search-opp',
                method: 'POST',
                isArray: true
            },
            'getSearchSubject': {
                url: 'api/confidenctial-letter/search-sub',
                method: 'POST',
                isArray: true
            }
        });
    }
})();
