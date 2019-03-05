(function() {
    'use strict';
    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('ConfidentialLetters', ConfidentialLetters);

    ConfidentialLetters.$inject = ['$resource', 'DateUtils'];

    function ConfidentialLetters ($resource, DateUtils) {
        var resourceUrl =  'api/communication-letter';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'getSearchOpportunity': {
                url: 'api/communication-letter/search-opp',
                method: 'POST',
                isArray: true
            },
            'getSearchSubject': {
                url: 'api/communication-letter/search-sub',
                method: 'POST',
                isArray: true
            }
        });
    }
})();
