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
         'consolidatedLearning':{
        	 	url:'api/opportunity-learnings',
                method:'GET',
                isArray: true
            },
            'subjectLearning':{
        	 	url:'api/opportunity-learnings-subject',
                method:'POST',
                isArray: true

            },
            'get':{
            	url:'api/opportunity-learnings/:id',
                method:'GET'


            },
            'update':{
            	url:'api/opportunity-learnings',
            	  method: 'PUT',
                  transformRequest: function (data) {
                      var copy = angular.copy(data);

                      return angular.toJson(copy);
                  }
            },
        'save':{
        	url:'api/opportunity-learnings',
            method:'POST',
            transformRequest: function (data) {

                var copy = angular.copy(data);
                copy.createdDate = DateUtils.convertLocalDateToServer(copy.createdDate);

                return angular.toJson(copy);
            }
        },

        'searchopportunity':{
                url:'api/opportunity-learning/get-name',
                method:'POST',
                isArray: true
        }
        });
    }
})();
