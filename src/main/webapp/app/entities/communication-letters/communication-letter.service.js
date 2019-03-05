(function() {
    'use strict';
    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('CommunicationLetters', CommunicationLetters);

    CommunicationLetters.$inject = ['$resource', 'DateUtils'];

    function CommunicationLetters ($resource, DateUtils) {
        var resourceUrl =  'api/communication-letter';

        console.log('resourceUrl');
        console.log(resourceUrl);
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
