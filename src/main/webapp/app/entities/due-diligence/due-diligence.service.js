(function() {
    'use strict';
    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('DueDiligences', DueDiligences);

    DueDiligences.$inject = ['$resource', 'DateUtils'];

    function DueDiligences ($resource, DateUtils) {
        var resourceUrl =  'api/due-diligence';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'getSearchOpportunity': {
                url: 'api/due-diligence/search-opp',
                method: 'POST',
                isArray: true
            },
            'getSearchSubject': {
                url: 'api/due-diligence/search-sub',
                method: 'POST',
                isArray: true
            }
        });
    }
})();
