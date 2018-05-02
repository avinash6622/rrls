(function() {
    'use strict';
    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('OpportunityLearning', OpportunityLearning);

    OpportunityLearning.$inject = ['$resource', 'DateUtils'];

    function OpportunityLearning ($resource, DateUtils) {
        var resourceUrl =  'api/opportunity-masters/get-learning/:id';

        return $resource(resourceUrl, {}, {
            'learningComment':{
                method:'GET',
                isArray: true
            },
        'save':{
        	url:'api/opportunity-learnings',
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
