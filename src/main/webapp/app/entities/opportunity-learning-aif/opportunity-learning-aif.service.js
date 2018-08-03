(function() {
    'use strict';
    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('LearningAIF', LearningAIF);

    LearningAIF.$inject = ['$resource', 'DateUtils'];

    function LearningAIF ($resource, DateUtils) {
        var resourceUrl =  'api/learning-aif/:inputData';

        return $resource(resourceUrl, {}, {
        	
        'update':{
        	
            method:'POST',
            transformRequest: function (data) {
                var copy = angular.copy(data);
               
                return angular.toJson(copy);
            	
            }
        },
        'query':{
        	
      	  url:'api/learning-aif',
            method:'GET',
            isArray: true
      },
      'learningAifSubject':{
    	  
    	  url:'api/learning-aif-all',
          method:'GET',
          isArray: true  
      },
      'save':{
    	  url:'api/learning-aif-create',
    	  method: 'POST',
          transformRequest: function (data) {

              var copy = angular.copy(data);
              copy.createdDate = DateUtils.convertLocalDateToServer(copy.createdDate);

              return angular.toJson(copy);
          }
      }
        });
    }
})();
