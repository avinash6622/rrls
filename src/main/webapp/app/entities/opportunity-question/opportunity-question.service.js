(function() {
    'use strict';
    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('OpportunityQuestion', OpportunityQuestion);

    OpportunityQuestion.$inject = ['$resource', 'DateUtils'];

    function OpportunityQuestion ($resource, DateUtils) {
        var resourceUrl =  'api/opportunity-masters/get-Question/:id';

        return $resource(resourceUrl, {}, {
            'questionAnswer':{
                method:'GET',
                isArray: true
            },
        'save':{
        	url:'api/opportunity-questions',
            method:'POST',
            transformRequest: function (data) {

                var copy = angular.copy(data);
                copy.createdDate = DateUtils.convertLocalDateToServer(copy.createdDate);

                return angular.toJson(copy);
            }
        }
        });
    }
})();
