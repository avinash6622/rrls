(function() {
    'use strict';
    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('OpportunityLearningAIF', OpportunityLearningAIF);

    OpportunityLearningAIF.$inject = ['$resource', 'DateUtils'];

    function OpportunityLearningAIF ($resource, DateUtils) {
        var resourceUrl =  'api/opportunity-masters/get-learning-aif/:id';

        return $resource(resourceUrl, {}, {
            'learningComment':{
                method:'GET',
                isArray: true
            },
       
            'subjectLearning':{
        	 	url:'api/opportunity-learnings-aif-subject',
                method:'POST',
                isArray: true

            },
            'get':{
            	url:'api/opportunity-learnings-aif/:id',
                method:'GET'


            },
            'update':{
            	url:'api/opportunity-learnings-aif',
            	  method: 'PUT',
                  transformRequest: function (data) {
                      var copy = angular.copy(data);

                      return angular.toJson(copy);
                  }
            },
        'save':{
        	url:'api/opportunity-learnings-aif',
            method:'POST',
            transformRequest: function (data) {

                var copy = angular.copy(data);
                copy.createdDate = DateUtils.convertLocalDateToServer(copy.createdDate);

                return angular.toJson(copy);
            }
        },

        'searchopportunity':{
                url:'api/opportunity-learning-aif/get-name',
                method:'POST',
                isArray: true
        }/*,
        'fixedLearning':{
        	
        	  url:'api/fixed-learning',
              method:'GET',
              isArray: true
        }*/
       
        });
    }
})();
